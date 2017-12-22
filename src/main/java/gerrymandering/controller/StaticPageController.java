package gerrymandering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class StaticPageController {

	@RequestMapping("/greeting")
	public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@RequestMapping("/analyze")
	public String analyze() {
		return "basic user three test results";
	}

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String root() {
		return "index";
	}

	@RequestMapping("/www/")
	public String staticBase() {
		return "redirect:/www/index.html";
	}
}
