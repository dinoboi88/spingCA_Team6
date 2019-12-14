package SpringCA.Controller;

import javax.validation.Valid;

import SpringCA.Repository.*;
import SpringCA.Service.UserServiceImpl;
import SpringCA.entities.*;
import SpringCA.model.ResetPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import SpringCA.entities.CompositeId.StudentCourseId;

@Controller
@RequestMapping("/lecturer")
public class LecturerController {
	@Autowired
	private LecturerCourseRepository lecturerCourseRepository;
	
	@Autowired
	private LecturerRepository lecturerRepository;
	
	@Autowired 
	private StudentCourseRepository studentCourseRepository;
	
	@Autowired 
	private CourseRepository courseRepository;
	
	@Autowired 
	private SemesterRepository semesterRepository;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private LecturerUserRepository lecturerUserRepository;

	static final int size = 3;

	@GetMapping(value = "/profile")
	public String getProfile(Model model){
		LecturerUser lecturerUser = lecturerUserRepository.findByUsername(userService.getUsername());
		model.addAttribute("lecturerUser", lecturerUser);
		model.addAttribute("lecturer", lecturerUser.getLecturerUser());
		return "lecturer/lecturerProfile";
	}

	@GetMapping(value = "/changePassword/{username}")
	public String changePassword(@PathVariable("username") String username, Model model, ResetPassword resetPassword){
		model.addAttribute("username", username);
		model.addAttribute("security", "lecturer");
		return "resetPassword";
	}

	@PostMapping(value = "/changePassword/{username}")
	public String resetPassword(@PathVariable("username") String username,
								@Valid ResetPassword resetPassword,
								BindingResult result, Model model){
		if(result.hasErrors()){
			model.addAttribute("username", username);
			return "resetPassword";
		}
		userService.resetLecturerPassword(username, resetPassword.getPassword());
		return "redirect:/lecturer/profile";
	}


	@GetMapping("/course/list/all")//view all lecturers and their assigned courses;
	public String getLecturerCourse(@PageableDefault(size = size) Pageable pageable,Model model) {
		Page<LecturerCourse> page=lecturerCourseRepository.findAll(pageable);
		model.addAttribute("page", page);
		return "/lecturer/lecturerCourseList";
	}
	
	@GetMapping("/course")//get specific lecturer's assigned courses;
	public String getLecturerCourseByLecturerId(@PageableDefault(size=1) Pageable pageable,
			Model model) {
		int lecturerId = lecturerUserRepository.findByUsername(userService.getUsername()).getLecturerUser().getLecturerId();
		
		Lecturer lec=lecturerRepository.findById(lecturerId).orElseThrow(
				() -> new IllegalArgumentException("Lecturer Id: " + lecturerId + " not found."));
		
		Page<LecturerCourse> lectCourse=lecturerCourseRepository.findAllByLecturerByCourse(lec,pageable);
		
		//Page<LecturerCourse> page=new PageImpl<LecturerCourse>(lectCourse,pageable,0);
		model.addAttribute("page", lectCourse);
		//model.addAttribute("lecturerId",lecturerId);
		return "/lecturer/lecturerCourseList";
	}
	
	@GetMapping("/course/student/{courseId}/{semId}")//view students in a course and particular semester;
	public String getStudentCourseByCourseAndSemester(@PathVariable("courseId")int courseId,
			@PathVariable("semId")int semId,
			@PageableDefault(size=size) Pageable pageable,Model model) {
		
		Course course=courseRepository.findById(courseId).orElseThrow(
				() -> new IllegalArgumentException("Course Id: " + courseId + " not found."));
		
		Semester sem=semesterRepository.findById(semId).orElseThrow(
				() -> new IllegalArgumentException("Semester Id: " + semId + " not found."));
		
		Page<StudentCourse> studCourse=studentCourseRepository.findByCourseByStudent_CourseIdAndSemesterStudentCourse_SemesterIdAndStatus(
				course.getCourseId(), sem.getSemesterId(), "Approved", pageable);
		
		model.addAttribute("page", studCourse);
		model.addAttribute("courseId",courseId);
		model.addAttribute("semId",semId);
		
		return "lecturer/studentCourseList";
	}
	
	@GetMapping("/course/student/score/{studentId}/{courseId}/{semId}")
	public ModelAndView editStudentCourseScore(@PathVariable("studentId")int studentId,
			@PathVariable("courseId")int courseId,
			@PathVariable("semId")int semId) { //,Model model
		ModelAndView mav=new ModelAndView("lecturer/updateScore");
		StudentCourse studCourse=studentCourseRepository.findById(new StudentCourseId(studentId,courseId,semId)).get();
		mav.addObject("studentCourse",studCourse);
		
		//mav.addObject("score",studCourse.getScore());
		mav.addObject("studentId", studentId);
		mav.addObject("courseId", courseId);
		mav.addObject("semId", semId);
		
		return mav;
	}
	
	/*@ModelAttribute("studentCourse")StudentCourse studCourse*/
	
	@PostMapping("/update/score/{studentId}/{courseId}/{semId}")
	public String updateStudentCourseScore(
			@PathVariable("studentId")int studentId,
			@PathVariable("courseId")int courseId,
			@PathVariable("semId")int semId,@RequestParam("score")float score) {
		
		StudentCourse studCourse=studentCourseRepository.findById(new StudentCourseId(studentId,courseId,semId)).get();
		studCourse.setScore(score);
		studentCourseRepository.save(studCourse);
		
		return"redirect:/course/student/"+courseId+"/"+semId;
	}
}
