package SpringCA.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import SpringCA.Repository.CourseRepository;
import SpringCA.Repository.LecturerCourseRepository;
import SpringCA.Repository.LecturerRepository;
import SpringCA.Repository.LecturerUserRepository;
import SpringCA.Repository.StudentCourseRepository;
import SpringCA.entities.Course;
import SpringCA.entities.Lecturer;
import SpringCA.entities.LecturerCourse;
import SpringCA.entities.LecturerUser;
import SpringCA.entities.Student;
import SpringCA.entities.StudentCourse;

@Controller
@RequestMapping("/")
public class LecturerController {
	
	@Autowired
	private LecturerUserRepository lectusRepo;
	
	@Autowired
	private LecturerRepository lectRepo;
		
	@Autowired
	private LecturerCourseRepository lectcoRepo;
	
	@Autowired
	private StudentCourseRepository studcoRepo;
	
	@Autowired
	private CourseRepository coRepo;
	
	@Autowired
	public void setLectusRepo(LecturerUserRepository lectusRepo) {
		this.lectusRepo = lectusRepo;
	}
	
	
	
	public void setLectRepo(LecturerRepository lectRepo) {
		this.lectRepo = lectRepo;
	}



	public void setLectcoRepo(LecturerCourseRepository lectcoRepo) {
		this.lectcoRepo = lectcoRepo;
	}



	public void setStudcoRepo(StudentCourseRepository studcoRepo) {
		this.studcoRepo = studcoRepo;
	}



	public void setCoRepo(CourseRepository coRepo) {
		this.coRepo = coRepo;
	}



	@GetMapping("/Login")
	public String Login()
	{
		return "LecturerLogin";
	}
	
	
	@PostMapping("/LoginForm")
	public String LoginForm(String username,String password,Model model)
	{
		System.out.println(username+" "+password);
		if(username==null)
		   return "LecturerLogin";
		
		LecturerUser lectus=lectusRepo.findLecturer(username, password);
		if(lectus==null)
		{
			 return "LecturerLogin";
		}
		else
		{
			model.addAttribute("LectId",lectus.getUserId().getUser());
			return "LecturerHome";
		}
	}
	
	@GetMapping("/LecturerHome")
	public String LecturerHome()
	{
		return "LecturerHome";
	}
	
	@GetMapping("/ListofCourses")
	public String ListofCourses(Model model,int Id)
	{
		//Lecturer l=lectRepo.findByLectId(Id);
		List<LecturerCourse> lcList=lectcoRepo.findByLectId(Id);
		List<Course> clist=new ArrayList<Course>();
		for(LecturerCourse lc : lcList)
		{
			Course c=lc.getCourseByLecturer();
			clist.add(c);
		}
		model.addAttribute("clist", clist);
		
		return "ListofCourses";
	}
	
	@GetMapping("/ListofStudents")
	public String ListofStudents(Model model,int Id)
	{
		//Lecturer l=lectRepo.findByLectId(Id);
		List<StudentCourse> scList=studcoRepo.findByCourseId(Id);
		List<Student> slist=new ArrayList<Student>();
		for(StudentCourse sc : scList)
		{
			Student s=sc.getStudentByCourse();
			slist.add(s);
		}
		model.addAttribute("slist", slist);
		model.addAttribute("courseid", Id);
		
		return "ListofStudents";
	}
	
	@GetMapping("/Student/edit")
	public String StudentEdit(Model model,int StudID,int CourseId)
	{
		
		model.addAttribute("studid", StudID);
		model.addAttribute("courseid", CourseId);
		return "StudentEdit";
	}
	
	@PostMapping("/Student/update")
	public String StudentUpdate(Model model,int score,int studid,int courseid)
	{
		StudentCourse sc=studcoRepo.getStudentCourse(courseid, studid);
		sc.setScore(score);
		studcoRepo.save(sc);
		return "StudentUpdate";
	}
	

}
