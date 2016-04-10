package com.nexus.apps.nexus.service.helper;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexus.apps.jTask.dto.bean.TwoListOfUsers;
import com.nexus.apps.nexus.bean.user.UserRights;
import com.nexus.apps.nexus.dto.ApplicationDTO;
import com.nexus.common.dto.NexusPersonDTO;
import com.nexus.common.service.ResultMessages;
import com.nexus.common.service.ServiceResult;
import com.timeron.NexusDatabaseLibrary.Entity.NexusApplication;
import com.timeron.NexusDatabaseLibrary.Entity.NexusUserApplicationRef;
import com.timeron.NexusDatabaseLibrary.dao.NexusApplicationDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusUserApplicationDAO;

@Component
public class NexusHelper {

	@Autowired 
	NexusApplicationDAO nexusApplicationDAO;
	@Autowired
	NexusUserApplicationDAO nexusUserApplicationDAO;
	@Autowired
	NexusPersonDAO nexusPersonDAO;
	@Autowired
	UserRights userRights;

	public ApplicationDTO getApplication(String app) {
		ApplicationDTO application = new ApplicationDTO(nexusApplicationDAO.getByKey(app));
		return application;
	}

	public ServiceResult addApplication(ApplicationDTO app, Principal principal) {
		ServiceResult result = new ServiceResult();
		try{
			NexusApplication application = new NexusApplication();
			application.setApplicationDescription(app.getDescription());
			application.setApplicationName(app.getName());
			application.setCreateTimestamp(new Date());
			application.setDeployed(false);
			application.setAppKey(app.getKey());
			application.setUpdateTimestamp(new Date());
			
			nexusApplicationDAO.save(application);
		}catch(Exception ex){
			result.setSuccess(false);
			result.addMessage(ResultMessages.APPLICATION_CANNOT_BE_ADDED);
			ex.printStackTrace();
		}
		
		try{
			NexusUserApplicationRef nexusUserApplicationRef = new NexusUserApplicationRef();
			nexusUserApplicationRef.setApplication(nexusApplicationDAO.getByName(app.getName()));
			nexusUserApplicationRef.setTimestamp(new Date());
			nexusUserApplicationRef.setUser(nexusPersonDAO.getByNick(principal.getName()));
			nexusUserApplicationDAO.save(nexusUserApplicationRef);
		}catch(Exception ex){
			result.setSuccess(false);
			result.addMessage(ResultMessages.USER_CANNOT_BE_CONNECTED_TO_APPLICATION);
			ex.printStackTrace();
		}
		return null;
	}

	public ServiceResult getUsersToManageAccessToApplication(ApplicationDTO app, Principal principal) {
		ServiceResult result = new ServiceResult();
		TwoListOfUsers users = new TwoListOfUsers();
		List<NexusPersonDTO> candidate = new ArrayList<NexusPersonDTO>();
		
		List<NexusPersonDTO> allUsers = null;
		List<NexusPersonDTO> owners = null;
		
		try{
			owners = userRights.getUsersWithAccessToApplication(nexusApplicationDAO.getById(app.getId()));
			allUsers = userRights.getUsersWithAccessToNexus();
		}catch(Exception ex){
			result.setSuccess(false);
			result.addMessage(ResultMessages.CANNOT_RECEIVE_USER);
			ex.printStackTrace();
		}
		if(!allUsers.isEmpty() && allUsers != null){
			for(NexusPersonDTO entryAllUser : allUsers){
				boolean flag = true;
				for(NexusPersonDTO entryOwner : owners){
					if(entryOwner.getNick().equals(entryAllUser.getNick())){
						flag = false;
					}
				}
				if(flag){
					candidate.add(entryAllUser);
				}
			}
		}
		users.setUsers1(owners);
		users.setUsers2(candidate);
		result.setObject(users);
		result.setSuccess(true);
		return result;
	}
	
	
	
}
