package com.warpaint.challengeservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MainController {

	@RequestMapping(value="/", produces="application/json")
	@ResponseBody
	public RedirectView index() {
		return new RedirectView("info");
	}
}
