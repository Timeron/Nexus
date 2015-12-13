package com.nexus.apps.contact.rest;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexus.apps.contact.dto.NewContactDTO;
import com.nexus.apps.contact.rest.helper.ContactRestServiceHelper;
import com.nexus.apps.wallet.rest.WalletRestService;
import com.nexus.apps.wallet.service.dto.NewAccountDTO;

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
	
}
