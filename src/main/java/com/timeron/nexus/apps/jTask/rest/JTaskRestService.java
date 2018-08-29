package com.timeron.nexus.apps.jTask.rest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
import com.timeron.nexus.apps.jTask.dto.bean.AssignUserTaskDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JNoteDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JReleaseDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JTaskDTO;
import com.timeron.nexus.apps.jTask.dto.bean.MainTaskDTO;
import com.timeron.nexus.apps.jTask.dto.bean.UsersWithProjectDTO;
import com.timeron.nexus.apps.jTask.rest.helper.JTaskRestServiceHelper;
import com.timeron.nexus.common.dto.NexusPersonDTO;
import com.timeron.nexus.common.service.NexusCommonRestService;
import com.timeron.nexus.common.service.ServiceResult;

@RestController
@RequestMapping("/v1/jt")
public class JTaskRestService extends NexusCommonRestService{
	
	static Logger LOG = Logger.getLogger(JTaskRestService.class);

	@Autowired
	JTaskRestServiceHelper helper;
	
	Gson gson = new Gson();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value = "/getAllProjects", method = RequestMethod.GET)
	public String getAllProjects(Principal principal){
		LOG.info("service: getAllProjects");
		String response = gson.toJson(helper.getProjectList(principal));
		response = prepareForHtml(response);
		LOG.info("service response: getAllProjects -> "+response);
		return response;
	}

	@RequestMapping(value = "/getAllTasksInOneProject", method = RequestMethod.GET)
	public String getAllTasksInOneProject(){
		LOG.info("service: getAllTasksInOneProject");
		JProjectDTO jProjectDTO = new JProjectDTO();
		jProjectDTO.setName("All Projects");
		jProjectDTO.addTasks(helper.getTaskList());
		String response = gson.toJson(jProjectDTO);
		response = prepareForHtml(response);
		LOG.info("service response: getAllTasksInOneProject -> "+response);
		return response;
	}
	
	@RequestMapping(value = "/historyTask", method = RequestMethod.GET)
	public String historyTask(HttpServletRequest request){
		LOG.info("service: historyTask <- "+request.getParameter("id"));
		int taskId = Integer.parseInt(request.getParameter("id"));
		String response = gson.toJson(helper.getTaskHistory(taskId));
		LOG.info("service response: historyTask -> "+response);
		return response;
	}
	
	@RequestMapping(value = "/notesTask", method = RequestMethod.GET)
	public String notesTask(HttpServletRequest request){
		LOG.info("service: notesTask <- "+request.getParameter("id"));
		int taskId = Integer.parseInt(request.getParameter("id"));
		String response = gson.toJson(helper.getTaskNotes(taskId));
		response = prepareForHtml(response);
		LOG.info("service response: notesTask -> "+ response);
		return response;
	}
	
	@RequestMapping(value = "/getSubTasks", method = RequestMethod.GET)
	public String getSubTasks(HttpServletRequest request){
		LOG.info("service: getSubTasks <- "+request.getParameter("id"));
		int taskId = Integer.parseInt(request.getParameter("id"));
		String response = gson.toJson(helper.getSubTasks(taskId));
		response = prepareForHtml(response);
		LOG.info("service response: getSubTasks -> "+ response);
		return response;
	}
	
	@RequestMapping(value = "/appVersion", method = RequestMethod.GET)
	public String appVersion(HttpServletRequest request){
		LOG.info("service: appVersion <- "+request.getParameter("name"));
		String appName = request.getParameter("name");
		String response = gson.toJson(helper.getAppVersion(appName));
		response = prepareForHtml(response);
		LOG.info("service response: appVersion -> "+ response);
		return response;
	}
	
	@RequestMapping(value = "/allProjectTasks", method = RequestMethod.GET)
	public String allProjectTasks(HttpServletRequest request){
		LOG.info("service response: allProjectTasks <- "+request.getParameter("id"));
		int id = Integer.parseInt(request.getParameter("id"));
		String response = gson.toJson(helper.getProjectTasksList(id));
		response = prepareForHtml(response);
		LOG.info("service response: allProjectTasks -> "+ response);
		return response;
	}
	
	@RequestMapping(value = "/allUsers", method = RequestMethod.GET)
	public String allUsers(HttpServletRequest request){
		LOG.info("service response: allUsers");
		String response = gson.toJson(helper.getAllUsers());
		response = prepareForHtml(response);
		LOG.info("service response: allUsers -> "+ response);
		return response;
	}
	
	@RequestMapping(value = "/getUsersWithAccessToProject", method = RequestMethod.GET)
	public String getUsersWithAccessToProject(HttpServletRequest request) {
		//TODO refactor
		List<NexusPersonDTO> result = new ArrayList<NexusPersonDTO>();
		Integer projectId = Integer.parseInt(request.getParameter("projectId"));
		LOG.info("service: getUsersWithAccessToProject <- " + projectId);
		String response = gson.toJson(helper.getUsersWithAccessToProject(projectId));
		LOG.info("service response: getUsersWithAccessToProject -> "+response);
		return response;
	}
	
	@RequestMapping(value = "/getUsersToManageAccessToProject", method = RequestMethod.GET)
	public String getUsersToManageAccessToProject(HttpServletRequest request) {
		//TODO refactor
		List<NexusPersonDTO> result = new ArrayList<NexusPersonDTO>();
		Integer projectId = Integer.parseInt(request.getParameter("projectId"));
		LOG.info("service: getUsersToManageAccessToProject <- " + projectId);
		String response = gson.toJson(helper.getUsersToManageAccessToProject(projectId));
		LOG.info("service response: getUsersToManageAccessToProject -> "+response);
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
		String response = gson.toJson(helper.getProjectTasksList(jProjectDTO));
		response = prepareForHtml(response);
		LOG.info("service response: getAllProjectTasks -> "+response);
		return response;
	}
	
	@RequestMapping(value = "/getTask", method = RequestMethod.POST)
	public String getTask(@RequestBody String json){
		LOG.info("service: getTask <- "+json);
		JTaskDTO task = gson.fromJson(json, JTaskDTO.class);
		String response = gson.toJson(helper.getTask(task.getId()));
		response = prepareForHtml(response);
		LOG.info("service response: getTask -> "+response);
		return response;
	}
	
	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String addProject(@RequestBody String json, Principal principal){
		//TODO refaktor
		LOG.info("service: addProject <- "+json);
		ServiceResult result = new ServiceResult();
		JProjectDTO jProjectDTO = gson.fromJson(json, JProjectDTO.class);
		result = helper.addNewProject(jProjectDTO, result, principal);
		String response = gson.toJson(result);
		response = prepareForHtml(response);
		LOG.info("service response: addProject -> "+response);
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "/addTask", method = RequestMethod.POST)
	public String addTask(@RequestBody String json, Principal principal){
		LOG.info("service: addTask <- "+json);
		ServiceResult result = new ServiceResult();
		JTaskDTO jTaskDTO = gson.fromJson(json, JTaskDTO.class);
		result = helper.addNewTask(jTaskDTO, result, principal);
		String response = gson.toJson(result);
		response = prepareForHtml(response);
		LOG.info("service response: addTask -> "+response);
		return response;
	}

	@RequestMapping(value = "/updateTask", method = RequestMethod.POST)
	public String updateTask(@RequestBody String json){
		LOG.info("service: updateTask <- "+json);
		ServiceResult result = new ServiceResult();
		JTaskDTO jTaskDTO = gson.fromJson(json, JTaskDTO.class);
		result = helper.updateTask(jTaskDTO, result);
		String response = prepareForHtml(gson.toJson(result));
		LOG.info("service response: updateTask -> "+response);
		return response;
	}	
	
	@RequestMapping(value = "/addNote", method = RequestMethod.POST)
	public String newNote(@RequestBody String json, Principal principal){
		LOG.info("service: newNote <- "+json);
		ServiceResult result = new ServiceResult();
		JNoteDTO jNoteDTO = gson.fromJson(json, JNoteDTO.class);
		result = helper.saveNote(jNoteDTO, principal.getName(), result);
		String response = prepareForHtml(gson.toJson(result));
		LOG.info("service response: newNote -> "+response);
		return response;
	}
	
	@RequestMapping(value = "/assignTaskToUser", method = RequestMethod.POST)
	public String assignTaskToUser(@RequestBody String json){
		LOG.info("service: assignTaskToUser <- "+json);
		ServiceResult result = new ServiceResult();
		AssignUserTaskDTO dto = gson.fromJson(json, AssignUserTaskDTO.class);
		result = helper.assignTaskToUser(dto, result);
		String response = prepareForHtml(gson.toJson(result));
		LOG.info("service response: assignTaskToUser -> "+response);
		return response;
	}
	
	@RequestMapping(value = "/setMainTask", method = RequestMethod.POST)
	public String setMainTask(@RequestBody String json){
		LOG.info("service: setMainTask <- "+json);
		ServiceResult result = new ServiceResult();
		MainTaskDTO dto = gson.fromJson(json, MainTaskDTO.class);
		result = helper.setMainTask(dto, result);
		String response = prepareForHtml(gson.toJson(result));
		LOG.info("service response: setMainTask -> "+response);
		return response;
	}
	
	@RequestMapping(value = "/saveAccessToProject", method = RequestMethod.POST)
	public String saveAccessToProject(@RequestBody String json){
		LOG.info("service: saveAccessToProject <- "+json);
		ServiceResult result = new ServiceResult();
		UsersWithProjectDTO dto = gson.fromJson(json, UsersWithProjectDTO.class);
		result = helper.saveAccessToProject(dto, result);
		String response = prepareForHtml(gson.toJson(result));
		LOG.info("service response: saveAccessToProject -> "+response);
		return response;
	}
	
	@RequestMapping(value = "/saveRelease", method = RequestMethod.POST)
	public String saveRelease(@RequestBody String json){
		LOG.info("service: saveRelease <- "+json);
		ServiceResult result = new ServiceResult();
		JReleaseDTO dto = gson.fromJson(json, JReleaseDTO.class);
		result = helper.saveRelease(dto, result);
		String response = prepareForHtml(gson.toJson(result));
		LOG.info("service response: saveRelease -> "+response);
		return response;
	}
	
	@RequestMapping(value = "/getAllReleases", method = RequestMethod.GET)
	public String getAllReleases(HttpServletRequest request){
		LOG.info("service: getAllReleases <- "+request.getParameter("projectId"));
		Integer projectId = Integer.parseInt(request.getParameter("projectId"));
		ServiceResult result = new ServiceResult();
		result = helper.getAllReleases(result, projectId);
		String response = prepareForHtml(gson.toJson(result));
		LOG.info("service response: getAllReleases -> "+response);
		return response;
	}
	
	private String prepareForHtml(String response) {
		String result = response.replace("\\n", "<br />");
		return result;
	}
}
