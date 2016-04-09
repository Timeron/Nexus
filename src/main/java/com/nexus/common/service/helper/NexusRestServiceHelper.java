package com.nexus.common.service.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.nexus.common.dto.NexusPersonDTO;
import com.nexus.common.service.ResultMessages;
import com.nexus.common.service.ServiceResult;
import com.timeron.NexusDatabaseLibrary.Entity.NexusConfig;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusApplicationDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusConfigDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusUserApplicationDAO;

@Component
public class NexusRestServiceHelper {
	static Logger LOG = Logger.getLogger(NexusRestServiceHelper.class);

	@Autowired
	NexusConfigDAO configDAO;
	@Autowired
	NexusPersonDAO nexusPersonDAO;
	@Autowired
	NexusUserApplicationDAO nexusUserApplicationDAO;
	@Autowired
	NexusApplicationDAO nexusApplicationDAO;
	
	public ServiceResult checkConnection() {
		ServiceResult result = new ServiceResult();
		String message = ResultMessages.NO_CONNECTION;
		NexusConfig token = configDAO.getParametr("connection.tooken");
		if(token!=null){
			result.setSuccess(true);
			result.addMessage(token.getValue());
			return result;
		}
		result.setSuccess(false);
		result.addMessage(message);
		return result;
	}

	public ServiceResult getUsersForApplication(String appName) {
		ServiceResult result = new ServiceResult();
		result.setSuccess(true);
		List<NexusPersonDTO> usersDTO = new ArrayList<NexusPersonDTO>();
		try{
			List<NexusPerson> users = nexusUserApplicationDAO.getUsersWithAccessToApp(nexusApplicationDAO.getByName(appName));
			for(NexusPerson userEntry : users){
				usersDTO.add(new NexusPersonDTO(userEntry));
				System.out.print("!!! "+userEntry.getNick());
			}
			result.setObject(usersDTO);
		}catch(Exception ex){
			result.setSuccess(false);
			ex.printStackTrace();
		}
		return result;
	}

}
