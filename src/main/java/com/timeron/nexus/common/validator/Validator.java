package com.timeron.nexus.common.validator;

import com.timeron.NexusDatabaseLibrary.Entity.Interface.NexusEntity;
import com.timeron.nexus.common.service.ServiceResult;

public interface Validator {

	public ServiceResult validate(NexusEntity entry);
	
}
