package com.nexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexus.dao.DaoImp;

@Controller
@RequestMapping("/contacts")
public class Contacts {
	
	@Autowired
	private DaoImp dao;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String ContactsPage(ModelMap model) {
		return "contactsHome";
	}

}
