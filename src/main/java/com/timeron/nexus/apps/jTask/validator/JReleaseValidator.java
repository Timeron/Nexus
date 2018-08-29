package com.timeron.nexus.apps.jTask.validator;

import com.timeron.NexusDatabaseLibrary.Entity.JRelease;
import com.timeron.NexusDatabaseLibrary.Entity.Interface.NexusEntity;
import com.timeron.nexus.apps.jTask.constant.ResultMessagesJTask;
import com.timeron.nexus.common.service.ResultMessages;
import com.timeron.nexus.common.service.ServiceResult;
import com.timeron.nexus.common.validator.Validator;

public class JReleaseValidator implements Validator{

	@Override
	public ServiceResult validate(NexusEntity entry) {
		JRelease jRelease;
		ServiceResult result = new ServiceResult();
		if(entry instanceof JRelease){
			jRelease = (JRelease) entry;
		}else{
			result.addError(ResultMessages.APPLICATION_ERROR);
			return result;
		}
		
		if(jRelease.getVersion() == null || jRelease.getVersion().isEmpty()){
			result.addError(ResultMessagesJTask.VERSION_EMPTY);
		}
		if(jRelease.getProject() == null){
			result.addError(ResultMessagesJTask.NEED_PROJECT_SELECTED);
		}
		if(jRelease.getVersion().length() > 10){
			result.addError(ResultMessagesJTask.VERSION_TOO_LONG);
		}
		
		return result;
	}

}
