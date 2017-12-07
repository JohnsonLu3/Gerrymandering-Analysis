package gerrymandering.controller;

import gerrymandering.model.User;
import gerrymandering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EditUserSettingsController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/editSettings", method = RequestMethod.GET)
    public ModelAndView showEditUsers(WebRequest request, Model model) {
        User user = new User();
        ModelAndView editSettings = new ModelAndView("editSettings");
        editSettings.addObject("user", user);
        return editSettings;
    }
}
