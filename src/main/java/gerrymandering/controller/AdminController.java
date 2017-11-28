package gerrymandering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * Created by yisuo on 10/30/17.
 */
@Controller
public class AdminController {

    @RequestMapping("/index")
    public String index() {
        return "redirect:/www/index.html";
    }

    @RequestMapping("/login")
    public String login(){
        return "loginView";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "adminDashBoard";
    }


    @RequestMapping(value = "/approveUsers", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        return "approveUsers";
    }




    @RequestMapping(value = "/changeEmail", method = RequestMethod.POST)
    public String changeEmail(@RequestParam String oldEmail, @RequestParam String newEmail){
        return "";
    }

    @RequestMapping(value = "/changePw", method = RequestMethod.POST)
    public String changePassword(@RequestParam String email, @RequestParam String pw){
        return "";
    }

    @RequestMapping(value = "/sendInvite", method = RequestMethod.POST)
    public String sendInvite(String email){
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
