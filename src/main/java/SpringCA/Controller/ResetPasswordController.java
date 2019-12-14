package SpringCA.Controller;

import SpringCA.Repository.*;
import SpringCA.Service.EmailServiceImpl;
import SpringCA.Service.UserServiceImpl;
import SpringCA.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import SpringCA.model.ResetPassword;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/resetPassword")
public class ResetPasswordController {

    private StudentUserRepository studentUserRepository;
    private PasswordResetTokenStudentRepository tokenStudentRepository;
    private LecturerUserRepository lecturerUserRepository;
    private PasswordResetTokenLecturerRepository tokenLecturerRepository;
    private AdminUserRepository adminUserRepository;
    private PasswordResetTokenAdminRepository tokenAdminRepository;
    private UserServiceImpl userService;
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    public ResetPasswordController(StudentUserRepository studentUserRepository, PasswordResetTokenStudentRepository tokenStudentRepository,
                                   LecturerUserRepository lecturerUserRepository, PasswordResetTokenLecturerRepository tokenLecturerRepository,
                                   AdminUserRepository adminUserRepository, PasswordResetTokenAdminRepository tokenAdminRepository,
                                   UserServiceImpl userService, EmailServiceImpl emailServiceImpl) {
        this.studentUserRepository = studentUserRepository;
        this.tokenStudentRepository = tokenStudentRepository;
        this.lecturerUserRepository = lecturerUserRepository;
        this.tokenLecturerRepository = tokenLecturerRepository;
        this.adminUserRepository = adminUserRepository;
        this.tokenAdminRepository = tokenAdminRepository;
        this.userService = userService;
        this.emailServiceImpl = emailServiceImpl;
    }


    @GetMapping(value = "/{id}/{username}")
    public String getResetPasswordUrl(HttpServletRequest request, @PathVariable("username") String username,
                                      @PathVariable("id") String id, RedirectAttributes redirectAttributes, Model model) {
        String token = UUID.randomUUID().toString();
        if(id.equals("student")) {
            if(studentUserRepository.findByUsername(username) == null){
                redirectAttributes.addFlashAttribute("message", "Invalid username");
                return "redirect:/login_student";
            }else {
                StudentUser studentUser = studentUserRepository.findByUsername(username);
                userService.createPasswordResetTokenForStudent(studentUser, token);
                model.addAttribute("email", studentUser.getStudentUser().getEmail());
            }
        }
        if(id.equals("lecturer")) {
            if(lecturerUserRepository.findByUsername(username) == null){
                redirectAttributes.addFlashAttribute("message", "Invalid username");
                return "redirect:/login_lecturer";
            }else {
                LecturerUser lecturerUser = lecturerUserRepository.findByUsername(username);
                userService.createPasswordResetTokenForLecturer(lecturerUser, token);
                model.addAttribute("email", lecturerUser.getLecturerUser().getEmail());
            }
        }
        if(id.equals("admin")) {
            if(adminUserRepository.findByUsername(username) == null){
                redirectAttributes.addFlashAttribute("message", "Invalid username");
                return "redirect:/login_admin";
            }else {
                AdminUser adminUser = adminUserRepository.findByUsername(username);
                userService.createPasswordResetTokenForAdmin(adminUser, token);
                model.addAttribute("email", adminUser.getAdminUser().getEmail());
            }
        }
        return "resetPasswordUrl";
    }

    @GetMapping(value = "/{id}/{username}/{token}")
    public String resetPassword(@PathVariable("username") String username, @PathVariable("token") String token,
                                @PathVariable("id") String id,
                                Model model, ResetPassword resetPassword){
        model.addAttribute("id", id);
        if(id.equals("student")){
            Iterable<PasswordResetTokenStudent> tokenStudents = tokenStudentRepository.findByStudentUser_Username(username);
            for(PasswordResetTokenStudent s: tokenStudents){
                if(s.getToken().equals(token) && s.getExpiryDate().after(new Date())){
                    model.addAttribute("username", s.getStudentUser().getUsername());
                    return "resetPassword";
                }
            }
        }
        if(id.equals("lecturer")){
            Iterable<PasswordResetTokenLecturer> tokenLecturers = tokenLecturerRepository.findByLecturerUser_Username(username);
            for(PasswordResetTokenLecturer l: tokenLecturers){
                if(l.getToken().equals(token) && l.getExpiryDate().after(new Date())){
                    model.addAttribute("username", l.getLecturerUser().getUsername());
                    return "resetPassword";
                }
            }
        }
        if(id.equals("admin")){
            Iterable<PasswordResetTokenAdmin> tokenAdmins = tokenAdminRepository.findByAdminUser_Username(username);
            for(PasswordResetTokenAdmin a: tokenAdmins){
                if(a.getToken().equals(token) && a.getExpiryDate().after(new Date())){
                    model.addAttribute("username", a.getAdminUser().getUsername());
                    return "resetPassword";
                }
            }
        }

        return "expiredLink";
    }

    @PostMapping(value = "/{id}/{username}")
    public String resetPassword(@PathVariable("username") String username, @PathVariable("id") String id,
                                @Valid ResetPassword resetPassword,
                                BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("username", username);
            return "resetPassword";
        }
        if(id.equals("student")) userService.resetStudentPassword(username, resetPassword.getPassword());
        if(id.equals("lecturer")) userService.resetLecturerPassword(username, resetPassword.getPassword());
        if(id.equals("admin")) userService.resetAdminPassword(username, resetPassword.getPassword());
        return "index";
    }


}
