package com.nexus.apps.jTask.service.rest.helper;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexus.apps.jTask.dto.bean.AssignUserTaskDTO;
import com.nexus.apps.jTask.dto.bean.JHistoryDTO;
import com.nexus.apps.jTask.dto.bean.JNoteDTO;
import com.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.nexus.apps.jTask.dto.bean.JTaskDTO;
import com.nexus.apps.jTask.dto.bean.MainTaskDTO;
import com.nexus.apps.jTask.dto.bean.NexusPersonDTO;
import com.nexus.apps.jTask.dto.bean.NexusVersionDTO;
import com.nexus.apps.wallet.constant.MessageResources;
import com.nexus.common.service.ResultMessages;
import com.nexus.common.service.ServiceResult;
import com.timeron.NexusDatabaseLibrary.Entity.JHistory;
import com.timeron.NexusDatabaseLibrary.Entity.JNote;
import com.timeron.NexusDatabaseLibrary.Entity.JProject;
import com.timeron.NexusDatabaseLibrary.Entity.JTask;
import com.timeron.NexusDatabaseLibrary.Entity.JTaskType;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.Entity.NexusVersion;
import com.timeron.NexusDatabaseLibrary.dao.JHistoryDAO;
import com.timeron.NexusDatabaseLibrary.dao.JNoteDAO;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.JStatusDAO;
import com.timeron.NexusDatabaseLibrary.dao.JTaskDAO;
import com.timeron.NexusDatabaseLibrary.dao.JTaskTypeDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusVersionDAO;
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
	@Autowired
	JHistoryDAO jHistoryDAO;
	@Autowired
	JNoteDAO jNoteDAO;
	@Autowired
	NexusPersonDAO nexusPersonDAO; 
	@Autowired
	NexusVersionDAO nexusVersionDAO;
	
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
			jProject.setPrefix(jProjectDTO.getPrefix());
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
	
	public ServiceResult addNewTask(JTaskDTO jTaskDTO, ServiceResult result, Principal principal) {
		LOG.info("ServiceHelper coled: addNewTask");
		JProject project = jProjectDAO.getById(jTaskDTO.getProjectId());
		String nextIdName = getNextName(jTaskDAO.getLastName(project), project.getPrefix());
		JTask jTask = new JTask();
		jTask.setCreated(new Date());
		jTask.setUpdated(new Date());
		jTask.setPriority(jTaskDTO.getPriority());
		jTask.setDescription(jTaskDTO.getDescription());
		jTask.setProject(project);
		jTask.setStatus(jStatusDAO.getById(2)); //2 jest domyślne dla nowych tasków
		jTask.setSummary(jTaskDTO.getSummary());
		jTask.setTaskType(jTaskTypeDAO.getById(jTaskDTO.getTaskTypeId()));
		if(principal.getName() != null){
			jTask.setUser(nexusPersonDAO.getByNick(principal.getName()));
		}else{
			result.setSuccess(false);
			result.addMessage(ResultMessages.PERSON_NOT_DETECTED);
		}
		jTask.setName(nextIdName);
		
		if(jTaskDTO.getEndDateLong() != 0){
			jTask.setEndDate(new Date(jTaskDTO.getEndDateLong()));	
		}
		if(jTaskDTO.getWorkExpected() != 0){
			jTask.setWorkExpected(jTaskDTO.getWorkExpected());
		}
		
		
		if(jTaskDTO.getMainTaskId() != null){
			jTask.setMainTask(jTaskDAO.getById(jTaskDTO.getMainTaskId()));
		}
		if(result.isSuccess() == null || result.isSuccess()){
			boolean save = jTaskDAO.save(jTask);
			if(!save){
				result.addMessage(ResultMessages.TASK_NOT_ADDED);
			}
			result.setSuccess(save);
		}else{
			return result;
		}
		return result;
	}

	public List<JTaskDTO> getProjectTasksList(JProjectDTO jProjectDTO) {
		LOG.info("ServiceHelper coled: getProjectTasksList");
		return getProjectTasksList(jProjectDTO.getId());
	}
	
	public List<JTaskDTO> getProjectTasksList(int id) {
		LOG.info("ServiceHelper coled: getProjectTasksList");
		List<JTaskDTO> jTasksDTO = new ArrayList<JTaskDTO>();
		JProject jProject = jProjectDAO.getById(id);
		for(JTask jTask : jTaskDAO.getByProject(jProject)){
			jTasksDTO.add(new JTaskDTO(jTask));
		}
		return jTasksDTO;
	}

	public List<JTaskDTO> getTaskList() {
		LOG.info("ServiceHelper coled: getTaskList");
		List<JTaskDTO> jTasksDTO = new ArrayList<JTaskDTO>();
		for(JTask jTask : jTaskDAO.getAll("priority", Direction.ASC)){
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

	public ServiceResult updateTask(JTaskDTO jTaskDTO, ServiceResult serviceResult) {
		LOG.info("ServiceHelper coled: updateTask");
		boolean updated = false;
		Date now = new Date();
		JTask jTask = jTaskDAO.getById(jTaskDTO.getId());
		if(jTaskDTO.getEndDateLong() != 0){
			jTaskDTO.setEndDate(new Date(jTaskDTO.getEndDateLong()));
		}
		
		if(jTaskDTO.getStatus()!=null && jTask.getStatus().getId()!=jTaskDTO.getStatus()){
			jTask.setStatus(jStatusDAO.getById(jTaskDTO.getStatus()));
			updated = true;
			JHistory history = new JHistory();
			history.setCreated(now);
			history.setTask(jTask);
			history.setMessage(jTaskDTO.getUpdateMessage());
			if(jTaskDTO.getUpdateMessageStatus() != null){
				history.setMessage(jTaskDTO.getUpdateMessage());
				history.setStatus(jStatusDAO.getById(jTaskDTO.getUpdateMessageStatus()));
			}else{
				history.setStatus(jStatusDAO.getById(jTaskDTO.getStatus()));
			}
			jHistoryDAO.save(history);
		}
		if(!jTask.getDescription().equals(jTaskDTO.getDescription())){
			updated = true;
			JHistory history = new JHistory();
			history.setCreated(now);
			history.setTask(jTask);
			history.setMessage("Zmieniono szczegóły: "+jTask.getDescription()+" -> "+jTaskDTO.getDescription());
			jHistoryDAO.save(history);
			jTask.setDescription(jTaskDTO.getDescription());
		}
		if(!jTask.getSummary().equals(jTaskDTO.getSummary())){
			updated = true;
			JHistory history = new JHistory();
			history.setCreated(now);
			history.setTask(jTask);
			history.setMessage("Zmieniono opis: "+jTask.getSummary()+" -> "+jTaskDTO.getSummary());
			jHistoryDAO.save(history);
			jTask.setSummary(jTaskDTO.getSummary());
		}
		if(jTaskDTO.getEndDate()!=null && jTask.getEndDate().getTime() != jTaskDTO.getEndDate().getTime()){
			updated = true;
			JHistory history = new JHistory();
			history.setCreated(now);
			history.setTask(jTask);
			history.setMessage("Zmieniono date zakończenia: "+jTask.getEndDate()+" -> "+jTaskDTO.getEndDate());
			jHistoryDAO.save(history);
			jTask.setEndDate(jTaskDTO.getEndDate());
		}
		if(jTask.getWorkExpected()!=jTaskDTO.getWorkExpected()){
			updated = true;
			JHistory history = new JHistory();
			history.setCreated(now);
			history.setTask(jTask);
			history.setMessage("Zmieniono przewidywany czas na wykonanie zadania: "+jTask.getWorkExpected()+" -> "+jTaskDTO.getWorkExpected());
			jHistoryDAO.save(history);
			jTask.setWorkExpected(jTaskDTO.getWorkExpected());
		}
		if(jTaskDTO.getName()!=null && jTask.getName()!=jTaskDTO.getName()){
			//TODO
			LOG.info("!!!!!!!!!");
		}
		if(jTask.getPriority()!=jTaskDTO.getPriority()){
			updated = true;
			JHistory history = new JHistory();
			history.setCreated(now);
			history.setTask(jTask);
			history.setMessage("Zmieniono piorytet: "+jTask.getPriority()+" -> "+jTaskDTO.getPriority());
			jHistoryDAO.save(history);
			jTask.setPriority(jTaskDTO.getPriority());
		}
		if(jTask.getTaskType().getId()!=jTaskDTO.getTaskTypeId()){
			updated = true;
			JHistory history = new JHistory();
			history.setCreated(now);
			history.setTask(jTask);
			JTaskType jTaskType = jTaskTypeDAO.getById(jTaskDTO.getTaskTypeId());
			history.setMessage("Zmieniono piorytet: "+jTask.getTaskType().getDescription()+" -> "+jTaskType.getDescription());
			jHistoryDAO.save(history);
			jTask.setTaskType(jTaskTypeDAO.getById(jTaskDTO.getTaskTypeId()));
		}
		
		if(updated){
			jTask.setUpdated(now);
			jTaskDAO.update(jTask);
		}
		
		JTaskDTO dto = new JTaskDTO(jTaskDAO.getById(jTask.getId()));
		serviceResult.setObject(dto);
		return serviceResult;
	}
	


	public List<JHistoryDTO> getTaskHistory(int taskId) {
		List<JHistoryDTO> jHistoriesDTO;
		jHistoriesDTO = transformToJHistoryDTO(jHistoryDAO.getAllFromTaskId(jTaskDAO.getById(taskId)));
		
		return jHistoriesDTO;
	}
	
	private List<JHistoryDTO> transformToJHistoryDTO(List<JHistory> histories) {
		List<JHistoryDTO> dtos = new ArrayList<JHistoryDTO>();
		for(JHistory history : histories){
			dtos.add(new JHistoryDTO(history));
		}
		return dtos;
	}
	
	public List<JNoteDTO> getTaskNotes(int taskId) {
		List<JNoteDTO> jNoteDTOs;
		jNoteDTOs = transformToJNoteDTO(jNoteDAO.getAllFromTaskId(jTaskDAO.getById(taskId)));
		return jNoteDTOs;
	}

	private List<JNoteDTO> transformToJNoteDTO(List<JNote> notes) {
		List<JNoteDTO> dtos = new ArrayList<JNoteDTO>();
		for(JNote note : notes){
			dtos.add(new JNoteDTO(note));
		}
		return dtos;
	}
	
	public ServiceResult saveNote(JNoteDTO jNoteDTO, ServiceResult result) {
		JNote entity = new JNote();
		entity.setCreated(new Date());
		entity.setTask(jTaskDAO.getById(jNoteDTO.getTaskId()));
		entity.setContent(jNoteDTO.getContent());
		entity.setHistory(buildHistory(entity, jNoteDTO.getTaskId()));
		result.setSuccess(jNoteDAO.save(entity));
		return result;
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

	private JHistory buildHistory(JNote entity, int taskId){
		JHistory history = new JHistory();
		history.setCreated(new Date());
		history.setNote(entity);
		history.setTask(jTaskDAO.getById(taskId));
		return history;
	}

	public NexusVersionDTO getAppVersion(String appName) {
		NexusVersion nexusVersion = nexusVersionDAO.getByName(appName);
		NexusVersionDTO nexusVersionsDTO = new NexusVersionDTO();
		if(nexusVersion != null){
			nexusVersionsDTO.setApp(nexusVersion.getApp());
			nexusVersionsDTO.setComment(nexusVersion.getComment());
			nexusVersionsDTO.setId(nexusVersion.getId());
			nexusVersionsDTO.setVersion(nexusVersion.getVersion());
		}
		return nexusVersionsDTO;
	}

	public List<NexusPersonDTO> getAllUsers() {
		List<NexusPersonDTO> nexusPersonDAOs = new ArrayList<NexusPersonDTO>();
		for(NexusPerson person : nexusPersonDAO.getAll()){
			nexusPersonDAOs.add(new NexusPersonDTO(person));
		}
		return nexusPersonDAOs;
	} 

	public ServiceResult assignTaskToUser(AssignUserTaskDTO dto, ServiceResult result){
		Date now = new Date();
		JTask jTask = jTaskDAO.getById(dto.getTaskId());
		NexusPerson nexusPerson = nexusPersonDAO.getById(dto.getUserId());
		jTask.setUser(nexusPerson);
		jTask.setUpdated(now);
		jTaskDAO.update(jTask);
		
		JHistory history = new JHistory();
		history.setCreated(now);
		history.setTask(jTask);
		String str = "";
		if(nexusPerson.getFirstName()!=null){
			str += nexusPerson.getFirstName()+" ";
		}
		if(nexusPerson.getLastName()!=null){
			str += nexusPerson.getLastName();
		}
		if(str.length() <= 0){
			str = nexusPerson.getNick();
		}
		history.setMessage("Zadanie zostało przypisane do: "+str);
		jHistoryDAO.save(history);
		
		return result;
	}

	public ServiceResult setMainTask(MainTaskDTO dto, ServiceResult result) {
		JTask task = jTaskDAO.getById(dto.getTaskId());
		JTask mainTask = jTaskDAO.getById(dto.getMainTaskId());
		
		task.setMainTask(mainTask);
		
		jTaskDAO.update(task);
		task = jTaskDAO.getById(dto.getTaskId());
		
		result.addMessage(MessageResources.OPERATION_SUCCESS);
		result.setObject(new JTaskDTO(task));
		return result;
	}
}
