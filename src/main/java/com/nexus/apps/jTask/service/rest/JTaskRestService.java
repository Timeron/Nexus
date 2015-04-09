package com.nexus.apps.jTask.service.rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexus.apps.jTask.dto.list.JProjectListDTO;
import com.nexus.apps.jTask.service.rest.helper.JTaskRestServiceHelper;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;

@RestController
@RequestMapping("/v1/jtask")
public class JTaskRestService {

	JTaskRestServiceHelper helper = new JTaskRestServiceHelper();
	

	
	Gson gson = new Gson();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value = "/getAllProjects", method = RequestMethod.GET)
	public String getAllProjects(){
		String result = gson.toJson(helper.getProjectList());
		System.out.println(result);
		return gson.toJson(result);
	}
	
}
