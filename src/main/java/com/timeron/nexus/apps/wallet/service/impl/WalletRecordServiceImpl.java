package com.timeron.nexus.apps.wallet.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.NexusDatabaseLibrary.Entity.WalletAccount;
import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.NexusDatabaseLibrary.Entity.WalletType;
import com.timeron.NexusDatabaseLibrary.dao.WalletAccountDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletRecordDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletTypeDAO;
import com.timeron.nexus.apps.wallet.constant.ResultMessagesWallet;
import com.timeron.nexus.apps.wallet.exception.ValidationException;
import com.timeron.nexus.apps.wallet.service.dto.KeyValueListDTO;
import com.timeron.nexus.apps.wallet.service.dto.RecordDTO;
import com.timeron.nexus.apps.wallet.service.interfaces.WalletRecordService;
import com.timeron.nexus.apps.wallet.validator.RecordValidator;
import com.timeron.nexus.common.service.ServiceResult;

@Component
public class WalletRecordServiceImpl implements WalletRecordService{

	@Autowired
	private WalletRecordDAO walletRecordDAO;

	@Autowired
	private WalletAccountDAO walletAccountDAO;

	@Autowired
	private WalletTypeDAO walletTypeDAO;

	static Logger LOG = Logger.getLogger(WalletRecordServiceImpl.class);

	public List<RecordDTO> getRecordsByType(Integer typeId, int accountId, Boolean income) throws ValidationException {
		List<RecordDTO> result = new ArrayList<RecordDTO>();
		WalletType type = walletTypeDAO.getById(typeId);
		WalletAccount account = walletAccountDAO.getById(accountId);
		if (type != null && account != null) {
			List<WalletRecord> records = walletRecordDAO.getRecordsFromAccountWithType(account, type, income);
			RecordDTO record;

			for (WalletRecord r : records) {
				record = new RecordDTO(r);
				result.add(record);
			}
		} else {
			throw new ValidationException();
		}

		return result;
	}

	public ServiceResult addNewRecord(RecordDTO recordDTO) {
		ServiceResult result = new ServiceResult();
		RecordValidator validator = new RecordValidator();
		if (recordDTO != null) {
			WalletRecord walletRecord = new WalletRecord();
			WalletRecord walletTransferRecord = new WalletRecord();

			if (recordDTO.getDate() != 0) {
				walletRecord.setDate(new DateTime(recordDTO.getDate()));
			} else {
				walletRecord.setDate(new DateTime());
			}

			walletRecord.setDescription(recordDTO.getDescription());
			walletRecord.setValue(recordDTO.getValue());

			walletRecord.setWalletAccount(walletAccountDAO.getById(recordDTO
					.getAccountId()));

			walletRecord.setWalletType(walletTypeDAO.getById(recordDTO
					.getRecordTypeId()));
			if (recordDTO.isTransfer()) {
				walletRecord.setTransfer(true);
				walletRecord.setIncome(false);
				walletRecord.setDestinationWalletAccount(walletAccountDAO
						.getById(recordDTO.getDestynationAccountId()));
				walletRecord.setSourceWalletAccount(walletRecord
						.getWalletAccount());
				walletTransferRecord.setDate(walletRecord.getDate());
				walletTransferRecord.setDescription(walletRecord
						.getDescription());
				walletTransferRecord.setDestinationWalletAccount(walletRecord
						.getDestinationWalletAccount());
				walletTransferRecord.setIncome(true);
				walletTransferRecord.setSourceWalletAccount(walletRecord
						.getSourceWalletAccount());
				walletTransferRecord.setTransfer(true);
				walletTransferRecord.setValue(walletRecord.getValue());
				walletTransferRecord.setWalletAccount(walletRecord
						.getDestinationWalletAccount());
				walletTransferRecord
						.setWalletType(walletRecord.getWalletType());
			} else {
				walletRecord.setTransfer(false);
				walletRecord.setIncome(recordDTO.isIncome());
			}
			try {
				if (walletRecord.isTransfer()) {
					result = validator.validateNewTransferRecord(walletRecord,
							walletTransferRecord, result);
					if (result.isSuccess()) {
						walletRecordDAO.saveTransfer(walletRecord,
								walletTransferRecord);
						result.addMessage(ResultMessagesWallet.RECORD_ADDED);
					}
				} else {
					result = validator.validateNewRecord(walletRecord, result);
					if (result.isSuccess()) {
						walletRecordDAO.save(walletRecord);
						result.addMessage(ResultMessagesWallet.RECORD_ADDED);
					}
				}
			} catch (Exception ex) {
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
		record.setDate(new DateTime(recordDTO.getDate()));
		record.setUpdated(new Date());
		record.setWalletType(walletTypeDAO.getById(recordDTO.getRecordTypeId()));
		record.setWalletAccount(walletAccountDAO.getById(recordDTO
				.getAccountId()));
		record.setDestinationWalletAccount(walletAccountDAO.getById(recordDTO
				.getDestynationAccountId()));
		record.setSourceWalletAccount(walletAccountDAO.getById(recordDTO
				.getSourceWalletAccountId()));
		try {
			walletRecordDAO.update(record);
			result.setSuccess(true);
			LOG.info("Record has been updated");
		} catch (Exception ex) {
			result.addError(ResultMessagesWallet.DATABASE_ISSUE);
			LOG.error("Can not update Record", ex);
		}
		return result;
	}

	public ServiceResult updateTransferRecord(RecordDTO recordDTO) {
		ServiceResult result = new ServiceResult();
		WalletRecord recordSource = new WalletRecord();
		WalletRecord recordDestination = new WalletRecord();
		
		if(!recordDTO.isIncome()){
			recordSource.setId(recordDTO.getId());
			recordSource.setValue(recordDTO.getValue());
			recordSource.setDescription(recordDTO.getDescription());
			recordSource.setIncome(recordDTO.isIncome());
			recordSource.setTransfer(recordDTO.isTransfer());
			recordSource.setDate(new DateTime(recordDTO.getDate()));
			recordSource.setUpdated(new Date());
			recordSource.setWalletType(walletTypeDAO.getById(recordDTO.getRecordTypeId()));
			recordSource.setWalletAccount(walletAccountDAO.getById(recordDTO
					.getAccountId()));
			recordSource.setDestinationWalletAccount(walletAccountDAO.getById(recordDTO
					.getDestynationAccountId()));
			recordSource.setSourceWalletAccount(walletAccountDAO.getById(recordDTO
					.getSourceWalletAccountId()));
			
			recordDestination = getDestinationRecordForSource(recordSource.getId());
			recordDestination.setDate(recordSource.getDate());
			recordDestination.setDescription(recordSource.getDescription());
			recordDestination.setDestinationWalletAccount(recordSource.getDestinationWalletAccount());
			recordDestination.setSourceWalletAccount(recordSource.getSourceWalletAccount());
			recordDestination.setUpdated(new Date());
			recordDestination.setValue(recordSource.getValue());
			recordDestination.setWalletAccount(recordSource.getDestinationWalletAccount());
			
			walletRecordDAO.update(recordSource);
			walletRecordDAO.update(recordDestination);
			
		}else{
			result.setSuccess(false);
		}
		return result;
		
	}

	private WalletRecord getDestinationRecordForSource(Integer id) {
		WalletRecord record = walletRecordDAO.getById(id);
		return walletRecordDAO.getDestinationSourceForRecord(record, record.isIncome());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<KeyValueListDTO<Integer, RecordDTO>> getRecordsByDay(int accountId, int year, int month ) {
		List<KeyValueListDTO<Integer, RecordDTO>> result = new ArrayList<KeyValueListDTO<Integer, RecordDTO>>();
		DateTime from = null;
		DateTime to = null;
		int daysInMonth = daysOfMonth(year, month);
		int maxDaysInMonth = 42;
		int firstDayInMonth = firstDaysOfMonth(year, month);
		
		WalletAccount account = walletAccountDAO.getById(accountId);
		
		int counter = 0;
		for(int i = 0; i < maxDaysInMonth; i++){
			if(i >= firstDayInMonth && counter < daysInMonth){
				counter++;
				from = new DateTime(year, month, counter, 0, 0, 0, 000);
				to = new DateTime(year, month, counter, 23, 59, 59, 999);
				List<WalletRecord> records = walletRecordDAO.getAllRecordsFromDay(account, from, to);
				result.add(new KeyValueListDTO<Integer, RecordDTO>(counter, transformRecordsToDto(records)));
			}else{
				result.add(new KeyValueListDTO<Integer, RecordDTO>(0, Collections.<RecordDTO> emptyList()));
			}
		}
		
		return result;
		
	}
	
	private List<RecordDTO> transformRecordsToDto(List<WalletRecord> records) {
		List<RecordDTO> results = new ArrayList<RecordDTO>();
		RecordDTO record = null;
		for (WalletRecord r : records) {
			record = new RecordDTO(r);
			results.add(record);
		}
		return results;
	}

	private static int daysOfMonth(int year, int month) {
		DateTime dateTime = new DateTime(year, month, 14, 12, 0, 0, 000);
		return dateTime.dayOfMonth().getMaximumValue();
	}
	
	private static int firstDaysOfMonth(int year, int month) {
		DateTime dateTime = new DateTime(year, month, 14, 12, 0, 0, 000);
		return dateTime.getDayOfWeek();
	}

}
