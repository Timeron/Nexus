package com.nexus.apps.jTask.service.rest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.nexus.apps.jTask.dto.bean.JTaskDTO;
import com.nexus.apps.jTask.service.rest.helper.JTaskRestServiceHelper;
import com.nexus.common.service.RestService;
import com.nexus.common.service.ServiceResult;

@RestController
@RequestMapping("/v1/jtask")
public class JTaskRestService extends RestService{
	
	static Logger LOG = Logger.getLogger(JTaskRestService.class);

	@Autowired
	JTaskRestServiceHelper helper;
	
	Gson gson = new Gson();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value = "/getAllProjects", method = RequestMethod.GET)
	public String getAllProjects(){
		LOG.info("service: getAllProjects");
		String result = gson.toJson(helper.getProjectList());
		LOG.info("service response: getAllProjects -> "+result);
		return result;
	}

	@RequestMapping(value = "/getAllTasks", method = RequestMethod.GET)
	public String getTasks(){
		LOG.info("service: getTasks");
		String result = gson.toJson(helper.getTaskList());
		LOG.info("service response: getTasks -> "+result);
		return result;
	}
	
	/**
	 * POSTs
	 *
	 */

	@RequestMapping(value = "/getProjectTasks", method = RequestMethod.POST)
	public String getAllProjectTasks(@RequestBody String json){
		LOG.info("service: getAllProjectTasks <- "+json);
		JProjectDTO jProjectDTO = gson.fromJson(json, JProjectDTO.class);
		String result = gson.toJson(helper.getProjectTasksList(jProjectDTO));
		LOG.info("service response: getAllProjectTasks -> "+result);
		return result;
	}
	
	@RequestMapping(value = "/getTask", method = RequestMethod.POST)
	public String getTask(@RequestBody String json){
		LOG.info("service: getTask <- "+json);
		JTaskDTO jTaskDTO = gson.fromJson(json, JTaskDTO.class);
		String result = gson.toJson(helper.getTask(jTaskDTO.getId()));
		LOG.info("service response: getTask -> "+result);
		return result;
	}
	
	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String addProject(@RequestBody String json){
		LOG.info("service: addProject <- "+json);
		ServiceResult result = new ServiceResult();
		JProjectDTO jProjectDTO = gson.fromJson(json, JProjectDTO.class);
		result = helper.addNewProject(jProjectDTO, result);
		LOG.info("service response: addProject -> "+gson.toJson(result));
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "/addTask", method = RequestMethod.POST)
	public String addTask(@RequestBody String json){
		LOG.info("service: addTask <- "+json);
		ServiceResult result = new ServiceResult();
		LOG.info("json "+json);
		JTaskDTO jTaskDTO = gson.fromJson(json, JTaskDTO.class);
		result = helper.addNewTask(jTaskDTO, result);
		LOG.info("service response: addTask -> "+gson.toJson(result));
		return gson.toJson(result);
	}
	//do zrobienia
	@RequestMapping(value = "/updateTask", method = RequestMethod.POST)
	public String updateTask(@RequestBody String json){
		LOG.info("service: updateTask <- "+json);
		ServiceResult result = new ServiceResult();
		//do zrobienia
		LOG.info("service response: updateTask -> "+gson.toJson(result));
		return gson.toJson(result);
	}
	
}
