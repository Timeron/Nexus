package com.nexus.apps.jTask.service.rest;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

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
import com.nexus.apps.jTask.dto.bean.AssignUserTaskDTO;
import com.nexus.apps.jTask.dto.bean.JNoteDTO;
import com.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.nexus.apps.jTask.dto.bean.JTaskDTO;
import com.nexus.apps.jTask.dto.bean.MainTaskDTO;
import com.nexus.apps.jTask.service.rest.helper.JTaskRestServiceHelper;
import com.nexus.common.service.NexusRestService;
import com.nexus.common.service.ServiceResult;

@RestController
@RequestMapping("/v1/jt")
public class JTaskRestService extends NexusRestService{
	
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

	@RequestMapping(value = "/getAllTasksInOneProject", method = RequestMethod.GET)
	public String getAllTasksInOneProject(){
		LOG.info("service: getTasks");
		JProjectDTO jProjectDTO = new JProjectDTO();
		jProjectDTO.setName("All Projects");
		jProjectDTO.addTasks(helper.getTaskList());
		String result = gson.toJson(jProjectDTO);
		LOG.info("service response: getTasks -> "+result);
		return result;
	}
	
	@RequestMapping(value = "/historyTask", method = RequestMethod.GET)
	public String historyTask(HttpServletRequest request){
		int taskId = Integer.parseInt(request.getParameter("id"));
		LOG.info("service response: historyTask -> "+gson.toJson(helper.getTaskHistory(taskId)));
		return gson.toJson(helper.getTaskHistory(taskId));
	}
	
	@RequestMapping(value = "/notesTask", method = RequestMethod.GET)
	public String notesTask(HttpServletRequest request){
		int taskId = Integer.parseInt(request.getParameter("id"));
		String response = gson.toJson(helper.getTaskNotes(taskId));
		LOG.info("service response: notesTask -> "+ response);
		return response;
	}
	
	@RequestMapping(value = "/appVersion", method = RequestMethod.GET)
	public String appVersion(HttpServletRequest request){
		String appName = request.getParameter("name");
		String response = gson.toJson(helper.getAppVersion(appName));
		LOG.info("service response: appVersion -> "+ response);
		return response;
	}
	
	@RequestMapping(value = "/allProjectTasks", method = RequestMethod.GET)
	public String allProjectTask(HttpServletRequest request){
		int id = Integer.parseInt(request.getParameter("id"));
		String response = gson.toJson(helper.getProjectTasksList(id));
		LOG.info("service response: getAllProjectTasks -> "+ response);
		return response;
	}
	
	@RequestMapping(value = "/allUsers", method = RequestMethod.GET)
	public String allUsers(HttpServletRequest request){
		String response = gson.toJson(helper.getAllUsers());
		LOG.info("service response: allUsers -> "+ response);
		return response;
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
		JTaskDTO task = gson.fromJson(json, JTaskDTO.class);
		String result = gson.toJson(helper.getTask(task.getId()));
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
	public String addTask(@RequestBody String json, Principal principal){
		LOG.info("service: addTask <- "+json);
		ServiceResult result = new ServiceResult();
		JTaskDTO jTaskDTO = gson.fromJson(json, JTaskDTO.class);
		result = helper.addNewTask(jTaskDTO, result, principal);
		LOG.info("service response: addTask -> "+gson.toJson(result));
		return gson.toJson(result);
	}

	@RequestMapping(value = "/updateTask", method = RequestMethod.POST)
	public String updateTask(@RequestBody String json){
		LOG.info("service: updateTask <- "+json);
		ServiceResult result = new ServiceResult();
		JTaskDTO jTaskDTO = gson.fromJson(json, JTaskDTO.class);
		result = helper.updateTask(jTaskDTO, result);

		LOG.info("service response: updateTask -> "+gson.toJson(result));
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "/historyTask", method = RequestMethod.POST)
	public String historyTask(@RequestBody String json){
		JTaskDTO jTaskDTO = gson.fromJson(json, JTaskDTO.class);
		return gson.toJson(helper.getTaskHistory(jTaskDTO.getId()));
	}	
	
	@RequestMapping(value = "/addNote", method = RequestMethod.POST)
	public String newNote(@RequestBody String json){
		LOG.info("service: newNote <- "+json);
		ServiceResult result = new ServiceResult();
		JNoteDTO jNoteDTO = gson.fromJson(json, JNoteDTO.class);
		result = helper.saveNote(jNoteDTO, result);
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "/assignTaskToUser", method = RequestMethod.POST)
	public String assignTaskToUser(@RequestBody String json){
		LOG.info("service: assignTaskToUser <- "+json);
		ServiceResult result = new ServiceResult();
		AssignUserTaskDTO dto = gson.fromJson(json, AssignUserTaskDTO.class);
		result = helper.assignTaskToUser(dto, result);
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "/setMainTask", method = RequestMethod.POST)
	public String setMainTask(@RequestBody String json){
		LOG.info("service: setMainTask <- "+json);
		ServiceResult result = new ServiceResult();
		MainTaskDTO dto = gson.fromJson(json, MainTaskDTO.class);
		result = helper.setMainTask(dto, result);
		return gson.toJson(result);
	}
}
