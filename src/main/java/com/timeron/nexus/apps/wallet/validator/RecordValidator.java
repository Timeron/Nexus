package com.timeron.nexus.apps.wallet.validator;

import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.nexus.apps.wallet.constant.ResultMessagesWallet;
import com.timeron.nexus.common.service.ResultMessages;
import com.timeron.nexus.common.service.ServiceResult;

public class RecordValidator extends Validator{

	public ServiceResult validateNewRecord(WalletRecord record, ServiceResult result) {
		if(result == null){
			result = new ServiceResult();
		}
		
		if (record.getDate() == null || record.getDate().getMillis() <= 0) {
			result.addError(ResultMessages.DATE_ERROR);
		}
		if (record.getWalletAccount() == null) {
			result.addError(ResultMessagesWallet.RECORD_ADD_NO_ACCOUNT);
		}
		if (record.getDestinationWalletAccount() != null) {
			result.addError(ResultMessagesWallet.DATA_ERROR);
		}
		if (record.getSourceWalletAccount() != null) {
			result.addError(ResultMessagesWallet.DATA_ERROR);
		}
		if (record.getWalletType() == null) {
			result.addError(ResultMessagesWallet.TYPE_NOT_EXISTS);
		}
		if (record.getValue() <= 0) {
			result.addError(ResultMessagesWallet.VALUE_NEGATIVE);
		}
		if (record.isTransfer()) {
			result.addError(ResultMessagesWallet.TRANSFER_SELECTED);
		}
		return result;
	}

	public ServiceResult validateNewTransferRecord(WalletRecord sourceRecord, WalletRecord destinationRecord, ServiceResult result){
		if(result == null){
			result = new ServiceResult();
		}
		
		result = basicRecordValidation(sourceRecord, result);
		result = basicRecordValidation(destinationRecord, result);
		
		if(sourceRecord.getDestinationWalletAccount() == null || 
				sourceRecord.getSourceWalletAccount() == null || 
				sourceRecord.getWalletAccount() == null || 
				destinationRecord.getDestinationWalletAccount() == null || 
				destinationRecord.getSourceWalletAccount() == null || 
				destinationRecord.getWalletAccount() == null){
			result.addError(ResultMessagesWallet.RECORD_ADD_NO_ACCOUNT);
		}else{
			if(!sourceRecord.getSourceWalletAccount().equals(destinationRecord.getSourceWalletAccount()) && 
					!sourceRecord.getDestinationWalletAccount().equals(destinationRecord.getDestinationWalletAccount()) &&
					!sourceRecord.getWalletAccount().equals(sourceRecord.getSourceWalletAccount()) &&
					!destinationRecord.getWalletAccount().equals(destinationRecord.getDestinationWalletAccount())){
				result.addError(ResultMessagesWallet.DATA_ERROR);
			}
			if(sourceRecord.getSourceWalletAccount().equals(sourceRecord.getDestinationWalletAccount())){
				result.addError(ResultMessagesWallet.DATA_ERROR);
			}
			if(!sourceRecord.getSourceWalletAccount().equals(sourceRecord.getWalletAccount())){
				result.addError(ResultMessagesWallet.DATA_ERROR);
			}
			if(destinationRecord.getSourceWalletAccount().equals(destinationRecord.getDestinationWalletAccount())){
				result.addError(ResultMessagesWallet.DATA_ERROR);
			}
			if(destinationRecord.getSourceWalletAccount().equals(destinationRecord.getWalletAccount())){
				result.addError(ResultMessagesWallet.DATA_ERROR);
			}
		}
		
		if(sourceRecord.getValue() != destinationRecord.getValue()){
			result.addError(ResultMessagesWallet.VALUE_ERROR);
		}
		
		return result;
	}

	private ServiceResult basicRecordValidation(WalletRecord record,
			ServiceResult result) {
		if (record.getDate() == null || record.getDate().getMillis() <= 0) {
			result.addError(ResultMessages.DATE_ERROR);
		}
		if (record.getWalletType() != null) {
			result.addError(ResultMessagesWallet.TYPE_NOT_EXISTS);
		}
		if (record.getValue() <= 0) {
			result.addError(ResultMessagesWallet.VALUE_NEGATIVE);
		}
		if (!record.isTransfer()) {
			result.addError(ResultMessagesWallet.TRANSFER_SELECTED);
		}
		return result;
	}

}
