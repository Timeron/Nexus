package com.timeron.nexus.apps.wallet.validator;

import org.springframework.stereotype.Component;

import com.timeron.NexusDatabaseLibrary.Entity.WalletType;
import com.timeron.nexus.apps.wallet.constant.ResultMessagesWallet;
import com.timeron.nexus.common.service.ServiceResult;

@Component
public class TypeValidator extends Validator{
	
	public ServiceResult validateNewType(WalletType type, ServiceResult result) {
		if(type.getColor() == null){
			result.addError(ResultMessagesWallet.TYPE_COLOR_MISSING);
		}
		if(type.getName() == null){
			result.addError(ResultMessagesWallet.TYPE_NAME_MISSING);
		}
		if(type.getTimestamp() == null || type.getTimestamp().getTime() <= 0){
			result.addError(ResultMessagesWallet.TYPE_TIMESTAMP_MISSING);
		}
		return result;
	}
	
	public ServiceResult validateNewTypeWithParent(WalletType type, ServiceResult result) {
		result = validateNewType(type, result);
		if(type.getParentType() == null){
			result.addError(ResultMessagesWallet.TYPE_NOT_ADDED_PARENT_DOESNOT_EXIST);
		}
		return result;
	}
	
	public ServiceResult validateUpdateType(WalletType type, ServiceResult result) {
		if(type.getId() == 0){
			result.addError(ResultMessagesWallet.TYPE_ID_MISSING);
		}
		result = validateNewType(type, result);
		return result;
	}
	
	public ServiceResult validateUpdateTypeWithParent(WalletType type, ServiceResult result) {
		if(type.getId() == 0){
			result.addError(ResultMessagesWallet.TYPE_ID_MISSING);
		}
		result = validateUpdateType(type, result);
		return result;
	}
	
}
