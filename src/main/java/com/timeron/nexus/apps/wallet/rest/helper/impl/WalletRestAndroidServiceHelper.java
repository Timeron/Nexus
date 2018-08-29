package com.timeron.nexus.apps.wallet.rest.helper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.NexusDatabaseLibrary.Entity.NexusConfig;
import com.timeron.NexusDatabaseLibrary.dao.NexusConfigDAO;
import com.timeron.nexus.apps.wallet.service.dto.RecordDTO;
import com.timeron.nexus.apps.wallet.service.impl.WalletRecordServiceImpl;
import com.timeron.nexus.common.service.ResultMessages;
import com.timeron.nexus.common.service.ServiceResult;

@Component
public class WalletRestAndroidServiceHelper {

	@Autowired
	WalletRecordServiceImpl walletRecordService;
	
	@Autowired
	NexusConfigDAO configDAO;
	
	public ServiceResult addNewRecord(RecordDTO recordDTO) {
		return walletRecordService.addNewRecord(recordDTO);
	}

	public ServiceResult availability() {
		ServiceResult result = new ServiceResult();
		String message = ResultMessages.NO_CONNECTION;
		NexusConfig token = configDAO.getParametr("connection.tooken");
		if(token!=null){
			result.addMessage(token.getValue());
			return result;
		}
		result.setSuccess(false);
		result.addMessage(message);
		return result;
	}
	
}
