package com.nexus.apps.nexus.service.helper;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexus.apps.nexus.dto.ApplicationDTO;
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

	public ApplicationDTO getApplication(String app) {
		
		return null;
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
	
	
	
}
