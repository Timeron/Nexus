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
import org.apache.velocity.runtime.directive.Parse;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
		log.info("Open: contactsHome");
		return "contactsHome";
	}
	
	@RequestMapping(value="/newContact", method = RequestMethod.GET)
	public String newContactsPageGet(ModelMap model) {
		NexusPerson nexusPerson = new NexusPerson();
		NexusCalendar nexusCalendar = new NexusCalendar();
		model.addAttribute("person", nexusPerson);
		model.addAttribute("years", nexusCalendar.getYears(100));
		model.addAttribute("months", nexusCalendar.getMonths());
		model.addAttribute("days", nexusCalendar.getDays());
		log.info("Open: contactsNew");
		return "contactsNew";
	}
	
	@RequestMapping(value="/newContact", method = RequestMethod.POST)
	public String newContactsPagePost(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("person") NexusPerson nexusPerson) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd");
		
		String birthday = "";
		String nameDay = "";
		DateTime birthdayDate = null;
		DateTime nameDayDate = null;
		
		if(!nexusPerson.getBirthdayYear().equals("NONE") && !nexusPerson.getBirthdayMonth().equals("NONE") && !nexusPerson.getBirthdayDay().equals("NONE")){
			birthday = nexusPerson.getBirthdayYear()+"/"+nexusPerson.getBirthdayMonth()+"/"+nexusPerson.getBirthdayDay();
			birthdayDate = formatter.parseDateTime(birthday);
			nexusPerson.setBirthday(birthdayDate.toDate());
		}
		if(!nexusPerson.getNameDayMonth().equals("NONE") && !nexusPerson.getNameDayDay().equals("NONE")){
			nameDay = "0001/"+nexusPerson.getNameDayMonth()+"/"+nexusPerson.getNameDayDay();
			nameDayDate = formatter.parseDateTime(nameDay);
			nexusPerson.setNameDay(nameDayDate.toDate());
		}
	
		personDao.savePerson(nexusPerson);
		
		nexusPerson = new NexusPerson();
		NexusCalendar nexusCalendar = new NexusCalendar();
		model.addAttribute("person", nexusPerson);
		model.addAttribute("years", nexusCalendar.getYears(100));
		model.addAttribute("months", nexusCalendar.getMonths());
		model.addAttribute("days", nexusCalendar.getDays());
		
		log.info("Person added: "+nexusPerson);
		log.info("Open: contactsNew");
		return "contactsNew";
	}
	
	@RequestMapping(value="/searchContact", method = RequestMethod.GET)
	public String SearchContactsPage(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if(request.getParameter("id") != null){
			int id = Integer.parseInt(request.getParameter("id"));
			NexusPerson searchedPerson = new NexusPerson();
			searchedPerson = personDao.searchPerson(id);
			request.setAttribute("peronFound", searchedPerson);
			return "searchContactResult";
		}else{
		log.info("Open: searchContacts");
			return "searchContacts";
		}
	}
	
	@RequestMapping(value="/searchContact", method = RequestMethod.POST)
	public String SearchContactsResultsPage(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute NexusPerson nexusPerson) {
		List<NexusPerson> searchedPersonList = new ArrayList<NexusPerson>();
		searchedPersonList = personDao.searchPerson(nexusPerson);
		
		if(searchedPersonList.isEmpty()){
			return "searchContactNoResults";
		}else
		if(searchedPersonList.size()>1){
			request.setAttribute("personList", searchedPersonList);
			return "searchContactResults";
		}else
		if(searchedPersonList.size()==1){
			request.setAttribute("peronFound", searchedPersonList.get(0));
			return "searchContactResult";
		}else
		return "searchContactErrorResult";
	}
	
	@RequestMapping(value="/editContactSearch", method = RequestMethod.GET)
	public String EditContactsPage(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "editContactsSearch";
	}
	
	@RequestMapping(value="/editContactSearch", method = RequestMethod.POST)
	public String EditContactsResultsPage(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute NexusPerson nexusPerson) {
		List<NexusPerson> searchedPersonList = new ArrayList<NexusPerson>();
		searchedPersonList = personDao.searchPerson(nexusPerson);
		
		request.setAttribute("editMode", true);
		
		if(searchedPersonList.isEmpty()){
			return "searchContactNoResults";
		}else
		if(searchedPersonList.size()>1){
			request.setAttribute("personList", searchedPersonList);
			return "searchContactResults";
		}else
		if(searchedPersonList.size()==1){
			request.setAttribute("personFound", searchedPersonList.get(0));
			return "editContactSearchResult";
		}else
		return "searchContactErrorResult";
	}
	
	@RequestMapping(value="/editContactResult", method = RequestMethod.POST)
	public String EditContactsPage(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute NexusPerson nexusPerson) {
		personDao.updatePerson(nexusPerson);
		return "editContactResult";
	}

}
