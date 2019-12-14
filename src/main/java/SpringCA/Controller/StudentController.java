package SpringCA.Controller;

import SpringCA.Repository.*;
import SpringCA.Service.StudentService;
import SpringCA.Service.StudentServiceImpl;
import SpringCA.Service.UserServiceImpl;
import SpringCA.entities.*;
import SpringCA.entities.CompositeId.StudentCourseId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {

	private StudentRepository studentRepository;
	private StudentCourseRepository studentCourseRepository;
	private StudentUserRepository studentUserRepository;
	private StudentServiceImpl studentServiceImpl;
	private StudentService studentService;
	private UserServiceImpl userService;
	private CourseRepository courseRepository;
	private SemesterRepository semesterRepository;

	public StudentController(StudentRepository studentRepository, StudentCourseRepository studentCourseRepository,
							 StudentUserRepository studentUserRepository, StudentServiceImpl studentServiceImpl,
							 StudentService studentService, UserServiceImpl userService, CourseRepository courseRepository,
							 SemesterRepository semesterRepository) {
		this.studentRepository = studentRepository;
		this.studentCourseRepository = studentCourseRepository;
		this.studentUserRepository = studentUserRepository;
		this.studentServiceImpl = studentServiceImpl;
		this.studentService = studentService;
		this.userService = userService;
		this.courseRepository = courseRepository;
		this.semesterRepository = semesterRepository;
	}

	@ModelAttribute("studentUser")
	private StudentUser getStudentUser(){
		return studentUserRepository.findByUsername(userService.getUsername());
	}

	@ModelAttribute("nextSemester")
	private Semester getNextSemester(){
		Iterable<Semester> semesters = semesterRepository.findAll();
		Date today = new Date();
		for(Semester s:semesters){
			if(s.getStartDate().compareTo(today) > 0)
				return s;
		}
		return null;
	}

	@GetMapping(value = "/profile")
	public String getProfile(Model model){
		StudentUser studentUser = studentUserRepository.findByUsername(userService.getUsername());
		model.addAttribute("studentUser", studentUser);
		model.addAttribute("student", studentUser.getStudentUser());
		return "student/studentProfile";
	}

//	@GetMapping(value = "/changePassword/{username}")
//	public String changePassword(@PathVariable("username") String username, Model model, ResetPassword resetPassword){
//		model.addAttribute("username", username);
//		model.addAttribute("security", "student");
//		return "resetPassword";
//	}
//
//	@PostMapping(value = "/changePassword/{username}")
//	public String resetPassword(@PathVariable("username") String username,
//								@Valid ResetPassword resetPassword,
//								BindingResult result, Model model){
//		if(result.hasErrors()){
//			model.addAttribute("username", username);
//			return "resetPassword";
//		}
//		userService.resetStudentPassword(username, resetPassword.getPassword());
//		return "redirect:/student/profile";
//	}


	@GetMapping("/edit/{studentId}")
	public String showEditForm(@PathVariable("studentId") int studentId, Model model) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + studentId));
		model.addAttribute("student", student);
		return "student/stuInfoForm";
	}

	@RequestMapping(value = "/editSave", path = "/editSave", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/html")
	public String updateInfo(@ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
		Student updateStu = studentServiceImpl.updateStudent(student.getStudentId(), student);
		model.addAttribute("student", updateStu);
		return "redirect:/student/profile";
	}

	@GetMapping("/courseEnrolled")
	public String listEnrolledCourse(Model model) {
		Iterable<StudentCourse> courseEnrolled = studentCourseRepository.findByStudentByCourse_StudentId(
				getStudentUser().getStudentUser().getStudentId());
		model.addAttribute("courseEnrolled", courseEnrolled);
		return "student/stuCourse";
	}

	@GetMapping("/courseScore")
	public String listCourseScores( Model model) {
		int studentId = getStudentUser().getStudentUser().getStudentId();
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + studentId));
		Iterable<StudentCourse> studentCourses = studentCourseRepository.findByStudentByCourse_StudentId(studentId);
		List<Semester> semesters = new ArrayList<Semester>();
		for (StudentCourse sc : studentCourses) {
			semesters.add(sc.getSemesterStudentCourse());
		}
		semesters = semesters.stream().distinct().collect(Collectors.toList());
		Map<Semester, List<StudentCourse>> semCourseMap = new HashMap<Semester, List<StudentCourse>>();
		for (Semester semester : semesters) {
			Iterable<StudentCourse> studentCoursesBySemester = studentCourseRepository.findByCourseByStudent_CourseIdAndSemesterStudentCourse_SemesterIdAndStatus(
					studentId, semester.getSemesterId(), "Approved"
			);
			semCourseMap.put(semester, (List<StudentCourse>) studentCoursesBySemester);
		}
		model.addAttribute("student", student);
		model.addAttribute("semCourseMap", semCourseMap);
		return "student/courseScore";
	}

	@GetMapping("/availableCourse")
	public String listAvailableCourse(Model model){
		Iterable<Course> availableCourses = courseRepository.getNotEnrolledCourse(getStudentUser().getStudentUser().getStudentId());
		if(availableCourses != null) model.addAttribute("availableCourses", availableCourses);
		return "student/availableCourse";
	}

	@GetMapping("/course/{action}/{semesterId}/{courseId}")
	public String registerForCourse(@PathVariable("courseId") int courseId, @PathVariable("action") String action,
									@PathVariable("semesterId") int semesterId){
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + courseId));
		Semester semester = semesterRepository.findById(semesterId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + semesterId));
		Student student = getStudentUser().getStudentUser();
		StudentCourse studentCourse = new StudentCourse(student, course, semester, 0, "Pending");
		studentCourseRepository.save(studentCourse);
		if(action.equals("registerAgain")) return "redirect:/student/rejectedCourse";
		return "redirect:/student/availableCourse";
	}

	@GetMapping("/pendingCourse")
	public String listPendingCourse(Model model){
		Iterable<StudentCourse> pendingCourses =
				studentCourseRepository.findByStudentByCourse_StudentIdAndStatus(
						getStudentUser().getStudentUser().getStudentId(), "Pending");
		if(pendingCourses != null) model.addAttribute("pendingCourses", pendingCourses);
		return "student/pendingCourse";
	}

	@GetMapping("/course/cancel/{semesterId}/{courseId}")
	public String cancelCourse(@PathVariable("courseId") int courseId,
							   @PathVariable("semesterId") int semesterId){
		StudentCourseId studentCourseId= new StudentCourseId(
				getStudentUser().getStudentUser().getStudentId(), courseId, semesterId);
		StudentCourse studentCourse = studentCourseRepository.findById(studentCourseId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid student info"));
		studentCourseRepository.delete(studentCourse);
		return "redirect:/student/pendingCourse";
	}

	@GetMapping("/rejectedCourse")
	public String listRejectedCourse(Model model){
		Iterable<StudentCourse> rejectedCourses =
				studentCourseRepository.findByStudentByCourse_StudentIdAndStatus(
						getStudentUser().getStudentUser().getStudentId(), "Rejected");
		if(rejectedCourses != null) model.addAttribute("rejectedCourses", rejectedCourses);
		return "student/rejectedCourse";
	}

}
