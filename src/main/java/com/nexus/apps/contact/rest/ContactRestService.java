package com.nexus.apps.contact.rest;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexus.apps.contact.dto.ContactDTO;
import com.nexus.apps.contact.dto.EventDTO;
import com.nexus.apps.contact.rest.helper.ContactRestServiceHelper;

@RestController
@RequestMapping("/v1/contact")
public class ContactRestService {

	static Logger LOG = Logger.getLogger(ContactRestService.class);
	Gson gson = new Gson();
	Gson gsonExpose = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	@Autowired
	ContactRestServiceHelper helper;
	
	@RequestMapping(value="/addContact", method = RequestMethod.POST)
	public String addContact(@RequestBody String json, Principal principal){
		LOG.info("service: addContact <- "+json);
		ContactDTO contactDTO = gson.fromJson(json, ContactDTO.class);
		String result = gson.toJson(helper.addContact(contactDTO, principal));
		LOG.info("service response: addContact -> "+result);
		return result;
	}
	
	@RequestMapping(value="/updateContact", method = RequestMethod.POST)
	public String updateContact(@RequestBody String json, Principal principal){
		LOG.info("service: updateContact <- "+json);
		ContactDTO contactDTO = gson.fromJson(json, ContactDTO.class);
		String result = gson.toJson(helper.updateContact(contactDTO, principal));
		LOG.info("service response: updateContact -> "+result);
		return result;
	}
	
	@RequestMapping(value="/getContacts", method = RequestMethod.GET)
	public String getContacts(Principal principal){
		String result = gson.toJson(helper.getContacts(principal));
		LOG.info("service response: getContacts -> "+result);
		return result;
	}
	
	@RequestMapping(value="/getContactDetails", method = RequestMethod.GET)
	public String getContactDetails(HttpServletRequest request, Principal principal){
		int contactId = Integer.parseInt(request.getParameter("contactId"));
		String result = gson.toJson(helper.getContactDetails(contactId, principal));
		LOG.info("service response: getContactDetails -> "+result);
		return result;
	}
	
	@RequestMapping(value="/addEvent", method = RequestMethod.POST)
	public String addEvent(@RequestBody String json, Principal principal){
		LOG.info("service: addEvent <- "+json);
		EventDTO eventDTO = gson.fromJson(json, EventDTO.class);
		String result = gson.toJson(helper.addEvent(eventDTO, principal));
		LOG.info("service response: addEvent -> "+result);
		return result;
	}
	
	@RequestMapping(value="/getOccasions", method = RequestMethod.GET)
	public String getOccasions(Principal principal){
		String result = gsonExpose.toJson(helper.getOccasions(principal));
		LOG.info("service response: getOccasions -> "+result);
		return result;
	}
}
