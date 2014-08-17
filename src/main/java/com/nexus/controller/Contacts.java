package com.nexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexus.dao.Implementation.PersonDAO;
import com.nexus.dao.entity.NexusPerson;

@Controller
@RequestMapping("/contacts")
public class Contacts {
	
	@Autowired
	PersonDAO dao;
	
	@ModelAttribute("person")
	private NexusPerson counstruct(){
		return new NexusPerson();
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String ContactsPage(ModelMap model) {
		return "contactsHome";
	}
	
	@RequestMapping(value="/newContact", method = RequestMethod.GET)
	public String newContactsPageGet(ModelMap model) {
		return "contactsNew";
	}
	
	@RequestMapping(value="/newContact", method = RequestMethod.POST)
	public String newContactsPagePost(ModelMap model, @ModelAttribute NexusPerson nexusPerson) {
		dao.savePerson(nexusPerson);
		return "contactsNew";
	}

}
