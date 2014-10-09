package com.nexus.controller.jtask;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/jtask")
public class JTaskControler {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String mainSite(Model model){
		return "jtaskMainSite";
	}
	
}
