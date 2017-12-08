package gerrymandering.controller;

import gerrymandering.model.User;
import gerrymandering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EditUserSettingsController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value="/editSettings", method = RequestMethod.GET)
    public ModelAndView showEditUsers(WebRequest request, Model model, Authentication authentication) {

        User user = userService.findByUsername(authentication.getName());

        ModelAndView editSettings = new ModelAndView("editSettings");
        editSettings.addObject("user", user);
        editSettings.addObject("pValue", user.getPValue());
        editSettings.addObject("compactness", user.getCompactnessThreshold());
        editSettings.addObject("efficiencyGap",user.getEfficiencyGap());
        return editSettings;
    }

    @RequestMapping(value="/saveUserSettings", method = RequestMethod.POST)
    public ModelAndView saveChanges(WebRequest request, Model model, @ModelAttribute("user")User user, Authentication authentication) {

        Long id = user.getId();
        User userToUpdate = userService.findById(id);

        String newEmail = request.getParameter("newUsername");
        String confirmEmail = request.getParameter("confirmUsername");

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");


        double compactnessThreshold = getTestValue( "compactnessThreshold", request);
        double pValue = getTestValue( "pValue", request);
        double EfficiencyGap = getTestValue( "EfficiencyGap", request);
        boolean changesMade = false;

        //save settings
        if(newEmail.equals(confirmEmail) && !newEmail.equals("") && !confirmEmail.equals("")){
            userToUpdate.setUsername(confirmEmail);
            changesMade = true;
        }

        if(newPassword.equals(confirmPassword) && !newPassword.equals("") && !confirmPassword.equals("")){
            userToUpdate.setPassword(bCryptPasswordEncoder.encode(confirmPassword));
            changesMade = true;
        }

        Double userCompactnessThreshold = userToUpdate.getCompactnessThreshold();
        if(userCompactnessThreshold == null){
            userToUpdate.setCompactnessThreshold(compactnessThreshold);
            changesMade = true;
        }else{
            if(compactnessThreshold != -1.0 && compactnessThreshold != userCompactnessThreshold){
                userToUpdate.setCompactnessThreshold(compactnessThreshold);
                changesMade = true;
            }
        }

        Double userPValue = userToUpdate.getPValue();
        if(userPValue == null){
            userToUpdate.setPValue(pValue);
            changesMade = true;
        }else {
            if (pValue != -1.0 && pValue != userPValue) {
                userToUpdate.setPValue(pValue);
                changesMade = true;
            }
        }

        Double userEfficiencyGap = userToUpdate.getEfficiencyGap();
        if(userEfficiencyGap == null){
            userToUpdate.setEfficiencyGap(userEfficiencyGap);
            changesMade = true;
        }else {
            if (EfficiencyGap != -1.0 && EfficiencyGap != userEfficiencyGap) {
                userToUpdate.setEfficiencyGap(EfficiencyGap);
                changesMade = true;
            }
        }

        if(changesMade == true){
            userService.saveUser(userToUpdate);
        }

        return showEditUsers(request, model, authentication);
    }


    private double getTestValue(String testName, WebRequest request){
        double testValue = -1.0;
        if(!request.getParameter(testName).equals("")) {
             testValue = Double.parseDouble(request.getParameter(testName));
        }

        return testValue;
    }
}
