package com.nexus.apps.wallet.rest.helper;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexus.apps.wallet.constant.MessageResources;
import com.nexus.apps.wallet.service.dto.AccountForDropdownDTO;
import com.nexus.apps.wallet.service.dto.NewAccountDTO;
import com.nexus.apps.wallet.service.dto.RecordDTO;
import com.nexus.apps.wallet.service.dto.RecordTypeDTO;
import com.nexus.common.service.ServiceResult;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.Entity.WalletAccount;
import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.NexusDatabaseLibrary.Entity.WalletType;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletAccountDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletRecordDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletTypeDAO;

@Component
public class WalletRestServiceHelper {
	
	@Autowired
	WalletAccountDAO walletAccountDAO;
	@Autowired
	NexusPersonDAO nexusPersonDAO;
	@Autowired
	WalletTypeDAO walletTypeDAO;
	@Autowired
	WalletRecordDAO walletRecordDAO;

	public ServiceResult addAccount(NewAccountDTO accountDTO, Principal principal) {
		ServiceResult result = new ServiceResult();
		NexusPerson nexusPerson = nexusPersonDAO.getByNick(principal.getName());
		if(!walletAccountDAO.checkIfNameIsAvailable(accountDTO.getName(), nexusPerson)){
			result.addMessage(MessageResources.ACCOUNT_ADDED);
			result.setSuccess(false);
			return result;
		}else{
			Date now = new Date();
			WalletAccount walletAccount = new WalletAccount();
			walletAccount.setCurrency("PLN");
			walletAccount.setDescription(accountDTO.getDescription());
			walletAccount.setName(accountDTO.getName());
			walletAccount.setTimestamp(now);
			walletAccount.setUpdated(now);
			walletAccount.setUser(nexusPerson);
			try{
				walletAccountDAO.save(walletAccount);
				result.addMessage(MessageResources.ACCOUNT_ADDED);
				result.setSuccess(true);
			}catch(Exception ex){
				result.addMessage(MessageResources.ACCOUNT_ADD_ERROR);
				result.setSuccess(false);
				ex.printStackTrace();
			}
			return result;
		}
		
	}

	public List<RecordTypeDTO> getAllRecordTypes() {
		List<RecordTypeDTO> recordTypeDTOs = new ArrayList<RecordTypeDTO>();
		List<WalletType> walletTypes =  walletTypeDAO.getAll();
		for(WalletType type : walletTypes){
			RecordTypeDTO recordTypeDTO = new RecordTypeDTO();
			recordTypeDTO.setId(type.getId());
			recordTypeDTO.setColor(type.getColor());
			recordTypeDTO.setDefaultValue(type.getDefaultValue());
			recordTypeDTO.setIcon(type.getIcon());
			recordTypeDTO.setName(type.getName());
			recordTypeDTO.setTimestamp(type.getTimestamp());
			recordTypeDTO.setUpdated(type.getUpdated());
			recordTypeDTOs.add(recordTypeDTO);
		}
		return recordTypeDTOs;
	}

	public ServiceResult addNewRecord(RecordDTO recordDTO) {
		ServiceResult result = new ServiceResult();
		WalletRecord walletRecord = new WalletRecord();
		walletRecord.setDate(new Date(recordDTO.getDate()));
		walletRecord.setDescription(recordDTO.getDescription());
		walletRecord.setValue(recordDTO.getValue());
		if(recordDTO.getAccountId() != 0){
			walletRecord.setWalletAccount(walletAccountDAO.getById(recordDTO.getAccountId()));
		}else{
			result.setSuccess(false);
			result.addMessage(MessageResources.RECORD_ADD_NO_ACCOUNT);
			return result;
		}
		if(recordDTO.getRecordTypeId() != 0){
			walletRecord.setWalletType(walletTypeDAO.getById(recordDTO.getRecordTypeId()));
		}
		if(recordDTO.isTransfer()){
			walletRecord.setTransfer(true);
			if(recordDTO.getDestynationAccountId() != 0){
				walletRecord.setDestinationWalletAccount(walletAccountDAO.getById(recordDTO.getDestynationAccountId()));
			}
			if(recordDTO.getAccountId() != 0){
				walletRecord.setSourceWalletAccount(walletRecord.getWalletAccount());
			}
		}else{
			walletRecord.setTransfer(false);
			walletRecord.setIncome(recordDTO.isIncome());
		}
		try{
			walletRecordDAO.save(walletRecord);
			result.setSuccess(true);
			result.addMessage(MessageResources.RECORD_ADDED);
		}catch(Exception ex){
			ex.printStackTrace();
			result.setSuccess(false);
			result.addMessage(MessageResources.RECORD_ADD_ERROR);
		}
		return result;
	}

	public List<AccountForDropdownDTO> getAllUserAccounts(Principal principal) {
		List<WalletAccount> walletAccounts = walletAccountDAO.getByUser(nexusPersonDAO.getByNick(principal.getName()));
		List<AccountForDropdownDTO> accountDTOs = new ArrayList<AccountForDropdownDTO>();
		for(WalletAccount walletAccount : walletAccounts){
			AccountForDropdownDTO accountDTO = new AccountForDropdownDTO();
			accountDTO.setDescription(walletAccount.getDescription());
			accountDTO.setName(walletAccount.getName());
			accountDTO.setId(walletAccount.getId());
			accountDTOs.add(accountDTO);
		}
		return accountDTOs;
	}

}
