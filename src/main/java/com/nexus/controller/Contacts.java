package com.nexus.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexus.context.contact.NexusCalendar;
import com.nexus.dao.Implementation.PersonDAO;
import com.nexus.dao.entity.NexusPerson;

@Controller
@RequestMapping("/contacts")
public class Contacts {
	
	static Logger log = Logger.getLogger(
			Contacts.class.getName());
	
	@Autowired
	PersonDAO personDao;
	
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
		NexusPerson nexusPerson = new NexusPerson();
		NexusCalendar nexusCalendar = new NexusCalendar();
		model.addAttribute("person", nexusPerson);
		model.addAttribute("years", nexusCalendar.getYears(150));
		model.addAttribute("months", nexusCalendar.getMonths());
		model.addAttribute("days", nexusCalendar.getDays());
		return "contactsNew";
	}
	
	@RequestMapping(value="/newContact", method = RequestMethod.POST)
	public String newContactsPagePost(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("person") NexusPerson nexusPerson) {
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		
		String birthday = nexusPerson.getBirthdayYear()+"/"+nexusPerson.getBirthdayMonth()+"/"+nexusPerson.getBirthdayDay();
		String nameDay = nexusPerson.getNameDayMonth()+"/"+nexusPerson.getNameDayDay();
		
		Date birthdayDate = null;
		try {
			birthdayDate = formatter.parse(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date nameDayDate = null;
		try {
			nameDayDate = formatter.parse(nameDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		nexusPerson.setBirthday(birthdayDate);
		nexusPerson.setNameDay(nameDayDate);
		
		personDao.savePerson(nexusPerson);
		
		log.info("Person added: "+nexusPerson);
		return "contactsNew";
	}
	
	@RequestMapping(value="/searchContact", method = RequestMethod.GET)
	public String SearchContactsPage(ModelMap model) {
		return "searchContacts";
	}
	
	@RequestMapping(value="/searchContact", method = RequestMethod.POST)
	public String SearchContactsResoultsPage(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute NexusPerson nexusPerson) {
		List<NexusPerson> searchedPersonList = new ArrayList<NexusPerson>();
		searchedPersonList = personDao.searchPerson(nexusPerson);
		request.setAttribute("message", searchedPersonList);
		return "searchContactsResoults";
	}

}
