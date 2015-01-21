package com.nexus.apps.multiObserver;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexus.apps.multiObserver.form.MultiObserverForm;

@Controller
@RequestMapping("/multiobserver")
public class MultiObserver {

	static Logger log = Logger.getLogger(MultiObserver.class.getName());
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String MultiObserverPage(ModelMap model) {

		MultiObserverForm multiObserverForm = new MultiObserverForm();
		
		model.addAttribute("form", multiObserverForm);

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return "multiObserver";

	}

}
