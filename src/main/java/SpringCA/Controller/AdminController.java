package SpringCA.Controller;

import SpringCA.Repository.*;
import SpringCA.Service.AdminServiceImpl;
import SpringCA.Service.FileSystemStorageService;
import SpringCA.Service.UserServiceImpl;
import SpringCA.csv.CsvWriter;
import SpringCA.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private StudentRepository studentRepository;
    private SemesterRepository semesterRepository;
    private DegreeRepository degreeRepository;
    private CourseRepository courseRepository;
    private LecturerRepository lecturerRepository;
    private LecturerCourseRepository lecturerCourseRepository;
    private StudentCourseRepository studentCourseRepository;
    private DepartmentRepository departmentRepository;
    private AdminUserRepository adminUserRepository;
    private StudentUserRepository studentUserRepository;
    private LecturerUserRepository lecturerUserRepository;
    private UserServiceImpl userService;
    private CsvWriter csvWriter;
    private AdminServiceImpl adminService;

    @Autowired
    private FileSystemStorageService storageService;

    private int size = 3;

    @Autowired
    public AdminController(StudentRepository studentRepository, SemesterRepository semesterRepository,
                           DegreeRepository degreeRepository, CourseRepository courseRepository, AdminUserRepository adminUserRepository,
                           LecturerRepository lecturerRepository, LecturerCourseRepository lecturerCourseRepository,
                           StudentCourseRepository studentCourseRepository, DepartmentRepository departmentRepository,
                           StudentUserRepository studentUserRepository, LecturerUserRepository lecturerUserRepository, UserServiceImpl userService,
                           CsvWriter csvWriter, AdminServiceImpl adminService) {
        this.studentRepository = studentRepository;
        this.semesterRepository = semesterRepository;
        this.degreeRepository = degreeRepository;
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
        this.lecturerCourseRepository = lecturerCourseRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.departmentRepository = departmentRepository;
        this.studentUserRepository = studentUserRepository;
        this.lecturerUserRepository = lecturerUserRepository;
        this.userService = userService;
        this.csvWriter = csvWriter;
        this.adminUserRepository = adminUserRepository;
        this.adminService = adminService;
    }



    @ModelAttribute(name = "semesters")
    public Iterable<Semester> getSemesters() {
        return semesterRepository.findAll();
    }

    @ModelAttribute(name = "degrees")
    public Iterable<Degree> getDegrees() {
        return degreeRepository.findAll();
    }

    @ModelAttribute(name = "students")
    public Iterable<Student> getStudents() {
        return studentRepository.findAll(PageRequest.of(0, size));
    }

    @ModelAttribute(name = "courses")
    public Iterable<Course> getCourses() {
        return courseRepository.findAll();
    }

    @ModelAttribute(name = "lecturers")
    public Iterable<Lecturer> getLecturers() {
        return lecturerRepository.findAll();
    }

    @ModelAttribute(name = "departments")
    public Iterable<Department> getDepartments() {
        return departmentRepository.findAll();
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

    @GetMapping("/student/list")
    public String studentList() {
        return "redirect:/admin/student/list/page/1";
    }

    @GetMapping("/lecturer/list")
    public String courseList() {
        return "redirect:/admin/lecturer/list/page/1";
    }

    @GetMapping("/course/list")
    public String lecturerList() {
        return "redirect:/admin/course/list/page/1";
    }

    @GetMapping(value = "/profile")
    public String getProfile(Model model){
        AdminUser adminUser = adminUserRepository.findByUsername(userService.getUsername());
        model.addAttribute("adminUser", adminUser);
//        if(getFiles() != null) model.addAttribute("avatar", getFiles().getHref());
//        System.out.println(getFiles().getHref());
        return "admin/adminProfile";
    }

//    @GetMapping(value = "/changePassword/{username}")
//    public String changePassword(@PathVariable("username") String username, Model model, ResetPassword resetPassword){
//        model.addAttribute("username", username);
//        model.addAttribute("security", "admin");
//        return "resetPassword";
//    }
//
//    @PostMapping(value = "/changePassword/{username}")
//    public String resetPassword(@PathVariable("username") String username,
//                                @Valid ResetPassword resetPassword,
//                                BindingResult result, Model model){
//        if(result.hasErrors()){
//            model.addAttribute("username", username);
//            return "resetPassword";
//        }
//        userService.resetAdminPassword(username, resetPassword.getPassword());
//        return "redirect:/admin/profile";
//    }

//    @PostMapping(value = "/profile/uploadFile")
//    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {
//        redirectAttributes.addFlashAttribute("file", file);
//        String filePath = request.getServletContext().getRealPath("/");
//        System.out.println(filePath);
//        try {
//            file.transferTo(new File(filePath));
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return "redirect:/admin/profile";
//    }
//
//    @PostMapping(value = "/img/upload")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
//        storageService.store(file, userService.getUsername());
//        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
//        return "redirect:/admin/profile";
//    }
//
//
//    public HRefModel getFiles() {
//        List<Path> lodf = new ArrayList<>();
//        List<HRefModel> uris = new ArrayList<>();
//
//        try {
//            lodf = storageService.listSourceFiles(Paths.get(userService.getUsername()));
//            for(Path pt : lodf) {
//                HRefModel href = new HRefModel();
//                href.setHref(MvcUriComponentsBuilder
//                        .fromMethodName(AdminController.class, "serveFile", pt.getFileName().toString())
//                        .build()
//                        .toString());
//
//                href.setHrefText(pt.getFileName().toString());
//                uris.add(href);
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        if(uris != null) return uris.get(0);
//        return null;
//    }
//
//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//        Resource file = storageService.loadAsResource(filename, userService.getUsername());
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//                .body(file);
//    }


    @GetMapping(value = "/{type}/list/page/{page}")
    public String listObjectsPageByPage(@PathVariable("page") int page, @PathVariable("type") String type, Model model) {
        PageRequest pageable = PageRequest.of(page - 1, 3);
        int totalPages = 0;
        if (type.equals("student")) {
            Page<Student> studentPages = studentRepository.findAll(pageable);
            totalPages = studentPages.getTotalPages();
            model.addAttribute("studentList", studentPages.getContent());
        }
        if (type.equals("lecturer")) {
            Page<Lecturer> lecturerPages = lecturerRepository.findAll(pageable);
            totalPages = lecturerPages.getTotalPages();
            model.addAttribute("lecturerList", lecturerPages.getContent());
        }
        if (type.equals("course")) {
            Page<Course> coursePages = courseRepository.findAll(pageable);
            totalPages = coursePages.getTotalPages();
            model.addAttribute("courseList", coursePages.getContent());
        }
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("page", page);
        }
        return "admin/" + type + "List";
    }

    @GetMapping(value = "/{type}/list/page/0")
    public String page0(@PathVariable("type") String type) {
        return "redirect:/admin/" + type + "/list/page/1";
    }


    @GetMapping("/student/addStudent")
    public String addStudent(Student student, Model model) {
        model.addAttribute("id", 0);
        return "admin/studentForm";
    }

    @GetMapping("/course/addCourse")
    public String addCourse(Course course, Model model) {
        model.addAttribute("id", 0);
        return "admin/courseForm";
    }

    @GetMapping("/student/addLecturer")
    public String addLecturer(Lecturer lecturer, Model model) {
        model.addAttribute("id", 0);
        return "admin/lecturerForm";
    }

    @GetMapping("/student/{page}/edit/{id}")
    public String updateStudent(@PathVariable("id") int id, @PathVariable("page") int page, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("student", student);
        model.addAttribute("id", id);
        model.addAttribute("page", page);
        return "admin/studentForm";
    }

    @GetMapping("/course/{page}/edit/{id}")
    public String updateCourse(@PathVariable("id") int id, @PathVariable("page") int page, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));

        model.addAttribute("course", course);
        model.addAttribute("id", id);
        model.addAttribute("page", page);
        return "admin/courseForm";
    }

    @GetMapping("/lecturer/{page}/edit/{id}")
    public String updateLecturer(@PathVariable("id") int id, @PathVariable("page") int page, Model model) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lecturer Id:" + id));

        model.addAttribute("lecturer", lecturer);
        model.addAttribute("id", id);
        model.addAttribute("page", page);
        return "admin/lecturerForm";
    }

    @PostMapping("/student/{page}/save/{id}")
    public String updateStudent(@PathVariable("id") int id, @PathVariable("page") int page, @Valid Student student,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "admin/studentForm";
        }
        if (id != 0) student.setStudentId(id);
        studentRepository.save(student);
        if (id == 0) userService.setStudentUser(student);
        if (page == 0) {
            int n;
            if (studentRepository.count() % size == 0) n = (int) studentRepository.count() / size;
            else n = (int) Math.floor(studentRepository.count() / size) + 1;
            return "redirect:/admin/student/list/page/" + n;
        } else return "redirect:/admin/student/list/page/" + page;
    }

    @PostMapping("/course/{page}/save/{id}")
    public String updateCourse(@PathVariable("id") int id, @PathVariable("page") int page, @Valid Course course,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "admin/courseForm";
        }
        if (id != 0) course.setCourseId(id);
        courseRepository.save(course);
        if (page == 0) {
            int n;
            if (courseRepository.count() % size == 0) n = (int) courseRepository.count() / size;
            else n = (int) Math.floor(courseRepository.count() / size) + 1;
            return "redirect:/admin/course/list/page/" + n;
        } else return "redirect:/admin/course/list/page/" + page;

    }

    @PostMapping("/lecturer/{page}/save/{id}")
    public String updateLecturer(@PathVariable("id") int id, @PathVariable("page") int page, @Valid Lecturer lecturer,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "admin/lecturerForm";
        }
        if (id != 0) lecturer.setLecturerId(id);
        lecturerRepository.save(lecturer);
        if (id == 0) userService.setLecturerUser(lecturer);
        if (page == 0) {
            int n;
            if (lecturerRepository.count() % size == 0) n = (int) lecturerRepository.count() / size;
            else n = (int) Math.floor(lecturerRepository.count() / size) + 1;
            return "redirect:/admin/lecturer/list/page" + n;
        } else return "redirect:/admin/lecturer/list/page/" + page;
    }

    @GetMapping("/student/{page}/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id, @PathVariable("page") int page, Model model) {
        adminService.deleteStudent(id);
        if ((studentRepository.count() % size == 0) && (studentRepository.count() / size == (page - 1))) page -= 1;
        return "redirect:/admin/student/list/page/" + page;
    }

    @GetMapping("/course/{page}/delete/{id}")
    public String deleteCourse(@PathVariable("id") int id, @PathVariable("page") int page, Model model) {
        adminService.deleteCourse(id);
        if ((courseRepository.count() % size == 0) && (courseRepository.count() / size == (page - 1))) page -= 1;
        return "redirect:/admin/course/list/page/" + page;
    }

    @GetMapping("/lecturer/{page}/delete/{id}")
    public String deleteLecturer(@PathVariable("id") int id, @PathVariable("page") int page) {
        adminService.deleteLecturer(id);
        if ((lecturerRepository.count() % size == 0) && (lecturerRepository.count() / size == (page - 1))) page -= 1;
        return "redirect:/admin/lecturer/list/page/" + page;
    }

    @GetMapping("/course/detail/{id}")
    public String getCourseSemesters(@PathVariable("id") int id, Model model) {
        Set<Semester> semesters = adminService.getSemestersForCourse(id);
                Course course = courseRepository.findByCourseId(id);
        model.addAttribute("courseSemesters", semesters);
        model.addAttribute("course", course);
        return "admin/course-semester";
    }

    @GetMapping("/course/detail/{courseId}/{semesterId}/page/{page}")
    public String getCourseDetails(@PathVariable("courseId") int courseId, @PathVariable("page") int page,
                                   @PathVariable("semesterId") int semesterId, Model model) {
        Iterable<LecturerCourse> lecturerCourses =
                lecturerCourseRepository.findByCourseByLecturer_CourseIdAndSemesterLecturerCourse_SemesterId(courseId, semesterId);
        Course course = courseRepository.findByCourseId(courseId);
        model.addAttribute("course", course);
        model.addAttribute("semesterId", semesterId);
        model.addAttribute("lecturerList", lecturerCourses);

        Pageable pageable = PageRequest.of(page - 1, 3);
        Page<StudentCourse> studentCoursePages = studentCourseRepository.findByCourseByStudent_CourseIdAndSemesterStudentCourse_SemesterIdAndStatus(courseId, semesterId, "Approved", pageable);
        int totalStudentCoursePages = studentCoursePages.getTotalPages();
        model.addAttribute("studentCourseList", studentCoursePages.getContent());

        if (totalStudentCoursePages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalStudentCoursePages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("page", page);
        }
        return "admin/courseDetail";
    }

    @GetMapping("/course/detail/{courseId}/{semesterId}/page/{page}/downloadCSV")
    public void downloadCSV(@PathVariable("courseId") int courseId, @PathVariable("page") int page,
                            @PathVariable("semesterId") int semesterId,
                            HttpServletResponse res) throws IOException {
        csvWriter.writeCsv(courseId, semesterId, res);
    }

    @GetMapping("/course/detail/{id}/addSemester")
    public String addSemester(@PathVariable("id") int id, Model model) {
        LecturerCourse lecturerCourse = new LecturerCourse();
        lecturerCourse.setCourseByLecturer(courseRepository.findByCourseId(id));
        model.addAttribute("lecturerCourse", lecturerCourse);
        return "admin/courseSemesterForm";
    }


    @PostMapping("/course/detail/{id}/addSemester")
    public String addSemester(@Valid LecturerCourse lecturerCourse, Model model, BindingResult result,
                              @PathVariable("id") int id) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "admin/courseSemesterForm";
        }
        lecturerCourse.setCourseByLecturer(courseRepository.findByCourseId(id));
        lecturerCourseRepository.save(lecturerCourse);
        return "redirect:/admin/course/detail/{id}";
    }

    @GetMapping("/student/{id}}")
    public String studentDetail(@PathVariable("id") int id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        Iterable<StudentCourse> coursesByStudent = studentCourseRepository.findByStudentByCourse_StudentId(id);
        model.addAttribute("student", student);
        model.addAttribute("coursesByStudent", coursesByStudent);
        return "admin/studentDetail";
    }

    @GetMapping("/lecturer/{id}}")
    public String lecturerDetail(@PathVariable("id") int id, Model model) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lecturer Id:" + id));
        Set<LecturerCourse> coursesByLecturer = lecturerCourseRepository.findByLecturerByCourse_LecturerId(id);
        model.addAttribute("lecturer", lecturer);
        model.addAttribute("coursesByLecturer", coursesByLecturer);
        return "admin/lecturerDetail";
    }

    @GetMapping("/course/registration/review")
    public String getPendingCourses(Model model){
        Iterable<StudentCourse> pendingCourses =
                studentCourseRepository.findBySemesterStudentCourse_SemesterIdAndStatus(
                        getNextSemester().getSemesterId(), "Pending");
        if(pendingCourses != null)model.addAttribute("pendingCourses", pendingCourses);
        return "admin/courseRegistrationReview";
    }

    @GetMapping("/course/{action}/{studentId}/{semesterId}/{courseId}")
    public String reviewCourse(@PathVariable("studentId") int studentId, @PathVariable("semesterId") int semesterId,
                               @PathVariable("courseId") int courseId, @PathVariable("action") String action){
        adminService.reviewCourse(studentId, semesterId, courseId, action);
        return "redirect:/admin/course/registration/review";

    }
}
