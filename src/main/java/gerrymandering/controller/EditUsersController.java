package gerrymandering.controller;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import gerrymandering.model.Authorities;
import gerrymandering.model.User;
import gerrymandering.service.AuthoritiesService;
import gerrymandering.service.EmailServiceImpl;
import gerrymandering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EditUsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    private EmailServiceImpl emailService;

    @RequestMapping(value="/editUsers", method = RequestMethod.GET)
    public ModelAndView showEditUsers(HttpServletRequest request, Model model) {
        Iterable<User> iterable = userService.findAll();
        List<User> users = new ArrayList<User>();
        if(iterable != null) {
            for(User e: iterable) {
                Authorities auth = authoritiesService.findByUsername(e.getUsername());
                e.setRole(auth.getRole());
                if(e.getRole().equals("ROLE_ADVANCE")){
                    users.add(e);
                }
            }
        }
        ModelAndView editUsers = new ModelAndView("editUsers");
        editUsers.addObject("users", users);

        return editUsers;
    }

    @RequestMapping(value="/saveChanges", method = RequestMethod.POST)
    public ModelAndView saveChanges(HttpServletRequest request, Model model, @ModelAttribute("user")User user) {

        for (String id : request.getParameterValues("id")) {
            long userId = Integer.parseInt(id);
            String username = request.getParameter("username_" + id);
            String role = request.getParameter("role_" + id);
            Boolean enabled = request.getParameter("enabled_" + id) != null;


            User userToUpdate = userService.findById(userId);

            Authorities auth = authoritiesService.findByUsername(userToUpdate.getUsername());
            if(userToUpdate != null && auth != null ) {
                if (!auth.getRole().equals("ROLE_ADMIN")) {
                    if (!role.equals(auth.getRole())) {
                        if (role.equals("ROLE_ADMIN") || role.equals("ROLE_ADVANCE")) {
                            auth.setRole(role);
                            authoritiesService.saveAuthorities(auth);
                        }
                    }
                    if (userToUpdate.getEnabled() != enabled) {
                        userToUpdate.setEnabled(enabled);
                        userService.saveUser(userToUpdate);
                        sendEmail(userToUpdate, request, enabled);
                    }

                    if (!username.equals("")) {
                        userToUpdate.setUsername(username);
                        userService.saveUser(userToUpdate);
                    }
                }
            }
        }
        return showEditUsers(request, model);
    }

    @RequestMapping(value="/deleteUser", method = RequestMethod.POST)
    public ModelAndView deleteUser(HttpServletRequest request, Model model, @ModelAttribute("user")User user) {

        if(user != null) {
            long id = user.getId();
            User userToDelete = userService.findById(id);
            Authorities auth = authoritiesService.findByUsername(userToDelete.getUsername());

            if (!auth.getRole().equals("ROLE_ADMIN")) {
                userService.deleteUserById(id);
            }
        }
        return showEditUsers(request, model);
    }



    public boolean sendEmail(User user, HttpServletRequest request ,boolean enabled){

        try{
            String subject = "Gerrymandering Analysis Account Status";
            String emailBody = "Your account " + user.getUsername() + " has been ";

            if(enabled == true){
                emailBody += "activatived by one of our admins. Click the link below to login to your account! \n";
            }else{
                emailBody += "de-activatived by one of our admins. \n";
            }

            String link = request.getScheme() + "://" + request.getServerName() + ":8080/login";

            SimpleMailMessage emailToSend = new SimpleMailMessage();
            emailToSend.setTo(user.getUsername());
            emailToSend.setSubject(subject);
            emailToSend.setText(emailBody + link);
            emailToSend.setFrom("cse308sbu@gmail.com");

            emailService.sendEmail(emailToSend);

        }catch (MailException ex){
            return false;
        }

        return true;
    }
}
