package SpringCA.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import SpringCA.entities.Course;
import SpringCA.entities.Department;
import SpringCA.entities.Lecturer;
import SpringCA.entities.LecturerCourse;
import SpringCA.entities.LecturerLeave;
import SpringCA.entities.Semester;
import SpringCA.entities.StudentCourse;
import SpringCA.entities.CompositeId.LecturerLeaveId;
import SpringCA.entities.CompositeId.StudentCourseId;
import SpringCA.Repository.CourseRepository;
import SpringCA.Repository.SemesterRepository;
import SpringCA.Repository.StudentCourseRepository;
import SpringCA.Repository.LecturerCourseRepository;
import SpringCA.Repository.LecturerLeaveRepository;
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
	
	@Autowired
	private LecturerLeaveRepository lectLeaveRepo;
	
    
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
	
	@GetMapping("view-lecturer-leave-applications")//get specific lecturer's leave applications; 
	public String getLecturerLeave(@PageableDefault(size=1) Pageable pageable,
			Model model,HttpSession httpSession) {
		
		int lecturerId=(int) httpSession.getAttribute("lecturerId"); //Using lecturerId stored in session variable here.
		
		Page<LecturerLeave> lectLeave=lectLeaveRepo.findAllLecturerLeaveByLecturerByLeave_LecturerId(lecturerId, pageable);
				
		//Page<LecturerCourse> page=new PageImpl<LecturerCourse>(lectCourse,pageable,0);
		model.addAttribute("page", lectLeave);
		//model.addAttribute("lecturerId",lecturerId);
		return "view-lecturer-leave-page";
	}
	
	@GetMapping("/new-lecturer-leave-application")
	public String showAddNewLecturerLeaveApplication(LecturerLeave lecturerLeave) {
		return "add-lecturer-leave-application";
	}

	@PostMapping("/add-lecturer-leave-application")
	public String addLecturerLeaveApplication(@Valid LecturerLeave lectLeave,BindingResult result,HttpSession httpSession) {
		
		if(result.hasErrors()) {
			return "add-lecturer-leave-application";
		}else if(lectLeave.getEndDate()==null||lectLeave.getStartDate()==null) {
			result.rejectValue("endDate", "error.lecturerLeave", "Dates must not be blank.");
			return "add-lecturer-leave-application";
		}else if(lectLeave.getEndDate().isBefore(lectLeave.getStartDate())){
			result.rejectValue("endDate", "error.lecturerLeave", "End date is earlier than start date.");
			return "add-lecturer-leave-application";
		}
		
		int lecturerId=(int) httpSession.getAttribute("lecturerId");
		List<LecturerLeave> lecturerLeaveList=lectLeaveRepo.findAllLecturerLeaveByLecturerId(lecturerId);
		
		for(LecturerLeave existLectLeave: lecturerLeaveList) {
			if((lectLeave.getEndDate().equals(existLectLeave.getEndDate()))||
					(lectLeave.getEndDate().equals(existLectLeave.getStartDate()))||
					(lectLeave.getStartDate().equals(existLectLeave.getStartDate()))||
					(lectLeave.getStartDate().equals(existLectLeave.getEndDate()))||
					(lectLeave.getStartDate().isAfter(existLectLeave.getStartDate()))&&
					(lectLeave.getStartDate().isBefore(existLectLeave.getEndDate()))||
					((lectLeave.getEndDate().isAfter(existLectLeave.getStartDate()))&&
							(lectLeave.getEndDate().isBefore(existLectLeave.getEndDate())))) {
				result.rejectValue("endDate", "error.lecturerLeave", "Dates overlap with existing leave applications.");
				return "add-lecturer-leave-application";
			}
		}

			
		Lecturer lect=lecturerRepo.findByLecturerId(lecturerId);
		
		lectLeave.setLecturerByLeave(lect);
		lectLeave.setStatus("Pending");
		
		lectLeaveRepo.save(lectLeave);
		return "redirect:/view-lecturer-leave-applications";
	}
	
	@GetMapping("/lecturer-leave-application/{lecturerId}/{startDate}")
	public ModelAndView editLecturerLeave(@PathVariable("lecturerId")int lecturerId,
			@PathVariable("startDate")String startDate) { //,Model model
		
		LecturerLeave lectLeave=lectLeaveRepo.findLecturerLeaveByLecturerIdAndStartDate(lecturerId, startDate);
			
		ModelAndView mav=new ModelAndView("edit-lecturer-leave-application");

		mav.addObject("lecturerLeave",lectLeave);
		
		return mav;
	}
	
	@PostMapping("/update-lecturer-leave-application")
	public String updateLecturerLeaveApplication(@ModelAttribute("lecturerLeave")@Valid LecturerLeave lectLeave,
			BindingResult result,HttpSession httpSession) {
		
		if(result.hasErrors()) {
			return "edit-lecturer-leave-application";
		}else if(lectLeave.getEndDate()==null||lectLeave.getStartDate()==null) {
			result.rejectValue("endDate", "error.lecturerLeave", "Dates must not be blank.");
			return "/edit-lecturer-leave-application";
		}else if(lectLeave.getEndDate().isBefore(lectLeave.getStartDate())){
			result.rejectValue("endDate", "error.lecturerLeave", "End date is earlier than start date.");
			return "/edit-lecturer-leave-application";
		}
		
		int lecturerId=(int) httpSession.getAttribute("lecturerId");
		
		LecturerLeave lectLeaveCurrent=lectLeaveRepo.
				findLecturerLeaveByLecturerIdAndStartDate(lecturerId, lectLeave.getStartDate().toString());
		List<LecturerLeave> lecturerLeaveList=lectLeaveRepo.findAllLecturerLeaveByLecturerId(lecturerId);
		lecturerLeaveList.remove(lectLeaveCurrent);
		//int lecturerId=(int) httpSession.getAttribute("lecturerId");
		//List<LecturerLeave> lecturerLeaveList=lectLeaveRepo.findAllLecturerLeaveByLecturerId(lectLeave.getLecturerByLeave().getLecturerId());
		
		for(LecturerLeave existLectLeave: lecturerLeaveList) {
			if((lectLeave.getEndDate().equals(existLectLeave.getEndDate()))||
					(lectLeave.getEndDate().equals(existLectLeave.getStartDate()))||
					(lectLeave.getStartDate().equals(existLectLeave.getStartDate()))||
					(lectLeave.getStartDate().equals(existLectLeave.getEndDate()))||
					(lectLeave.getStartDate().isAfter(existLectLeave.getStartDate()))&&
					(lectLeave.getStartDate().isBefore(existLectLeave.getEndDate()))||
					((lectLeave.getEndDate().isAfter(existLectLeave.getStartDate()))&&
							(lectLeave.getEndDate().isBefore(existLectLeave.getEndDate())))) {
				result.rejectValue("endDate", "error.lecturerLeave", "Dates overlap with existing leave applications.");
				return "/edit-lecturer-leave-application";
			}
		}
		
		//LocalDate endDateLD=LocalDate.parse(endDate);
		//LecturerLeave lectLeave=lectLeaveRepo.findLecturerLeaveByLecturerIdAndStartDate(lecturerId, startDate);
		//lectLeave.setEndDate(endDateLD);
		lectLeaveRepo.delete(lectLeaveCurrent);
		lectLeave.setLecturerByLeave(lecturerRepo.findByLecturerId(lecturerId));
		lectLeave.setStatus("Pending");
		lectLeaveRepo.save(lectLeave);

		return"redirect:/view-lecturer-leave-applications";
	}
	
	@GetMapping("delete-lecturer-leave-application/{lecturerId}/{startDate}")
	public String deleteProduct(@PathVariable("lecturerId")int lecturerId,
			@PathVariable("startDate")String startDate) {
		LecturerLeave lectLeave=lectLeaveRepo.findLecturerLeaveByLecturerIdAndStartDate(lecturerId, startDate);
		lectLeaveRepo.delete(lectLeave);//add a confirmation message pop up.
		
		return "redirect:/view-lecturer-leave-applications";
	}
	
}
