package com.timeron.nexus.apps.wallet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.NexusDatabaseLibrary.dao.WalletRecordDAO;
import com.timeron.nexus.apps.wallet.service.dto.RecordDTO;

@Component
public class WalletService {
	
	@Autowired
	private WalletRecordDAO recordDAO;

	public List<RecordDTO> getRecordsByType(Integer typeId, Boolean income){
		List<RecordDTO> result = new ArrayList<RecordDTO>();
		List<WalletRecord> records = recordDAO.getByType(typeId, income);
		RecordDTO record;
		
		for(WalletRecord r : records){
			record = new RecordDTO(r);
			result.add(record);
		}
		
		return result;
	}
	
}
