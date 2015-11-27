package com.nexus.apps.wallet.rest.helper;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.nexus.apps.wallet.constant.MessageResources;
import com.nexus.apps.wallet.service.dto.AccountDTO;
import com.nexus.apps.wallet.service.dto.AccountForDropdownDTO;
import com.nexus.apps.wallet.service.dto.KeyValueDTO;
import com.nexus.apps.wallet.service.dto.NewAccountDTO;
import com.nexus.apps.wallet.service.dto.PieChartDTO;
import com.nexus.apps.wallet.service.dto.RecordDTO;
import com.nexus.apps.wallet.service.dto.RecordTypeDTO;
import com.nexus.apps.wallet.service.dto.RecordTypeListDTO;
import com.nexus.apps.wallet.service.dto.SumForAccountByType;
import com.nexus.common.service.ServiceResult;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.Entity.WalletAccount;
import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.NexusDatabaseLibrary.Entity.WalletType;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletAccountDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletRecordDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletTypeDAO;
import com.timeron.NexusDatabaseLibrary.dao.Enum.Direction;

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
			if(type.getParentType() != null){
				recordTypeDTO.setParentId(type.getParentType().getId());
			}
			recordTypeDTOs.add(recordTypeDTO);
		}
		return recordTypeDTOs;
	}

	public ServiceResult addNewRecord(RecordDTO recordDTO) {
		ServiceResult result = new ServiceResult();
		WalletRecord walletRecord = new WalletRecord();
		WalletRecord walletTransferRecord = new WalletRecord();
		
		if(recordDTO.getDate() != 0){
			walletRecord.setDate(new Date(recordDTO.getDate()));
		}else{
			walletRecord.setDate(new Date());
		}
		
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
			walletRecord.setIncome(false);
			if(recordDTO.getDestynationAccountId() != 0){
				walletRecord.setDestinationWalletAccount(walletAccountDAO.getById(recordDTO.getDestynationAccountId()));
			}
			if(recordDTO.getAccountId() != 0){
				walletRecord.setSourceWalletAccount(walletRecord.getWalletAccount());
			}
			
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
			walletRecordDAO.save(walletRecord);
			if(walletRecord.isTransfer()){
				walletRecordDAO.save(walletTransferRecord);
			}
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

	public ServiceResult getAllAccountsAndRecords(Principal principal) {
		ServiceResult result = new ServiceResult();
		List<AccountDTO> accountDTOs = new ArrayList<AccountDTO>();
		for(WalletAccount account : walletAccountDAO.getByUser(nexusPersonDAO.getByNick(principal.getName()))){
			AccountDTO accountDTO = new AccountDTO(account);
			List<RecordDTO> recordDTOs = new ArrayList<RecordDTO>();
			RecordDTO recordDTO;
			BigDecimal sum = new BigDecimal(0);
			List<WalletRecord> records = walletRecordDAO.getRecordsFromAccount(account);
			for(WalletRecord record : records){
				recordDTO = new RecordDTO(record);
				recordDTOs.add(recordDTO);
				
				if(record.isTransfer()){
					if(record.isIncome()){
						sum = sum.add(round(record.getValue(),2));
					}else{
						sum = sum.add(round(record.getValue(),2).negate());
					}
				}else{
					if(record.isIncome()){
						sum = sum.add(round(record.getValue(),2));
					}else{
						sum = sum.add(round(record.getValue(),2).negate());
					}
				}
			}
			accountDTO.setRecords(recordDTOs);
			accountDTOs.add(accountDTO);
			accountDTO.setSum(sum.doubleValue());
		}
		result.setObject(accountDTOs);
		result.setSuccess(true);
		return result;
	}

	public List<KeyValueDTO> getRecordsForAccountByDay(AccountDTO accountDTO, Principal principal) {
		WalletAccount account = walletAccountDAO.getById(accountDTO.getId());
		List<WalletRecord> records = walletRecordDAO.getRecordsFromAccount(account, Direction.ASC);
		return transformToDayPeriod(records);
	}
	
	
	private BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
        return bd;
    }
	
	public List<PieChartDTO> getSumForAccountByType(SumForAccountByType sumForAccountByType, Principal principal) {
		WalletAccount account = walletAccountDAO.getById(sumForAccountByType.getId());
		List<WalletRecord> records = walletRecordDAO.getRecordsFromAccountWithType(account, sumForAccountByType.getIncome());
		return transformToPieChartByType(records);
	}
	
	public List<PieChartDTO> getSumForAccountByParentType(SumForAccountByType sumForAccountByType, Principal principal) {
		WalletAccount account = walletAccountDAO.getById(sumForAccountByType.getId());
		List<WalletRecord> records = walletRecordDAO.getRecordsFromAccountWithType(account, sumForAccountByType.getIncome());
		return transformToPieChartByParentType(records);
	}
	
	private List<PieChartDTO> transformToPieChartByParentType(List<WalletRecord> records) {
		Map<Integer, List<RecordDTO>> recordDTOsMap = new HashMap<Integer, List<RecordDTO>>();
		List<RecordDTO> recordDTOs;
		PieChartDTO chartDTO;
//		WalletType typeName;
		Integer typeId;
		List<PieChartDTO> chartDTOs = new ArrayList<PieChartDTO>();
		for(WalletRecord record : records){
			
			chartDTO = new PieChartDTO();
			if(record.getWalletType().getParentType() == null){
				typeId = record.getWalletType().getId();
				if(recordDTOsMap.containsKey(typeId)){
					recordDTOsMap.get(typeId).add(new RecordDTO(record));
				}else{
					recordDTOs = new ArrayList<RecordDTO>();
					recordDTOs.add(new RecordDTO(record));
					recordDTOsMap.put(record.getWalletType().getId(), recordDTOs);
				}
			}else{
				typeId = record.getWalletType().getParentType().getId();
				if(recordDTOsMap.containsKey(typeId)){
					recordDTOsMap.get(typeId).add(new RecordDTO(record));
				}else{
					recordDTOs = new ArrayList<RecordDTO>();
					recordDTOs.add(new RecordDTO(record));
					recordDTOsMap.put(record.getWalletType().getParentType().getId(), recordDTOs);
				}
			}
			System.out.println(typeId);
		}
		
		for(Entry<Integer, List<RecordDTO>> recordEntryList : recordDTOsMap.entrySet()){
			Float sum = 0f;
			chartDTO = new PieChartDTO();
			RecordTypeDTO recordTypeDTO = new RecordTypeDTO(walletTypeDAO.getById(recordEntryList.getKey()));
			chartDTO.setColor(recordTypeDTO.getColor());
			chartDTO.setKey(recordTypeDTO.getName());
			for(RecordDTO recordDTOEntry : recordEntryList.getValue()){
				sum += recordDTOEntry.getValue();
			}
			chartDTO.setValue(sum.toString());
			chartDTO.setOrder(recordTypeDTO.getId());
			chartDTOs.add(chartDTO);
		}
		Collections.sort(chartDTOs, new Comparator<PieChartDTO>(){
		    public int compare(PieChartDTO o1, PieChartDTO o2) {
		        return o1.getOrder() - o2.getOrder();
		    }
		});
		return chartDTOs;
	}


	private List<PieChartDTO> transformToPieChartByType(List<WalletRecord> records) {
		List<PieChartDTO> chartDTOs = new ArrayList<PieChartDTO>();
		PieChartDTO chartDTO = new PieChartDTO();
		String tempType = "";
		String tempColor = "";
		WalletRecord tempRecord = null;
		BigDecimal sum = new BigDecimal(0);
		for(WalletRecord record : records){
			
			if(record.getWalletType().getName().equals(tempType)){
				sum = sum.add(round(record.getValue(), 2));
			}else{
				if(!tempType.equals("")){
					chartDTO.setColor(tempColor);
					chartDTO.setKey(tempType);
					chartDTO.setValue(sum.toString());
					chartDTO.setOrder(setOrderByType(tempRecord));
					chartDTOs.add(chartDTO);
				}
				chartDTO = new PieChartDTO();
				tempType = record.getWalletType().getName();
				tempColor = record.getWalletType().getColor();
				sum = round(record.getValue(), 2);
				tempRecord = record;
			}
		}
		chartDTO.setColor(tempColor);
		chartDTO.setKey(tempType);
		chartDTO.setValue(sum.toString());
		chartDTO.setOrder(setOrderByType(tempRecord));
		chartDTOs.add(chartDTO);
		Collections.sort(chartDTOs, new Comparator<PieChartDTO>(){
		    public int compare(PieChartDTO o1, PieChartDTO o2) {
		        return o1.getOrder() - o2.getOrder();
		    }
		});
		return chartDTOs;
	}

	private int setOrderByType(WalletRecord record) {
		int typeId = 0;
		if(record.getWalletType().getParentType() != null){
			typeId = record.getWalletType().getParentType().getId();
		}else{
			typeId = record.getWalletType().getId();
		}
		return typeId;
	}

	private List<KeyValueDTO> transformToDayPeriod(List<WalletRecord> records){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd", Locale.ENGLISH);
		
		List<KeyValueDTO> dataValueDTOs = new ArrayList<KeyValueDTO>();
		Calendar tempDate = Calendar.getInstance();
		tempDate.setTimeInMillis(0);
		BigDecimal valueBD = new BigDecimal(0);
		Calendar currentDate = Calendar.getInstance();
		
		KeyValueDTO dataValueDTO = new KeyValueDTO();
		for(WalletRecord record : records){
			boolean multirow = false;
			currentDate.setTime(record.getDate());
			if(tempDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) && tempDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)){
				multirow = true;
				if(record.isIncome()){
					valueBD = valueBD.add(round(record.getValue(), 2));
				}else{
					valueBD = valueBD.add(round(record.getValue(), 2).negate());
				}
			}else{
				if(tempDate.getTimeInMillis()!=0){
					String formatted = format.format(tempDate.getTime());
					dataValueDTO.setKey(formatted);
					if(multirow){
						dataValueDTO.setValue(valueBD.toString());
					}else{
						if(record.isIncome()){
							valueBD = valueBD.add(round(record.getValue(), 2));
						}else{
							valueBD = valueBD.add(round(record.getValue(), 2).negate());
						}
						dataValueDTO.setValue(valueBD.toString());
						multirow = false;
					}
					
					dataValueDTOs.add(dataValueDTO);
				}else{
					String formatted = format.format(currentDate.getTime());
					dataValueDTO.setKey(formatted);
					if(record.isIncome()){
						valueBD = valueBD.add(round(record.getValue(), 2));
					}else{
						valueBD = valueBD.add(round(record.getValue(), 2).negate());
					}
					dataValueDTO.setValue(valueBD.toString());
				}
				tempDate.setTimeInMillis(currentDate.getTimeInMillis());
				dataValueDTO = new KeyValueDTO();						
			}
		}
		return dataValueDTOs;
	}

	public ServiceResult addNewType(RecordTypeDTO typeDTO) {
		ServiceResult result = new ServiceResult();
		result.setSuccess(true);
		WalletType type = new WalletType();
		type.setColor(typeDTO.getColor());
		type.setDefaultValue(typeDTO.getDefaultValue());
		type.setIcon(typeDTO.getIcon());
		type.setName(typeDTO.getName());
		type.setParentType(walletTypeDAO.getById(typeDTO.getParentId()));
		type.setTimestamp(new Date());
		type.setUpdated(new Date());
		try{
			walletTypeDAO.save(type);
		}catch(Exception e){
			result.setSuccess(false);
			result.getMessages().add(e.getMessage());
		}
		return result;
	}

	public List<RecordTypeDTO> getTypesValidForParent(Principal principal) {
		List<WalletType> walletTypes = walletTypeDAO.getAllParents();
		List<RecordTypeDTO> recordTypeDTOs = new ArrayList<RecordTypeDTO>();
		RecordTypeDTO typeDTO;
		for(WalletType type : walletTypes){
			typeDTO = new RecordTypeDTO(type);
			recordTypeDTOs.add(typeDTO);
		}
		return recordTypeDTOs;
	}

	public List<RecordTypeDTO> updateTypes(RecordTypeListDTO typeListDTO) {
		List<RecordTypeDTO> uTypes = new ArrayList<RecordTypeDTO>();
		WalletType walletType;
		for(RecordTypeDTO type : typeListDTO.getTypes()){
			walletType = walletTypeDAO.getById(type.getId());
			walletType.setColor(type.getColor());
			walletType.setDefaultValue(type.getDefaultValue());
			walletType.setIcon(type.getIcon());
			walletType.setName(type.getName());
//			walletType.setParentType(parentType);
			walletType.setUpdated(new Date());
			walletTypeDAO.update(walletType);
		}
		RecordTypeDTO uType;
		for(WalletType wType : walletTypeDAO.getAll()){
			uType = new RecordTypeDTO(wType);
			uTypes.add(uType);
		}
		return uTypes;
	}

}
