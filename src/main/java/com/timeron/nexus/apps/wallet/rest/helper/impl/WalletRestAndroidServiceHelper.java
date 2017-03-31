package com.timeron.nexus.apps.wallet.rest.helper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.nexus.apps.wallet.service.WalletRecordService;
import com.timeron.nexus.apps.wallet.service.dto.RecordDTO;
import com.timeron.nexus.common.service.ServiceResult;

@Component
public class WalletRestAndroidServiceHelper {

	@Autowired
	WalletRecordService walletRecordService;
	
	public ServiceResult addNewRecord(RecordDTO recordDTO) {
		return walletRecordService.addNewRecord(recordDTO);
	}
	
}
