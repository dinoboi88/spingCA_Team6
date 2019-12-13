package SpringCA.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import SpringCA.entities.Course;
import SpringCA.entities.Lecturer;
import SpringCA.entities.LecturerCourse;
import SpringCA.entities.Semester;
import SpringCA.entities.StudentCourse;
import SpringCA.entities.CompositeId.StudentCourseId;
import SpringCA.Repository.CourseRepository;
import SpringCA.Repository.SemesterRepository;
import SpringCA.Repository.StudentCourseRepository;
import SpringCA.Repository.LecturerCourseRepository;
import SpringCA.Repository.LecturerRepository;

@Controller
public class LecturerController {
	@Autowired
	private LecturerCourseRepository lectCourRepo;
	
	@Autowired
	private LecturerRepository lecturerRepo;
	
	@Autowired 
	private StudentCourseRepository studCourseRepo;
	
	@Autowired 
	private CourseRepository courseRepo;
	
	@Autowired 
	private SemesterRepository semRepo;
	
    
    /****Please set lecturerId into session after successful login. Then can remove this method.*****/
	@GetMapping("/setSession-lecturerId/{lecturerId}") 
    public String setSessionLecturerId(@PathVariable("lecturerId")int lecturerId,HttpSession session) {
        session.setAttribute("lecturerId", lecturerId);
        return "redirect:/view-lecturer-course";
    }
	
	@GetMapping("/view-all-lecturers-courses")//view all lecturers and their assigned courses; 
	public String getLecturerCourse(@PageableDefault(size=3) Pageable pageable,Model model) {
		Page<LecturerCourse> page=lectCourRepo.findAll(pageable);
		model.addAttribute("page", page);
		return "/view-lecturer-course";
	}
	
	@GetMapping("view-lecturer-course")//get specific lecturer's assigned courses; 
	public String getLecturerCourseByLecturerId(@PageableDefault(size=1) Pageable pageable,
			Model model,HttpSession httpSession) {
		
		int lecturerId=(int) httpSession.getAttribute("lecturerId"); //Using lecturerId stored in session variable here.
		
		Lecturer lec=lecturerRepo.findById(lecturerId).orElseThrow(
				() -> new IllegalArgumentException("Lecturer Id: " + lecturerId + " not found."));
		
		Page<LecturerCourse> lectCourse=lectCourRepo.findAllByLecturerByCourse(lec,pageable);
		
		//Page<LecturerCourse> page=new PageImpl<LecturerCourse>(lectCourse,pageable,0);
		model.addAttribute("page", lectCourse);
		//model.addAttribute("lecturerId",lecturerId);
		return "view-lecturer-course";
	}
	
	@GetMapping("view-student-course-page/{courseId}/{semId}")//view students in a course and particular semester; 
	public String getStudentCourseByCourseAndSemester(@PathVariable("courseId")int courseId,
			@PathVariable("semId")int semId,
			@PageableDefault(size=5) Pageable pageable,Model model) {
		
		Course course=courseRepo.findById(courseId).orElseThrow(
				() -> new IllegalArgumentException("Course Id: " + courseId + " not found."));
		
		Semester sem=semRepo.findById(semId).orElseThrow(
				() -> new IllegalArgumentException("Semester Id: " + semId + " not found."));
		
		Page<StudentCourse> studCourse=studCourseRepo.findAllByCourseByStudentAndSemesterStudentCourse(course, sem, pageable);
		
		model.addAttribute("page", studCourse);
		model.addAttribute("courseId",courseId);
		model.addAttribute("semId",semId);
		
		return "view-student-course-page";
	}
	
	@GetMapping("student-course-score/{studentId}/{courseId}/{semId}")
	public ModelAndView editStudentCourseScore(@PathVariable("studentId")int studentId,
			@PathVariable("courseId")int courseId,
			@PathVariable("semId")int semId) { //,Model model
		ModelAndView mav=new ModelAndView("edit-student-course-score");
		StudentCourse studCourse=studCourseRepo.findById(new StudentCourseId(studentId,courseId,semId)).get();
		mav.addObject("studentCourse",studCourse);
		
		//mav.addObject("score",studCourse.getScore());
		mav.addObject("studentId", studentId);
		mav.addObject("courseId", courseId);
		mav.addObject("semId", semId);
		
		return mav;
	}
	
	/*@ModelAttribute("studentCourse")StudentCourse studCourse*/
	
	@PostMapping("update-student-course-score/{studentId}/{courseId}/{semId}")
	public String updateStudentCourseScore(
			@PathVariable("studentId")int studentId,
			@PathVariable("courseId")int courseId,
			@PathVariable("semId")int semId,@RequestParam("score")float score) {
		
		StudentCourse studCourse=studCourseRepo.findById(new StudentCourseId(studentId,courseId,semId)).get();
		studCourse.setScore(score);
		studCourseRepo.save(studCourse);
		
		return"redirect:/view-student-course-page/"+courseId+"/"+semId;
	}
}
