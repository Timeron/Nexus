package com.nexus.controller.multiObserver;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/multiobserver")
public class MultiObserver {

	static Logger log = Logger.getLogger(MultiObserver.class.getName());
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String MultiObserverPage(ModelMap model) {

		model.addAttribute("message",
				"Admin home");

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return "multiObserver";

	}

}
