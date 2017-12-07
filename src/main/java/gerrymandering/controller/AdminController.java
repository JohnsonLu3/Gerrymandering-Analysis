package gerrymandering.controller;

import gerrymandering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import gerrymandering.model.User;

import java.util.Map;

/**
 * Created by yisuo on 10/30/17.
 */
@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(){
        return "loginView";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "adminDashBoard";
    }


    @RequestMapping(value = "/changeEmail", method = RequestMethod.POST)
    public String changeEmail(@RequestParam String oldEmail, @RequestParam String newEmail){

        User user = userService.findByUsername(oldEmail);
        user.setUsername(newEmail);

        userService.saveUser(user);

        return "";
    }

    @RequestMapping(value = "/changePw", method = RequestMethod.POST)
    public String changePassword(@RequestParam String email, @RequestParam String pw){

        User user = userService.findByUsername(email);
        user.setPassword(pw);

        userService.saveUser(user);
        return "";
    }

    @RequestMapping(value = "/sendNotification", method = RequestMethod.POST)
    public String sendNotification(String email, String notification){
        return "";
    }

    @RequestMapping(value = "/statesVisited", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> statesVisited(){
        return null;
    }

    @RequestMapping(value = "/districtsVisited", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> districtsVisited(@RequestParam String stateName){
        return null;
    }

    @RequestMapping(value = "/whatIfStatesCombined", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> whatIfStatesCombined(@RequestParam String stateName){
        return null;
    }
//    @RequestMapping("/logout")
//    public String logout() {
//        return "redirect:/www/index.html";
//    }
//

}
