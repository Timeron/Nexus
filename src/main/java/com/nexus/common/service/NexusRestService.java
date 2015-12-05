package com.nexus.common.service;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexus.common.service.helper.NexusRestServiceHelper;

@RestController
@RequestMapping("/v1")
public class NexusRestService {

	static Logger LOG = Logger.getLogger(NexusRestService.class);

	@Autowired
	NexusRestServiceHelper helper;
	
	Gson gson = new Gson();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value = "/checkConnection", method = RequestMethod.GET)
	public String checkConnection(Principal principal){
		String response = "NUF";
		if(principal != null){
			LOG.info("service: checkConnection " + principal.getName());
			response = gson.toJson(helper.checkConnection());
			LOG.info("service response: checkConnection -> "+response);
		}
		return response;
	}
	
	@RequestMapping(value = "/currentUser", method = RequestMethod.GET)
	public String currentUser(Principal principal){
		return gson.toJson(principal);
	}
	
	@RequestMapping(value = "/currentUserPOST", method = RequestMethod.POST)
	public String currentUserPOST(Principal principal){
		return gson.toJson(principal);
	}
	
}