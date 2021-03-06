package com.timeron.nexus.apps.jTask.rest.helper;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.timeron.NexusDatabaseLibrary.Entity.JHistory;
import com.timeron.NexusDatabaseLibrary.Entity.JNote;
import com.timeron.NexusDatabaseLibrary.Entity.JProject;
import com.timeron.NexusDatabaseLibrary.Entity.JRelease;
import com.timeron.NexusDatabaseLibrary.Entity.JTask;
import com.timeron.NexusDatabaseLibrary.Entity.JTaskType;
import com.timeron.NexusDatabaseLibrary.Entity.JUserProject;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.Entity.NexusVersion;
import com.timeron.NexusDatabaseLibrary.dao.JHistoryDAO;
import com.timeron.NexusDatabaseLibrary.dao.JNoteDAO;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.JReleaseDAO;
import com.timeron.NexusDatabaseLibrary.dao.JStatusDAO;
import com.timeron.NexusDatabaseLibrary.dao.JTaskDAO;
import com.timeron.NexusDatabaseLibrary.dao.JTaskTypeDAO;
import com.timeron.NexusDatabaseLibrary.dao.JUserProjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusApplicationDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusUserApplicationDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusVersionDAO;
import com.timeron.NexusDatabaseLibrary.dao.Enum.Direction;
import com.timeron.nexus.apps.jTask.constant.ResultMessagesJTask;
import com.timeron.nexus.apps.jTask.dto.bean.AssignUserTaskDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JHistoryDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JNoteDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JReleaseDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JTaskDTO;
import com.timeron.nexus.apps.jTask.dto.bean.MainTaskDTO;
import com.timeron.nexus.apps.jTask.dto.bean.NexusVersionDTO;
import com.timeron.nexus.apps.jTask.dto.bean.TwoListOfUsers;
import com.timeron.nexus.apps.jTask.dto.bean.UsersWithProjectDTO;
import com.timeron.nexus.apps.jTask.project.ProjectImpl;
import com.timeron.nexus.apps.jTask.task.TaskImpl;
import com.timeron.nexus.apps.jTask.validator.JReleaseValidator;
import com.timeron.nexus.apps.wallet.constant.ResultMessagesWallet;
import com.timeron.nexus.common.dto.NexusPersonDTO;
import com.timeron.nexus.common.service.ResultMessages;
import com.timeron.nexus.common.service.ServiceResult;

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
	JUserProjectDAO jUserProjectDAO;
	@Autowired
	NexusPersonDAO nexusPersonDAO; 
	@Autowired
	NexusVersionDAO nexusVersionDAO;
	@Autowired
	NexusUserApplicationDAO nexusUserApplicationDAO;
	@Autowired
	NexusApplicationDAO nexusApplicationDAO;
	@Autowired
	JReleaseDAO jReleaseDAO;
	
	@Autowired
	ProjectImpl projectImpl;
	@Autowired
	TaskImpl taskImpl;
	
	private String APP_NAME = "JTASK";
	
	public JTaskRestServiceHelper(){}
	
	public List<JProjectDTO> getProjectList(Principal principal){
		LOG.info("ServiceHelper coled: getProjectList");
		List<JProjectDTO> projectListDTO;
		if(principal != null){
			projectListDTO = projectImpl.getUserProjectsWithTask(nexusPersonDAO.getByNick(principal.getName()));
		}else{
			projectListDTO = Collections.emptyList();
		}
		return projectListDTO;
	}

	public ServiceResult addNewProject(JProjectDTO jProjectDTO, ServiceResult result, Principal principal) {
		LOG.info("ServiceHelper coled: addNewProject");
		if(jProjectDAO.getByName(jProjectDTO.getName()) == null){
			if(principal != null){
				NexusPerson user = nexusPersonDAO.getByNick(principal.getName());
				if(user != null){
				JProject jProject = new JProject();
				jProject.setDescription(jProjectDTO.getDescription());
				jProject.setUser(user);
				jProject.setName(jProjectDTO.getName());
				jProject.setPrefix(jProjectDTO.getPrefix());
				jProject.setCreated(new Date());
				jProjectDAO.save(jProject);
				
				jProject = jProjectDAO.getByName(jProjectDTO.getName());
				
				JUserProject userProject = new JUserProject();
				userProject.setUser(user);
				userProject.setProject(jProject);
				userProject.setTimestamp(new Date());
				jUserProjectDAO.save(userProject);
				
				result.addMessage("Project added: "+jProject.getName());
				result.setObject(new JProjectDTO(jProject));
				LOG.info(result.getMessages());
				}else{
					result.addError(ResultMessagesJTask.PERSON_NOT_EXIST);
				}
			}else{
				result.addError(ResultMessagesJTask.PERSON_NOT_DETECTED);
			}
		}else{
			result.addError(ResultMessagesJTask.PROJECT_NOT_FOUND);
			LOG.warn(result.getMessages());
		}
		return result;
	}
	
	public ServiceResult addNewTask(JTaskDTO jTaskDTO, ServiceResult result, Principal principal) {
		LOG.info("ServiceHelper coled: addNewTask");
		JProject project = jProjectDAO.getById(jTaskDTO.getProjectId());
		int nextIdName = getNextName(jTaskDAO.getLastName(project), project.getPrefix());
		JTask jTask = new JTask();
		jTask.setCreated(new Date());
		jTask.setUpdated(new Date());
		jTask.setPriority(jTaskDTO.getPriority());
		jTask.setDescription(jTaskDTO.getDescription());
		jTask.setProject(project);
		jTask.setStatus(jStatusDAO.getById(2)); //2 jest domyślne dla nowych tasków
		jTask.setSummary(jTaskDTO.getSummary());
		jTask.setTaskType(jTaskTypeDAO.getById(jTaskDTO.getTaskTypeId()));
		if(principal != null){
			NexusPerson person = nexusPersonDAO.getByNick(principal.getName());
			if(person != null){
				jTask.setUser(person);
			}else{
				result.addError(ResultMessages.PERSON_NOT_EXIST);
				return result;
			}
		}else{
			result.addError(ResultMessages.PERSON_NOT_DETECTED);
			return result;
		}
		jTask.setName(project.getPrefix()+"-"+nextIdName);
		jTask.setIdFromName(nextIdName);
		
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
				result.addError(ResultMessagesJTask.TASK_NOT_ADDED);
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
			//TODO do usunięcia po naprawie danych
//			if(jTask.getIdFromName() == 0){
//				String[] nameArray = jTask.getName().split("-");
//				int id1 = Integer.parseInt(nameArray[1]);
//				jTask.setIdFromName(id1);
//				jTaskDAO.save(jTask);
//			}
			//TODO do tego miejsca
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
		JTask jTask = jTaskDAO.getById(jTaskId);
		if(jTask != null){
			JTaskDTO jTaskDTO = new JTaskDTO(jTask);
			return jTaskDTO;
		}else{
			return null;
		}
	}
	
	public String getProjectPrefix(int id){
		LOG.info("ServiceHelper coled: getProjectPrefix");
		JProject project = jProjectDAO.getById(id);
		if(project != null){
			return jProjectDAO.getById(id).getPrefix();
		}else{
			return null;
		}
	}

	public ServiceResult updateTask(JTaskDTO jTaskDTO, ServiceResult serviceResult) {
		LOG.info("ServiceHelper coled: updateTask");
		boolean updated = false;
		serviceResult.setSuccess(true);
		Date now = new Date();
		JTask jTask = jTaskDAO.getById(jTaskDTO.getId());
		if(jTask != null){
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
				try{
					jTaskDAO.update(jTask);
				}catch(Exception ex){
					serviceResult.addError(ResultMessagesJTask.DATABASE_ISSUE);
				}
			}
			
			JTaskDTO dto = new JTaskDTO(jTaskDAO.getById(jTask.getId()));
			serviceResult.setObject(dto);
		}else{
			serviceResult.addError(ResultMessagesJTask.TASK_CANNOT_BE_FOUND_TASK);
		}
		return serviceResult;
	}
	


	public List<JHistoryDTO> getTaskHistory(int taskId) {
		LOG.info("ServiceHelper coled: getTaskHistory");
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
		LOG.info("ServiceHelper coled: getTaskNotes");
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
	
	public ServiceResult saveNote(JNoteDTO jNoteDTO, String user, ServiceResult result) {
		LOG.info("ServiceHelper coled: saveNote");
		JTask task = jTaskDAO.getById(jNoteDTO.getTaskId());
		if(task != null){
			JNote entity = new JNote();
			entity.setCreated(new Date());
			entity.setTask(task);
			entity.setContent(jNoteDTO.getContent());
			entity.setName(jNoteDTO.getName());
			entity.setHistory(buildHistory(entity, jNoteDTO.getTaskId()));
			entity.setUser(nexusPersonDAO.getByNick(user));
			result.setSuccess(jNoteDAO.save(entity));
			result.setSuccess(true);
		}else{
			result.addError(ResultMessagesJTask.TASK_CANNOT_BE_FOUND_TASK);
		}
		return result;
	}
	
	public int getNextName(JTask jTask, String prefix) {
		LOG.info("ServiceHelper coled: getNextName");
		if(jTask != null){
			if(jTask.getIdFromName() == 0){
				String[] nameArray = jTask.getName().split("-");
				int id = Integer.parseInt(nameArray[1]);
				jTask.setIdFromName(id);
				jTaskDAO.save(jTask);
			}
			int id = jTask.getIdFromName();
			id++;
			return id;
		}else{
			return 1;
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
		LOG.info("ServiceHelper coled: getAppVersion");
		NexusVersion nexusVersion = nexusVersionDAO.getByName(appName);
		NexusVersionDTO nexusVersionsDTO = null;
		if(nexusVersion != null){
			nexusVersionsDTO = new NexusVersionDTO();
			nexusVersionsDTO.setApp(nexusVersion.getApp());
			nexusVersionsDTO.setComment(nexusVersion.getComment());
			nexusVersionsDTO.setId(nexusVersion.getId());
			nexusVersionsDTO.setVersion(nexusVersion.getVersion());
		}
		return nexusVersionsDTO;
	}

	public List<NexusPersonDTO> getAllUsers() {
		LOG.info("ServiceHelper coled: getAllUsers");
		List<NexusPersonDTO> nexusPersonDAOs = new ArrayList<NexusPersonDTO>();
		for(NexusPerson person : nexusPersonDAO.getAll()){
			nexusPersonDAOs.add(new NexusPersonDTO(person));
		}
		return nexusPersonDAOs;
	} 

	public ServiceResult assignTaskToUser(AssignUserTaskDTO dto, ServiceResult result){
		LOG.info("ServiceHelper coled: assignTaskToUser");
		Date now = new Date();
		JTask jTask = jTaskDAO.getById(dto.getTaskId());
		NexusPerson nexusPerson = nexusPersonDAO.getById(dto.getUserId());
		jTask.setUser(nexusPerson);
		jTask.setUpdated(now);
		JHistory history = new JHistory();
		try{
			jTaskDAO.update(jTask);
		}catch(Exception ex){
			result.addError(ResultMessagesJTask.CANNOT_UPDATE_TASK);
			LOG.error(ResultMessagesJTask.CANNOT_UPDATE_TASK, ex);
			return result;
		}
		
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
		
		try{
			jHistoryDAO.save(history);
			result.setSuccess(true);
		}catch(Exception ex){
			result.addError(ResultMessagesJTask.CANNOT_UPDATE_TASK);
			LOG.error(ResultMessagesJTask.CANNOT_UPDATE_TASK, ex);
			return result;
		}
		result.setObject(jTaskDAO.getById(dto.getTaskId()));
		return result;
	}

	public ServiceResult setMainTask(MainTaskDTO dto, ServiceResult result) {
		LOG.info("ServiceHelper coled: setMainTask");
		JTask task = jTaskDAO.getById(dto.getTaskId());
		JTask mainTask = jTaskDAO.getById(dto.getMainTaskId());
		if(task != null && mainTask != null){
			if(task.getProject().getId() == mainTask.getProject().getId()){
				task.setMainTask(mainTask);
				
				jTaskDAO.update(task);
				task = jTaskDAO.getById(dto.getTaskId());
				
				result.addMessage(ResultMessagesWallet.RECORD_ADDED);
				result.setObject(new JTaskDTO(task));
			}else{
				result.addError(ResultMessagesJTask.PROJECTS_ARE_DIFFERENT);
			}
			
		}else{
			result.addError(ResultMessagesJTask.TASK_CANNOT_BE_FOUND_TASK);
		}
		return result;
	}
	
	public ServiceResult getUsersWithAccessToProject(Integer projectId) {
		ServiceResult result = new ServiceResult();
		result.setSuccess(true);
		List<NexusPersonDTO> usersDTO = new ArrayList<NexusPersonDTO>();
		try{
			List<JUserProject> users = jUserProjectDAO.getByProject(jProjectDAO.getById(projectId));
			for(JUserProject user : users){
				usersDTO.add(new NexusPersonDTO(user.getUser()));
			}
			result.setObject(usersDTO);
		}catch(Exception ex){
			result.addError(ResultMessagesJTask.DATABASE_ISSUE);
			ex.printStackTrace();
		}
		return result;
	}

	public ServiceResult getUsersToManageAccessToProject(Integer projectId) {
		ServiceResult result = new ServiceResult();
		result.setSuccess(true);
		List<NexusPersonDTO> usersWithAccessDTO = new ArrayList<NexusPersonDTO>();
		List<NexusPersonDTO> allUsersDTO = new ArrayList<NexusPersonDTO>();
		List<NexusPersonDTO> usersAvailableToAddDTO = new ArrayList<NexusPersonDTO>();
		try{
			List<JUserProject> usersWithProject = jUserProjectDAO.getByProject(jProjectDAO.getById(projectId));
			for(JUserProject user : usersWithProject){
				usersWithAccessDTO.add(new NexusPersonDTO(user.getUser()));
			}
			List<NexusPerson> users = nexusUserApplicationDAO.getUsersWithAccessToApp(nexusApplicationDAO.getByName(this.APP_NAME));
			for(NexusPerson userEntry : users){
				allUsersDTO.add(new NexusPersonDTO(userEntry));
			}
			
			for(NexusPersonDTO entryAllUsers : allUsersDTO){
				boolean flag = false;
				for(NexusPersonDTO entryUser : usersWithAccessDTO){
					if(entryAllUsers.getId() == entryUser.getId()){
						flag = true;
					}
				}
				if(!flag){
					usersAvailableToAddDTO.add(entryAllUsers);
				}
			}
			
			TwoListOfUsers userLists = new TwoListOfUsers();
			userLists.setUsers1(usersWithAccessDTO);
			userLists.setUsers2(usersAvailableToAddDTO);
			result.setObject(userLists);
		}catch(Exception ex){
			result.addError(ResultMessagesJTask.DATABASE_ISSUE);
			ex.printStackTrace();
		}
		return result;
	}

	public ServiceResult saveAccessToProject(UsersWithProjectDTO usersWithProject, ServiceResult result) {
		JUserProject jUserProject;
		try{
			//will remove all users(access) from this project
			for(JUserProject oldUsers : jUserProjectDAO.getByProject(jProjectDAO.getById(usersWithProject.getProjectId()))){
				jUserProjectDAO.removeById(oldUsers.getId());
			}
			
			for(NexusPersonDTO user : usersWithProject.getUsers()){
				jUserProject = new JUserProject();
				jUserProject.setProject(jProjectDAO.getById(usersWithProject.getProjectId()));
				jUserProject.setUser(nexusPersonDAO.getById(user.getId()));
				jUserProject.setTimestamp(new Date());
				jUserProjectDAO.save(jUserProject);
			}
		}catch(Exception ex){
			result.addError(ResultMessagesJTask.DATABASE_ISSUE);
			ex.printStackTrace();
		}
		return result;
	}

	public ServiceResult getSubTasks(int taskId) {
		ServiceResult result = new ServiceResult();
		try{
			List<JTaskDTO> subTasks = taskImpl.getSubTasks(taskId);
			result.setObject(subTasks);
		}catch(Exception ex){
			ex.printStackTrace();
			result.addError(ResultMessagesJTask.DATABASE_ISSUE);
		}
		return result;
	}

	
	public ServiceResult saveRelease(JReleaseDTO dto, ServiceResult result) {
		JReleaseValidator validator = new JReleaseValidator();
		JRelease entity = new JRelease();
		entity.setComment(dto.getComment());
		entity.setProject(jProjectDAO.getById(dto.getProjectId()));
		entity.setVersion(dto.getVersion());
		
		result = validator.validate(entity);
		if(result.isSuccess()){
			jReleaseDAO.save(entity);
		}
		return result;
	}

	@Transactional
	public ServiceResult getAllReleases(ServiceResult result, Integer projectId) {
		JProject project = jProjectDAO.getById(projectId);
		if(project != null){
			List<JReleaseDTO> releases = transformToDto(jReleaseDAO.getAllReleasesForProjectInNameOrder(projectId));
			result.setObject(releases);
		}else{
			result.addError(ResultMessagesJTask.PROJECT_NOT_FOUND);
		}
		return result;
	}
	
	private List<JReleaseDTO> transformToDto(List<JRelease> jReleases) {
		List<JReleaseDTO> releaseDTOs = new ArrayList<JReleaseDTO>();
		for(JRelease release : jReleases){
			releaseDTOs.add(new JReleaseDTO(release));
		}
		return releaseDTOs;
	}

	public ServiceResult getRelease(ServiceResult result, Integer releaseId) {
		JRelease release = jReleaseDAO.getById(releaseId);
		result.setObject(release);
		return result;
	}

	public ServiceResult updateRelease(JReleaseDTO jReleaseDTO,	ServiceResult result) {
		JReleaseValidator validator = new JReleaseValidator();
		JRelease entity = new JRelease();
		entity.setId(jReleaseDTO.getId());
		entity.setComment(jReleaseDTO.getComment());
		entity.setProject(jProjectDAO.getById(jReleaseDTO.getProjectId()));
		entity.setVersion(jReleaseDTO.getVersion());
		
		result = validator.validate(entity);
		if(result.isSuccess()){
			jReleaseDAO.update(entity);
		}
		return result;
	}
}
