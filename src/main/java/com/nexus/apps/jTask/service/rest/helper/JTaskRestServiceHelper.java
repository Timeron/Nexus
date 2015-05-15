package com.nexus.apps.jTask.service.rest.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

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

public class JTaskRestServiceHelper {
	
	static Logger LOG = Logger.getLogger(JTaskRestServiceHelper.class);

	JProjectDAO jProjectDAO = new JProjectDAO();
	JTaskDAO jTaskDAO = new JTaskDAO();
	JStatusDAO jStatusDAO = new JStatusDAO();
	JTaskTypeDAO jTaskTypeDAO = new JTaskTypeDAO();
	
	public List<JProjectDTO> getProjectList(){
		LOG.info("Service coled: getProjectList");
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
		LOG.info("Service coled: addNewProject");
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

	public List<JTaskDTO> getProjectTasksList(JProjectDTO jProjectDTO) {
		LOG.info("Service coled: getProjectTasksList");
		List<JTaskDTO> jTasksDTO = new ArrayList<JTaskDTO>();
		for(JTask jTask : jTaskDAO.getByProject(jProjectDAO.getById(jProjectDTO.getId()))){
			jTasksDTO.add(new JTaskDTO(jTask));
		}
		return jTasksDTO;
	}

	public List<JTaskDTO> getTaskList() {
		LOG.info("Service coled: getTaskList");
		List<JTaskDTO> jTasksDTO = new ArrayList<JTaskDTO>();
		for(JTask jTask : jTaskDAO.getAll()){
			jTasksDTO.add(new JTaskDTO(jTask));
		}
		return jTasksDTO;
	}

	public JTaskDTO getTask(int jTaskId) {
		LOG.info("Service coled: getTask");
		JTaskDTO jTaskDTO = new JTaskDTO(jTaskDAO.getById(jTaskId));
		return jTaskDTO;
	}

	public ServiceResult saveTask(JTaskDTO jTaskDTO, ServiceResult result) {
		int nextId = jTaskDAO.getLastId()+1;
		JTask jTask = new JTask();
		jTask.setId(nextId);
		jTask.setCreated(new Date());
		jTask.setUpdated(new Date());
		jTask.setPriority(jTaskDTO.getPriority());
		jTask.setDescription(jTaskDTO.getDescription());
		jTask.setName(getProjectPrefix(jTaskDTO.getProjectId()));
		jTask.setProject(jProjectDAO.getById(jTaskDTO.getProjectId()));
		jTask.setStatus(jStatusDAO.getById(1));
		jTask.setSummary(jTaskDTO.getSummary());
		jTask.setTaskType(jTaskTypeDAO.getById(jTaskDTO.getTaskTypeId()));
		jTask.setUser(null);
		jTask.setName(buildTaskName(getProjectPrefix(jTaskDTO.getProjectId()), nextId));
		
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
	
	public String getProjectPrefix(int id){
		return jProjectDAO.getById(id).getPrefix();
	}
	
	private String buildTaskName(String prefix, int id){
		return prefix+"-"+id;
	}
	
}
