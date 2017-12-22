package gerrymandering.controller;

import gerrymandering.model.Authorities;
import gerrymandering.model.User;
import gerrymandering.service.AuthoritiesService;
import gerrymandering.service.EmailServiceImpl;
import gerrymandering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class InviteAdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;


    private RandomValueStringGenerator keyGenerator = new RandomValueStringGenerator();

    @RequestMapping(value="/inviteAdmins", method = RequestMethod.GET)
    public String showEditUsers(WebRequest request, Model model) {
        return "inviteAdmins";
    }

    @RequestMapping(value="/sendInvite", method = RequestMethod.POST)
    public String sendInvite(HttpServletRequest request, Model model){

        String email = request.getParameter("Email");
        String password = request.getParameter("Password");

        User user = new User();
        user.setUsername(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEnabled(false);

        keyGenerator.setLength(40);
        user.setActivationKey(keyGenerator.generate());

        userService.saveUser(user);

        Authorities authorities = new Authorities();
        authorities.setUserName(email);
        authorities.setRole("ROLE_ADMIN");
        authoritiesService.saveAuthorities(authorities);

        return "inviteAdmins?sent=" + sendEmail(user, request);
    }


    public boolean sendEmail(User user, HttpServletRequest request){

        try{
            String subject = "Gerrymandering Analysis Admin Invitation";
            String emailBody = "Hello you have been invited to be an admin at Gerrymandering Analysis \n"
                    + "click on the link below to activate your account. \n\n";
            String link = request.getScheme() + "://" + request.getServerName() + ":8080/registrationConfirmed?key=" + user.getActivationKey();

            SimpleMailMessage emailToSend = new SimpleMailMessage();
            emailToSend.setTo(user.getUsername());
            emailToSend.setSubject(subject);
            emailToSend.setText(emailBody + link);
            emailToSend.setFrom("cse308sbu@gmail.com");

            emailService.sendEmail(emailToSend);

        }catch (MailException ex){
            System.err.println(ex.getMessage());
            return false;
        }

        return true;
    }

}
