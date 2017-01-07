package com.timeron.nexus.apps.wallet.service;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.Entity.WalletAccount;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletAccountDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletRecordDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletTypeDAO;
import com.timeron.nexus.apps.wallet.constant.ResultMessagesWallet;
import com.timeron.nexus.apps.wallet.service.dto.AccountForDropdownDTO;
import com.timeron.nexus.apps.wallet.service.dto.NewAccountDTO;
import com.timeron.nexus.common.service.ServiceResult;

@Component
public class WalletAccountService {

	@Autowired
	WalletAccountDAO walletAccountDAO;
	@Autowired
	NexusPersonDAO nexusPersonDAO;
	@Autowired
	WalletTypeDAO walletTypeDAO;
	@Autowired
	WalletRecordDAO walletRecordDAO;
	@Autowired
	WalletRecordService walletService;
	
	static Logger LOG = Logger.getLogger(WalletAccountService.class);
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd", Locale.ENGLISH);
	SimpleDateFormat formatMonth = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
	
	public ServiceResult addAccount(NewAccountDTO accountDTO, NexusPerson nexusPerson) {
		ServiceResult result = new ServiceResult();
		if(!walletAccountDAO.checkIfNameIsAvailable(accountDTO.getName(), nexusPerson)){
			result.addError(ResultMessagesWallet.ACCOUNT_ADD_ERROR);
			return result;
		}else{
			Date now = new Date();
			WalletAccount walletAccount = new WalletAccount();
			walletAccount.setCurrency("PLN");
			walletAccount.setDescription(accountDTO.getDescription());
			walletAccount.setName(accountDTO.getName());
			walletAccount.setTimestamp(now);
			walletAccount.setUpdated(now);
			walletAccount.setOwner(nexusPerson);
			try{
				walletAccountDAO.save(walletAccount);
				result.addMessage(ResultMessagesWallet.ACCOUNT_ADDED);
			}catch(Exception ex){
				result.addError(ResultMessagesWallet.ACCOUNT_ADD_ERROR);
				ex.printStackTrace();
			}
			return result;
		}
		
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
