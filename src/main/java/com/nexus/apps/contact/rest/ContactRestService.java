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
import com.nexus.apps.contact.dto.NewContactDTO;
import com.nexus.apps.contact.rest.helper.ContactRestServiceHelper;

@RestController
@RequestMapping("/v1/contact")
public class ContactRestService {

	static Logger LOG = Logger.getLogger(ContactRestService.class);
	Gson gson = new Gson();
	
	@Autowired
	ContactRestServiceHelper helper;
	
	@RequestMapping(value="/addContact", method = RequestMethod.POST)
	public String addContact(@RequestBody String json, Principal principal){
		LOG.info("service: addContact <- "+json);
		NewContactDTO contactDTO = gson.fromJson(json, NewContactDTO.class);
		String result = gson.toJson(helper.addContact(contactDTO, principal));
		LOG.info("service response: addContact -> "+result);
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
	
}