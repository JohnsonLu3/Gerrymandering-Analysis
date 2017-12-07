package gerrymandering.controller;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import gerrymandering.model.Authorities;
import gerrymandering.model.User;
import gerrymandering.service.AuthoritiesService;
import gerrymandering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EditUsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesService authoritiesService;

    @RequestMapping(value="/editUsers", method = RequestMethod.GET)
    public ModelAndView showEditUsers(WebRequest request, Model model) {
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
    public ModelAndView saveChanges(WebRequest request, Model model, @ModelAttribute("user")User user) {

        for (String id : request.getParameterValues("id")) {
            long userId = Integer.parseInt(id);
            String username = request.getParameter("username_" + id);
            String role = request.getParameter("role_" + id);
            Boolean enabled = request.getParameter("enabled_" + id) != null;


            User userToUpdate = userService.findById(userId);

            Authorities auth = authoritiesService.findByUsername(userToUpdate.getUsername());
            if(!role.equals("ROLE_ADMIN")) {
                if (auth != null && !role.equals(auth.getRole())) {
                    if(role.equals("ROLE_ADMIN") || role.equals("ROLE_ADVANCE")) {
                        auth.setRole(role);
                        authoritiesService.saveAuthorities(auth);
                    }
                }
                if(userToUpdate.getEnabled() != enabled){
                    userToUpdate.setEnabled(enabled);
                    userService.saveUser(userToUpdate);
                }

                if (!username.equals("")) {
                    userToUpdate.setUsername(username);
                    userService.saveUser(userToUpdate);
                }
            }
        }
        return showEditUsers(request, model);
    }

    @RequestMapping(value="/deleteUser", method = RequestMethod.POST)
    public ModelAndView deleteUser(WebRequest request, Model model, @ModelAttribute("user")User user) {
        long id = -1;

        if(user.getRole().equals("ROLE_ADMIN")) {
            if (user != null) {
                id = user.getId();
            }

            if (id != -1) {
                userService.deleteUserById(id);
            }
        }

        return showEditUsers(request, model);
    }
}
