package com.nexus.apps.nexus.service.rest;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexus.apps.nexus.dto.ApplicationDTO;
import com.nexus.apps.nexus.service.helper.NexusHelper;

@RestController
@RequestMapping("/v1/nx")
public class NexusRestService {

	static Logger LOG = Logger.getLogger(NexusRestService.class);

	@Autowired
	NexusHelper helper;
	
	Gson gson = new Gson();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value = "/addApp", method = RequestMethod.POST)
	public String checkConnection(@RequestBody String json, Principal principal){
		ApplicationDTO app = gson.fromJson(json, ApplicationDTO.class);
		String response = gson.toJson(helper.addApplication(app, principal));
		return response;
	}
	
}
