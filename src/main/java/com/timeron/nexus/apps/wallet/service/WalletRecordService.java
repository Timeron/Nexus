package com.timeron.nexus.apps.wallet.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.NexusDatabaseLibrary.dao.WalletAccountDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletRecordDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletTypeDAO;
import com.timeron.nexus.apps.wallet.constant.ResultMessagesWallet;
import com.timeron.nexus.apps.wallet.service.dto.RecordDTO;
import com.timeron.nexus.apps.wallet.validator.RecordValidator;
import com.timeron.nexus.common.service.ServiceResult;

@Component
public class WalletRecordService {
	
	@Autowired
	private WalletRecordDAO walletRecordDAO;
	
	@Autowired
	private WalletAccountDAO walletAccountDAO;
	
	@Autowired
	private WalletTypeDAO walletTypeDAO;

	static Logger LOG = Logger.getLogger(WalletRecordService.class);
	
	public List<RecordDTO> getRecordsByType(Integer typeId, Boolean income){
		List<RecordDTO> result = new ArrayList<RecordDTO>();
		List<WalletRecord> records = walletRecordDAO.getByType(typeId, income);
		RecordDTO record;
		
		for(WalletRecord r : records){
			record = new RecordDTO(r);
			result.add(record);
		}
		
		return result;
	}
	
	public ServiceResult addNewRecord(RecordDTO recordDTO) {
		ServiceResult result = new ServiceResult();
		RecordValidator validator = new RecordValidator();
		if(recordDTO != null){
			WalletRecord walletRecord = new WalletRecord();
			WalletRecord walletTransferRecord = new WalletRecord();
			
			if(recordDTO.getDate() != 0){
				walletRecord.setDate(new Date(recordDTO.getDate()));
			}else{
				walletRecord.setDate(new Date());
			}
			
			walletRecord.setDescription(recordDTO.getDescription());
			walletRecord.setValue(recordDTO.getValue());
			
			walletRecord.setWalletAccount(walletAccountDAO.getById(recordDTO.getAccountId()));
			
			walletRecord.setWalletType(walletTypeDAO.getById(recordDTO.getRecordTypeId()));
			if(recordDTO.isTransfer()){
				walletRecord.setTransfer(true);
				walletRecord.setIncome(false);
				walletRecord.setDestinationWalletAccount(walletAccountDAO.getById(recordDTO.getDestynationAccountId()));
				walletRecord.setSourceWalletAccount(walletRecord.getWalletAccount());
				walletTransferRecord.setDate(walletRecord.getDate());
				walletTransferRecord.setDescription(walletRecord.getDescription());
				walletTransferRecord.setDestinationWalletAccount(walletRecord.getDestinationWalletAccount());
				walletTransferRecord.setIncome(true);
				walletTransferRecord.setSourceWalletAccount(walletRecord.getSourceWalletAccount());
				walletTransferRecord.setTransfer(true);
				walletTransferRecord.setValue(walletRecord.getValue());
				walletTransferRecord.setWalletAccount(walletRecord.getDestinationWalletAccount());
				walletTransferRecord.setWalletType(walletRecord.getWalletType());			
			}else{
				walletRecord.setTransfer(false);
				walletRecord.setIncome(recordDTO.isIncome());
			}
			try{
				if(walletRecord.isTransfer()){
					result = validator.validateNewTransferRecord(walletRecord, walletTransferRecord, result);
					if(result.isSuccess()){
						walletRecordDAO.saveTransfer(walletRecord, walletTransferRecord);
						result.addMessage(ResultMessagesWallet.RECORD_ADDED);
					}
				}else{
					result = validator.validateNewRecord(walletRecord, result);
					if(result.isSuccess()){
						walletRecordDAO.save(walletRecord);
						result.addMessage(ResultMessagesWallet.RECORD_ADDED);
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
				result.addError(ResultMessagesWallet.RECORD_ADD_ERROR);
			}
		}
		return result;
	}
	
	public ServiceResult updateRecord(RecordDTO recordDTO) {
		ServiceResult result = new ServiceResult();
		WalletRecord record = new WalletRecord();
		record.setId(recordDTO.getId());
		record.setValue(recordDTO.getValue());
		record.setDescription(recordDTO.getDescription());
		record.setIncome(recordDTO.isIncome());
		record.setTransfer(recordDTO.isTransfer());
		record.setDate(new Date(recordDTO.getDate()));
		record.setUpdated(new Date());
		record.setWalletType(walletTypeDAO.getById(recordDTO.getRecordTypeId()));
		record.setWalletAccount(walletAccountDAO.getById(recordDTO.getAccountId()));
		record.setDestinationWalletAccount(walletAccountDAO.getById(recordDTO.getDestynationAccountId()));
		record.setSourceWalletAccount(walletAccountDAO.getById(recordDTO.getSourceWalletAccountId()));
		try{
			walletRecordDAO.update(record);
			result.setSuccess(true);
			LOG.info("Record has been updated");
		}catch(Exception ex){
			result.addError(ResultMessagesWallet.DATABASE_ISSUE);
			LOG.error("Can not update Record", ex);
		}
		return result;
	}
	
}
