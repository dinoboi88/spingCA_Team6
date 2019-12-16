package SpringCA.Controller;

import javax.validation.Valid;

import SpringCA.Repository.*;
import SpringCA.Service.UserServiceImpl;
import SpringCA.entities.*;
import SpringCA.entities.CompositeId.LecturerLeaveId;
import SpringCA.model.ResetPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import SpringCA.entities.CompositeId.StudentCourseId;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/lecturer")
public class LecturerController {
	private LecturerCourseRepository lecturerCourseRepository;
	private LecturerRepository lecturerRepository;
	private StudentCourseRepository studentCourseRepository;
	private UserServiceImpl userService;
	private LecturerUserRepository lecturerUserRepository;
	private LecturerLeaveRepository lecturerLeaveRepository;
	private StudentRepository studentRepository;

	public LecturerController(LecturerCourseRepository lecturerCourseRepository, LecturerRepository lecturerRepository,
							  StudentCourseRepository studentCourseRepository, UserServiceImpl userService,
							  LecturerUserRepository lecturerUserRepository, LecturerLeaveRepository lecturerLeaveRepository,
							  StudentRepository studentRepository) {
		this.lecturerCourseRepository = lecturerCourseRepository;
		this.lecturerRepository = lecturerRepository;
		this.studentCourseRepository = studentCourseRepository;
		this.userService = userService;
		this.lecturerUserRepository = lecturerUserRepository;
		this.lecturerLeaveRepository = lecturerLeaveRepository;
		this.studentRepository=studentRepository;
	}

	private int size = 3;

	@ModelAttribute("lecturerUser")
	public LecturerUser getLecturerUser() {
		return lecturerUserRepository.findByUsername(userService.getUsername());
	}

	@GetMapping(value = "/profile")
	public String getProfile(Model model) {
		LecturerUser lecturerUser = lecturerUserRepository.findByUsername(userService.getUsername());
		model.addAttribute("lecturerUser", lecturerUser);
		model.addAttribute("lecturer", lecturerUser.getLecturerUser());
		return "lecturer/lecturerProfile";
	}

	@GetMapping(value = "/changePassword/{username}")
	public String changePassword(@PathVariable("username") String username, Model model, ResetPassword resetPassword) {
		model.addAttribute("username", username);
		model.addAttribute("security", "lecturer");
		return "resetPassword";
	}

	@PostMapping(value = "/changePassword/{username}")
	public String resetPassword(@PathVariable("username") String username,
								@Valid ResetPassword resetPassword,
								BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("username", username);
			return "resetPassword";
		}
		userService.resetLecturerPassword(username, resetPassword.getPassword());
		return "redirect:/lecturer/profile";
	}


	@GetMapping(value = "/course/list/page/{page}")
	public String viewAllCourses(@PathVariable("page") int page, Model model) {
		LecturerUser lecturerUser = lecturerUserRepository.findByUsername(userService.getUsername());
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<LecturerCourse> lecturerCourses =
				lecturerCourseRepository.findByLecturerByCourse_LecturerId(lecturerUser.getLecturerUser().getLecturerId(), pageable);
		int totalCoursePages = lecturerCourses.getTotalPages();
		model.addAttribute("courseList", lecturerCourses.getContent());

		if (totalCoursePages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalCoursePages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
			model.addAttribute("page", page);
		}
		return "lecturer/courseList";
	}


	@GetMapping("/course/student/{courseId}/{semesterId}/page/{page}")
	public String viewStudentByCourse(@PathVariable("courseId") int courseId, @PathVariable("semesterId") int semesterId,
									  @PathVariable("page") int page, Model model) {
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<StudentCourse> studentByCourseList =
				studentCourseRepository.findByCourseByStudent_CourseIdAndSemesterStudentCourse_SemesterIdAndStatus(
						courseId, semesterId, "Approved", pageable);
		int totalStudentByCoursePages = studentByCourseList.getTotalPages();
		model.addAttribute("studentByCourseList", studentByCourseList.getContent());
		model.addAttribute("courseId", courseId);
		model.addAttribute("semesterId", semesterId);
		if (totalStudentByCoursePages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalStudentByCoursePages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
			model.addAttribute("page", page);
		}
		return "lecturer/studentByCourse";
	}

	@GetMapping("/update/{page}/score/{studentId}/{courseId}/{semesterId}")
	public String updateScore(@PathVariable("studentId") int studentId, @PathVariable("courseId") int courseId,
							  @PathVariable("semesterId") int semesterId, @PathVariable("page") int page, Model model) {
		StudentCourseId studentCourseId = new StudentCourseId(studentId, courseId, semesterId);
		StudentCourse studentCourse = studentCourseRepository.findById(studentCourseId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid id"));
		model.addAttribute("page", page);
		model.addAttribute("studentCourse", studentCourse);
		return "lecturer/updateScore";
	}

	@PostMapping("/update/{page}/score")
	public String updateScore(@PathVariable("page") int page, @Valid StudentCourse studentCourse, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("page", page);
			return "lecturer/updateScore";
		}
		
		studentCourseRepository.save(studentCourse);
		
		int studentId=studentCourse.getStudentByCourse().getStudentId();
		Iterable<StudentCourse> semesterCourses=studentCourseRepository.
				findByStudentByCourse_StudentId(studentId);
		float GPA=0;
		int courseUnit=0;
		float score;
		int totalCourseUnits=0;
		float total_Score_mult_CourseUnits=0;
		
		 for(StudentCourse semCourse:semesterCourses) {
			 courseUnit=semCourse.getCourseByStudent().getCourseUnit();
			 score=semCourse.getScore();
			 total_Score_mult_CourseUnits+=score*courseUnit;
			 totalCourseUnits+=courseUnit;
		 }
		 
		 GPA=total_Score_mult_CourseUnits/totalCourseUnits;
		 GPA=(float) (Math.round(GPA*100.0)/100.0);
		 Student student=studentRepository.findById(studentId).get();
		 student.setGPA(GPA);
		 studentRepository.save(student);
		
		return "redirect:/lecturer/course/student/" + studentCourse.getCourseByStudent().getCourseId() + "/"
				+ studentCourse.getSemesterStudentCourse().getSemesterId() + "/page/" + page;
	}

	@GetMapping("/leave/list")
	public String getLecturerLeave(Model model) {
		Iterable<LecturerLeave> lecturerLeaves =
				lecturerLeaveRepository.findByLecturerByLeave_LecturerIdAndStatus(
						getLecturerUser().getLecturerUser().getLecturerId(), "Approved");
		model.addAttribute("lecturerLeaves", lecturerLeaves);
		return "lecturer/lecturerLeaveList";
	}

	@GetMapping("/leave/application")
	public String getLeaveApplicationForm(Model model, LecturerLeave lecturerLeave) {
		lecturerLeave = new LecturerLeave();
		LecturerUser lecturerUser = lecturerUserRepository.findByUsername(userService.getUsername());
		Lecturer lecturer = lecturerRepository.findById(lecturerUser.getLecturerUser().getLecturerId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid id"));
		lecturerLeave.setLecturerByLeave(lecturer);
		model.addAttribute("lecturerLeave", lecturerLeave);
		return "lecturer/lecturerLeaveForm";
	}

	@PostMapping("/leave/application/submit/{date}")
	public String submitLeaveApplication(@Valid LecturerLeave lecturerLeave, BindingResult result,
										 @PathVariable("date") String date,
										 Model model) throws ParseException {
		if (result.hasErrors()) {
			return "lecturer/lecturerLeaveForm";
		}
		if (lecturerLeave.getStartDate().compareTo(lecturerLeave.getEndDate()) >= 0) {
			model.addAttribute("msg", "End date must be after start date");
			return "lecturer/lecturerLeaveForm";
		}

		if (date.equals("new")) {
			LecturerLeaveId newLeave = new LecturerLeaveId(getLecturerUser().getLecturerUser().getLecturerId(), lecturerLeave.getStartDate());
			try {
				LecturerLeave oldLeave = lecturerLeaveRepository.findById(newLeave)
						.orElseThrow(() -> new IllegalArgumentException("Invalid"));
				model.addAttribute("msg", "You have already applied for a leave started from this date." +
						"Please go back to the pending list if you would like to edit the end date.");
				return "lecturer/lecturerLeaveForm";
			} catch (IllegalArgumentException e) {
				//ignore
			}
		} else if (!date.equals("new")) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date fDate = formatter.parse(date);
			LecturerLeaveId lecturerLeaveId = new LecturerLeaveId(getLecturerUser().getLecturerUser().getLecturerId(), fDate);
			try {
				LecturerLeave oldLeave = lecturerLeaveRepository.findById(lecturerLeaveId)
						.orElseThrow(() -> new IllegalArgumentException("Invalid"));
				lecturerLeaveRepository.delete(oldLeave);
			} catch (IllegalArgumentException e) {
				//ignore
			}
		}
		lecturerLeave.setStatus("Pending");
		lecturerLeaveRepository.save(lecturerLeave);
		return "redirect:/lecturer/leave/pending/list";
	}

	@GetMapping("/leave/pending/list")
	public String getPendingLeave(Model model) {
		Iterable<LecturerLeave> lecturerPendingLeaves =
				lecturerLeaveRepository.findByLecturerByLeave_LecturerIdAndStatus(
						getLecturerUser().getLecturerUser().getLecturerId(), "Pending");
		model.addAttribute("lecturerPendingLeaves", lecturerPendingLeaves);
		return "lecturer/lecturerPendingLeaves";
	}

	@GetMapping("/leave/pending/edit/{startDate}")
	public String editPendingLeave(@PathVariable("startDate") String startDate, Model model) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date date = formatter.parse(startDate);
		LecturerLeaveId lecturerLeaveId = new LecturerLeaveId(getLecturerUser().getLecturerUser().getLecturerId(), date);
		LecturerLeave lecturerLeave = lecturerLeaveRepository.findById(lecturerLeaveId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid"));
		model.addAttribute("lecturerLeave", lecturerLeave);
		model.addAttribute("date", lecturerLeave.getStartDate());
		return "lecturer/lecturerLeaveForm";
	}

	@GetMapping("/leave/pending/cancel/{startDate}")
	public String cancelPendingLeave(@PathVariable("startDate") String startDate, Model model) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date date = formatter.parse(startDate);
		LecturerLeaveId lecturerLeaveId = new LecturerLeaveId(getLecturerUser().getLecturerUser().getLecturerId(), date);
		LecturerLeave lecturerLeave = lecturerLeaveRepository.findById(lecturerLeaveId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid"));
		lecturerLeaveRepository.delete(lecturerLeave);
		return "redirect:/lecturer/leave/pending/list";
	}


	@GetMapping("/leave/rejected/list")
	public String getRejectedLeave(Model model) {
		Iterable<LecturerLeave> lecturerRejectedLeaves =
				lecturerLeaveRepository.findByLecturerByLeave_LecturerIdAndStatus(
						getLecturerUser().getLecturerUser().getLecturerId(), "Rejected");
		model.addAttribute("lecturerRejectedLeaves", lecturerRejectedLeaves);
		return "lecturer/lecturerRejectedLeaves";
	}
}