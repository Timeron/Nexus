package com.nexus.apps.wallet.rest.helper;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexus.apps.wallet.constant.MessageResources;
import com.nexus.apps.wallet.service.dto.NewAccountDTO;
import com.nexus.common.service.ServiceResult;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.Entity.WalletAccount;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletAccountDAO;

@Component
public class WalletRestServiceHelper {
	
	@Autowired
	WalletAccountDAO walletAccountDAO;
	@Autowired
	NexusPersonDAO nexusPersonDAO;

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

}
