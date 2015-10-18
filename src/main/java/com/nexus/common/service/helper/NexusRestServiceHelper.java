package com.nexus.common.service.helper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexus.common.service.ResultMessages;
import com.nexus.common.service.ServiceResult;
import com.timeron.NexusDatabaseLibrary.Entity.NexusConfig;
import com.timeron.NexusDatabaseLibrary.dao.NexusConfigDAO;

@Component
public class NexusRestServiceHelper {
	static Logger LOG = Logger.getLogger(NexusRestServiceHelper.class);

	@Autowired
	NexusConfigDAO configDAO;
	
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

}
