package com.timeron.nexus.apps.nexus.service.rest;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.timeron.nexus.apps.jTask.dto.bean.TwoListOfUsers;
import com.timeron.nexus.apps.nexus.dto.ApplicationDTO;
import com.timeron.nexus.apps.nexus.dto.ApplicationForUsersDTO;
import com.timeron.nexus.apps.nexus.service.helper.NexusHelper;
import com.timeron.nexus.common.dto.NexusPersonListDTO;

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
	
	@RequestMapping(value = "/getUsersToManageAccessToApplication", method = RequestMethod.POST)
	public String getUsersToManageAccessToApplication(@RequestBody String json, Principal principal){
		ApplicationDTO app = gson.fromJson(json, ApplicationDTO.class);
		String response = gson.toJson(helper.getUsersToManageAccessToApplication(app, principal));
		return response;
	}
	
	@RequestMapping(value = "/saveAccessToApplication", method = RequestMethod.POST)
	public String saveAccessToApplication(@RequestBody String json, Principal principal){
		ApplicationForUsersDTO appUsers = gson.fromJson(json, ApplicationForUsersDTO.class);
		String response = gson.toJson(helper.saveAccessToApplication(appUsers, principal));
		return response;
	}
}
