package gerrymandering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@RequestMapping("/")
	public String root() {
		return "redirect:/www/index.html";
	}

	@RequestMapping("/www/")
	public String staticBase() {
		return "redirect:/www/index.html";
	}
}