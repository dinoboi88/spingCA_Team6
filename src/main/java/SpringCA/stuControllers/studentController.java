package SpringCA.stuControllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import SpringCA.entities.Semester;
import SpringCA.entities.Student;
import SpringCA.entities.StudentCourse;
import SpringCA.entities.StudentUser;
import SpringCA.stuRepo.StuCourseRepo;
import SpringCA.stuRepo.StudentRepository;
import SpringCA.stuRepo.StudentUserRepo;
import SpringCA.stuService.StudentService;
import SpringCA.stuService.StudentServiceImpl;

@Controller
@SessionAttributes("studentUser")
public class studentController {

	@Autowired
	private StudentRepository stuRepo;

	@Autowired
	private StuCourseRepo stuCourseRepo;

	@Autowired
	private StudentUserRepo stuUserRepo;

	@Autowired
	private StudentServiceImpl stuserviceImpl;

	@Autowired
	private StudentService stuservice;

	@Autowired
	public void setProductServices(StudentServiceImpl stuserviceImpl) {
		this.stuservice = stuserviceImpl;
	}

	@GetMapping("/student/home")
	public String getHome() {
		return "home";
	}

	@GetMapping("/student/login")
	public String getLoginPage(Model model) {
		model.addAttribute("studentUser", new StudentUser());
		return "login";
	}

	@GetMapping("/student/logout")
	public String getLogoutPage(SessionStatus status) {
		status.setComplete();
		return "redirect:/student/home";
	}

	@PostMapping("/student/authenticate")
	public String getAuthentication(@ModelAttribute("studentUser") StudentUser studentUser,
			BindingResult bindingResult) {

		StudentUser user = stuUserRepo.findStudentUserByUserId_Username(studentUser.getUserId().getUsername());

		if (user == null) {
			return "login";
		}

		else {
			if (user.getPassword().equals(studentUser.getPassword())) {
				return "redirect:/student/navigate";
			}
			else {
				return "login";
			}
		}
	}

	@GetMapping("/student/navigate")
	public String Navigate(@ModelAttribute("studentUser") StudentUser studentUser, Model model) {

		StudentUser user = stuUserRepo.findStudentUserByUserId_Username(studentUser.getUserId().getUsername());
		int student_id = user.getUserId().getUser();
		model.addAttribute("studentId", student_id);
		return "Navigate";
	}

	@GetMapping("student/info/{id}")
	public String StudentInfo(@PathVariable("id") Integer id, Model model) {

		Student student = stuRepo.findById(id).get();
		model.addAttribute("student", student);
		return "stuInfo";
	}

	@GetMapping("/student/edit/{studentId}")
	public String showEditForm(@PathVariable("studentId") int studentId, Model model) {
		Student student = stuRepo.findById(studentId);
		model.addAttribute("student", student);
		return "stuInfoForm";
	}

	@RequestMapping(value = "/student/editSave", path = "/student/editSave", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/html")
	public String updateInfo(@ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
		Student updateStu = stuservice.updateStudent(student.getStudentId(), student);
		model.addAttribute("student", updateStu);
		return "stuInfo";
	}

	@GetMapping("/student/courseEnrolled/{studentId}")
	public String listEnrolledCourse(@PathVariable("studentId") int studentId, Model model) {
		Student stu = stuRepo.findById(studentId);
		List<StudentCourse> courseEnrolled = stuCourseRepo.findByStudentByCourse(stu);
		StudentCourse sc = courseEnrolled.get(0);
		System.out.println(sc.getCourseByStudent().getCourseName());
		model.addAttribute("courseEnrolled", courseEnrolled);
		return "stuCourse";
	}

	@GetMapping("/student/courseScore/{studentId}")
	public String listcourseScores(@PathVariable("studentId") int studentId, Model model) {
		Student stu = stuRepo.findById(studentId);
		List<StudentCourse> studentcourse = stuCourseRepo.findByStudentByCourse(stu);
		List<Semester> semesters = new ArrayList<Semester>();
		for (StudentCourse sc : studentcourse) {
			semesters.add(sc.getSemesterStudentCourse());
		}
		semesters = semesters.stream().distinct().collect(Collectors.toList());
		Map<Semester, List<StudentCourse>> semCourseMap = new HashMap<Semester, List<StudentCourse>>();
		for (Semester semester : semesters) {
			List<StudentCourse> semestercourse = stuCourseRepo.findByStudentByCourseAndSemesterStudentCourse(stu,
					semester);
			semCourseMap.put(semester, semestercourse);
		}
		model.addAttribute("student", stu);
		model.addAttribute("semCourseMap", semCourseMap);
		return "courseScore";

	}

}
