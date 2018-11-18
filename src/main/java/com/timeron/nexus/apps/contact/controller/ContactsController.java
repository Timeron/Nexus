package com.timeron.nexus.apps.contact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.dao.PersonDAO;

@Controller
@RequestMapping("/contacts")
public class ContactsController {
	
	static Logger log = Logger.getLogger(
			ContactsController.class.getName());
	
	@Autowired
	PersonDAO personDao;
	
	@ModelAttribute("person")
	private NexusPerson counstruct(){
		return new NexusPerson();
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String ContactsPage(ModelMap model) {
		log.info("Open: contactsHome");
		return "contactsHome";
	}
}
