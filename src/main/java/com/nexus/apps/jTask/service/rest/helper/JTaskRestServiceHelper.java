package com.nexus.apps.jTask.service.rest.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.nexus.apps.jTask.dto.bean.JTaskDTO;
import com.nexus.common.service.ServiceResult;
import com.timeron.NexusDatabaseLibrary.Entity.JProject;
import com.timeron.NexusDatabaseLibrary.Entity.JTask;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.JStatusDAO;
import com.timeron.NexusDatabaseLibrary.dao.JTaskDAO;
import com.timeron.NexusDatabaseLibrary.dao.JTaskTypeDAO;
import com.timeron.NexusDatabaseLibrary.dao.Enum.Direction;

@Component
public class JTaskRestServiceHelper {
	
	static Logger LOG = Logger.getLogger(JTaskRestServiceHelper.class);

	@Autowired
	JProjectDAO jProjectDAO;
	@Autowired
	JTaskDAO jTaskDAO;
	@Autowired
	JStatusDAO jStatusDAO;
	@Autowired
	JTaskTypeDAO jTaskTypeDAO;
	
	public JTaskRestServiceHelper(){}
	
	public List<JProjectDTO> getProjectList(){
		LOG.info("ServiceHelper coled: getProjectList");
		List<JProjectDTO> projectListDTO = new ArrayList<JProjectDTO>();
		List<JProject> projects = jProjectDAO.getAll("id", Direction.ASC);
		JProjectDTO projectDTO = null;
		for(JProject project : projects){
			projectDTO = new JProjectDTO(project);
			if(!project.getTask().isEmpty() && project.getTask() != null){
				for(JTask jTask : project.getTask()){
					projectDTO.addTask(new JTaskDTO(jTask));
				}
			}
			projectListDTO.add(projectDTO);
		}
		return projectListDTO;
	}

	public ServiceResult addNewProject(JProjectDTO jProjectDTO, ServiceResult result) {
		LOG.info("ServiceHelper coled: addNewProject");
		if(jProjectDAO.getByName(jProjectDTO.getName()) == null){
			JProject jProject = new JProject();
			jProject.setDescription(jProjectDTO.getDescription());
			jProject.setName(jProjectDTO.getName());
			jProject.setCreated(new Date());
			jProjectDAO.save(jProject);
			result.setSuccess(true);
			result.addMessage("Project added: "+jProject.getName());
			result.setObject(new JProjectDTO(jProjectDAO.getByName(jProjectDTO.getName())));
			LOG.info(result.getMessages());
		}else{
			result.setSuccess(false);
			result.addMessage("Error: Project not added");
			LOG.warn(result.getMessages());
		}
		return result;
	}
	
	public ServiceResult addNewTask(JTaskDTO jTaskDTO, ServiceResult result) {
		LOG.info("ServiceHelper coled: addNewTask");
		JProject project = jProjectDAO.getById(jTaskDTO.getProjectId());
		String nextIdName = getNextName(jTaskDAO.getLastName(project), project.getPrefix());
		JTask jTask = new JTask();
		jTask.setCreated(new Date());
		jTask.setUpdated(new Date());
		jTask.setPriority(jTaskDTO.getPriority());
		jTask.setDescription(jTaskDTO.getDescription());
		jTask.setProject(project);
		jTask.setStatus(jStatusDAO.getById(2));
		jTask.setSummary(jTaskDTO.getSummary());
		jTask.setTaskType(jTaskTypeDAO.getById(jTaskDTO.getTaskTypeId()));
		jTask.setUser(null);
		jTask.setName(nextIdName);
		
		if(jTaskDTO.getMainTaskId() != null){
			jTask.setMainTask(jTaskDAO.getById(jTaskDTO.getMainTaskId()));
		}
		boolean save = jTaskDAO.save(jTask);
		result.setSuccess(save);
		if(!save){
			result.addMessage("Wystąpił błąd: Task nie dodany!");
		}
		return result;
	}

	public List<JTaskDTO> getProjectTasksList(JProjectDTO jProjectDTO) {
		LOG.info("ServiceHelper coled: getProjectTasksList");
		List<JTaskDTO> jTasksDTO = new ArrayList<JTaskDTO>();
		JProject jProject = jProjectDAO.getById(jProjectDTO.getId());
		for(JTask jTask : jTaskDAO.getByProject(jProject)){
			jTasksDTO.add(new JTaskDTO(jTask));
		}
		return jTasksDTO;
	}

	public List<JTaskDTO> getTaskList() {
		LOG.info("ServiceHelper coled: getTaskList");
		List<JTaskDTO> jTasksDTO = new ArrayList<JTaskDTO>();
		for(JTask jTask : jTaskDAO.getAll()){
			jTasksDTO.add(new JTaskDTO(jTask));
		}
		return jTasksDTO;
	}

	public JTaskDTO getTask(int jTaskId) {
		LOG.info("ServiceHelper coled: getTask");
		JTaskDTO jTaskDTO = new JTaskDTO(jTaskDAO.getById(jTaskId));
		return jTaskDTO;
	}
	
	public String getProjectPrefix(int id){
		return jProjectDAO.getById(id).getPrefix();
	}

	public ServiceResult updateTask(JTaskDTO jTaskDTO) {
		LOG.info("ServiceHelper coled: updateTask");
		JTask jTask = jTaskDAO.getById(jTaskDTO.getId());
		jTask.setStatus(jStatusDAO.getById(jTaskDTO.getStatus()));
		jTask.setUpdated(new Date());
		jTaskDAO.update(jTask);
		return null;
	}
	
	public String getNextName(String name, String prefix) {
		if(name != ""){
			String[] nameArray = name.split("-");
			int id = Integer.parseInt(nameArray[1]);
			id++;
			name = prefix+"-"+id;
			return name;
		}else{
			return prefix+"-1";
		}
	}
	
}
