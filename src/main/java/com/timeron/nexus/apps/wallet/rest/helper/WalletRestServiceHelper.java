package com.timeron.nexus.apps.wallet.rest.helper;

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

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.Entity.WalletAccount;
import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.NexusDatabaseLibrary.Entity.WalletType;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletAccountDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletRecordDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletTypeDAO;
import com.timeron.NexusDatabaseLibrary.dao.Enum.Direction;
import com.timeron.NexusDatabaseLibrary.dto.IdOrderDTO;
import com.timeron.nexus.apps.wallet.constant.ResultMessagesWallet;
import com.timeron.nexus.apps.wallet.service.WalletService;
import com.timeron.nexus.apps.wallet.service.dto.AccountDTO;
import com.timeron.nexus.apps.wallet.service.dto.AccountForDropdownDTO;
import com.timeron.nexus.apps.wallet.service.dto.HierarchyPieChartDTO;
import com.timeron.nexus.apps.wallet.service.dto.NewAccountDTO;
import com.timeron.nexus.apps.wallet.service.dto.PieChartDTO;
import com.timeron.nexus.apps.wallet.service.dto.RecordDTO;
import com.timeron.nexus.apps.wallet.service.dto.RecordTypeDTO;
import com.timeron.nexus.apps.wallet.service.dto.RecordTypeListDTO;
import com.timeron.nexus.apps.wallet.service.dto.SumForAccountByType;
import com.timeron.nexus.apps.wallet.service.dto.TypeForStatistics;
import com.timeron.nexus.apps.wallet.service.dto.TypesForStatistics;
import com.timeron.nexus.apps.wallet.service.dto.WalletTypeDTO;
import com.timeron.nexus.apps.wallet.service.dto.graph.GraphListOfKeyValuesAndProperties;
import com.timeron.nexus.apps.wallet.service.dto.graph.KeyValueDTO;
import com.timeron.nexus.apps.wallet.service.dto.graph.KeyValuePropertyDTO;
import com.timeron.nexus.apps.wallet.service.dto.graph.KeyValuesByObjectDTO;
import com.timeron.nexus.apps.wallet.service.dto.graph.KeyValuesDTO;
import com.timeron.nexus.common.service.ResultMessages;
import com.timeron.nexus.common.service.ServiceResult;

@Component
public class WalletRestServiceHelper {
	
	static Logger LOG = Logger.getLogger(WalletRestServiceHelper.class);
	
	@Autowired
	WalletAccountDAO walletAccountDAO;
	@Autowired
	NexusPersonDAO nexusPersonDAO;
	@Autowired
	WalletTypeDAO walletTypeDAO;
	@Autowired
	WalletRecordDAO walletRecordDAO;
	@Autowired
	WalletService walletService;

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd", Locale.ENGLISH);
	SimpleDateFormat formatMonth = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
	
	public ServiceResult addAccount(NewAccountDTO accountDTO, Principal principal) {
		ServiceResult result = new ServiceResult();
		NexusPerson nexusPerson = nexusPersonDAO.getByNick(principal.getName());
		if(!walletAccountDAO.checkIfNameIsAvailable(accountDTO.getName(), nexusPerson)){
			result.addError(ResultMessagesWallet.ACCOUNT_ADDED);
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

	public List<RecordTypeDTO> getAllRecordTypes() {
		List<RecordTypeDTO> recordTypeDTOs = new ArrayList<RecordTypeDTO>();
		List<WalletType> walletTypes =  walletTypeDAO.getAll();
		List<IdOrderDTO> typeOrder = walletTypeDAO.getIdOrder();
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
		recordTypeDTOs = getTypeByOrder(recordTypeDTOs, typeOrder);
		return recordTypeDTOs;
	}

	private List<RecordTypeDTO> getTypeByOrder(List<RecordTypeDTO> recordTypeDTOs, List<IdOrderDTO> typeOrder) {
		List<RecordTypeDTO> typeByOrderDTOs = new ArrayList<RecordTypeDTO>();
		List<RecordTypeDTO> typeWithoutSortOrder = new ArrayList<RecordTypeDTO>();
		boolean added = false;
		for(RecordTypeDTO record : recordTypeDTOs){
			for(IdOrderDTO entry : typeOrder){
				if(entry.getId() == record.getId()){
					typeByOrderDTOs.add(record);
					added = true;
				}
			}if(!added){
				typeWithoutSortOrder.add(record);
			}
			added = false;
		}
		typeByOrderDTOs.addAll(typeWithoutSortOrder);
		return typeByOrderDTOs;
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
			result.addError(ResultMessagesWallet.RECORD_ADD_NO_ACCOUNT);
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
			result.addMessage(ResultMessagesWallet.RECORD_ADDED);
		}catch(Exception ex){
			ex.printStackTrace();
			result.addError(ResultMessagesWallet.RECORD_ADD_ERROR);
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
			List<WalletRecord> records = walletRecordDAO.getRecordsFromAccount(account, Direction.DESC);
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

	public List<KeyValueDTO> getRecordsForAccountByDay(int accountId, Principal principal) {
		WalletAccount account = walletAccountDAO.getById(accountId);
		List<WalletRecord> records = walletRecordDAO.getRecordsFromAccount(account, Direction.ASC);
		List<KeyValueDTO> chartData = transformToDayPeriod(records);
		if(chartData.size() > 0){
			KeyValueDTO toDay = new KeyValueDTO();
			toDay.setKey(format.format(new Date()));
			toDay.setValue(chartData.get(chartData.size()-1).getValue());
			chartData.add(toDay);
		}
		return chartData;
	}

	private BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
        return bd;
    }
	
	public List<PieChartDTO> getSumForAccountByType(SumForAccountByType sumForAccountByTypeDto, Principal principal) {
		WalletAccount account = walletAccountDAO.getById(sumForAccountByTypeDto.getId());
		List<WalletRecord> records = walletRecordDAO.getRecordsFromAccountWithAllTypes(account, sumForAccountByTypeDto.getIncome());
		return transformToPieChartByType(records);
	}
	
	public List<PieChartDTO> getSumForAccountByParentType(SumForAccountByType sumForAccountByType, Principal principal) {
		WalletAccount account = walletAccountDAO.getById(sumForAccountByType.getId());
		List<WalletRecord> records = walletRecordDAO.getRecordsFromAccountWithAllTypes(account, sumForAccountByType.getIncome());
		transformToHierarchyPieChartByType(records, sumForAccountByType.getIncome());
		return transformToPieChartByParentType(records);
	}
	
	public List<HierarchyPieChartDTO> getSumForTypeInTypeHierarchy(SumForAccountByType sumForAccountByType, Principal principal) {
		WalletAccount account = walletAccountDAO.getById(sumForAccountByType.getId());
		List<WalletRecord> records = walletRecordDAO.getRecordsFromAccountWithAllTypes(account, sumForAccountByType.getIncome());
		return transformToHierarchyPieChartByType(records, sumForAccountByType.getIncome());
	}
	
	private List<HierarchyPieChartDTO> transformToHierarchyPieChartByType(List<WalletRecord> records, boolean income){
		List<HierarchyPieChartDTO> chartDTOs = new ArrayList<HierarchyPieChartDTO>();
		HierarchyPieChartDTO parenPieChartDTO;
		List<HierarchyPieChartDTO> childPieChartDTOs;
		HierarchyPieChartDTO childPieChartDTO;
		if(!records.isEmpty()){
			WalletAccount account = records.get(0).getWalletAccount();
			
			List<PieChartDTO> tempParentList = transformToPieChartByParentType(records);
			for(PieChartDTO tempParent : tempParentList){
				parenPieChartDTO = new HierarchyPieChartDTO();
				parenPieChartDTO.setColor(tempParent.getColor());
				parenPieChartDTO.setKey(tempParent.getKey());
				parenPieChartDTO.setOrder(tempParent.getOrder());
				parenPieChartDTO.setValue(tempParent.getValue());
				
				List<WalletType> walletTypes = walletTypeDAO.getChildren(tempParent.getOrder());
				childPieChartDTOs = new ArrayList<HierarchyPieChartDTO>();
				for(WalletType type : walletTypes){
					childPieChartDTO = new HierarchyPieChartDTO();
					childPieChartDTO.setValue(sumRecords(walletRecordDAO.getRecordsFromAccountWithType(account, type, income)));
					childPieChartDTO.setColor(type.getColor());
					childPieChartDTO.setKey(type.getName());
					childPieChartDTO.setOrder(type.getId());
					childPieChartDTOs.add(childPieChartDTO);
				}
				
				Collections.sort(childPieChartDTOs, new Comparator<HierarchyPieChartDTO>(){
				    public int compare(HierarchyPieChartDTO o1, HierarchyPieChartDTO o2) {
				    	return (int) (Float.parseFloat(o2.getValue()) - Float.parseFloat(o1.getValue()));
				    }
				});
				parenPieChartDTO.setChildren(childPieChartDTOs);
				chartDTOs.add(parenPieChartDTO);
			}
		}else{
			chartDTOs = Collections.emptyList();
		}
		
		
		return chartDTOs;
	}
	
	private String sumRecords(List<WalletRecord> records) {
		BigDecimal sum = new BigDecimal(0);
		for(WalletRecord record : records){
			sum = sum.add(round(record.getValue(), 2));
		}
		return sum.toString();
	}
	
	private String sumRecordDTOs(List<RecordDTO> records) {
		BigDecimal sum = new BigDecimal(0);
		for(RecordDTO record : records){
			sum = sum.add(round(record.getValue(), 2));
		}
		return sum.toString();
	}

	private List<PieChartDTO> transformToPieChartByParentType(List<WalletRecord> records) {
		Map<Integer, List<RecordDTO>> recordDTOsMap = new HashMap<Integer, List<RecordDTO>>();
		List<RecordDTO> recordDTOs;
		PieChartDTO chartDTO;
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
		}
		
		for(Entry<Integer, List<RecordDTO>> recordEntryList : recordDTOsMap.entrySet()){
			BigDecimal sum = new BigDecimal(0);
			chartDTO = new PieChartDTO();
			RecordTypeDTO recordTypeDTO = new RecordTypeDTO(walletTypeDAO.getById(recordEntryList.getKey()));
			chartDTO.setColor(recordTypeDTO.getColor());
			chartDTO.setKey(recordTypeDTO.getName());
			for(RecordDTO recordDTOEntry : recordEntryList.getValue()){
				sum = sum.add(round(recordDTOEntry.getValue(), 2));
			}
			chartDTO.setValue(sum.toString());
			chartDTO.setOrder(recordTypeDTO.getId());
			chartDTOs.add(chartDTO);
		}
		Collections.sort(chartDTOs, new Comparator<PieChartDTO>(){
		    public int compare(PieChartDTO o1, PieChartDTO o2) {
		    	return (int) (Float.parseFloat(o2.getValue()) - Float.parseFloat(o1.getValue()));
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
		        return (int) (Float.parseFloat(o1.getValue()) - Float.parseFloat(o2.getValue()));
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
		validateNewType(typeDTO, result);
		if(result.isSuccess()){
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
				result.addMessage(ResultMessagesWallet.RECORD_ADDED);
			}catch(Exception e){
				result.addError(ResultMessagesWallet.DATABASE_ISSUE);
				LOG.error(e.getMessage());
			}
		}
		return result;
	}

	private void validateNewType(RecordTypeDTO typeDTO, ServiceResult result) {
		if(walletTypeDAO.getById(typeDTO.getParentId()) != null){
			
		}else{
			result.addError(ResultMessagesWallet.TYPE_NOT_ADDED_PARENT_DOESNOT_EXIST);
		}
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

	public List<KeyValueDTO> getSumForTypeForStatistics(TypeForStatistics typeForStatistics, Principal principal) {
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yy, MMM");
		
		List<KeyValueDTO> results = new ArrayList<KeyValueDTO>();
		Integer tempMonth = null;
		KeyValueDTO keyValueDTO;
		List<DateTime> dateValues = new ArrayList<DateTime>();
		WalletAccount account = walletAccountDAO.getById(typeForStatistics.getAccount());
		WalletType type = walletTypeDAO.getById(typeForStatistics.getType());
		if(type == null){
			return null;
		}
//		TODO records pobiera tylko pierwszy rekord
		List<WalletRecord> recortTypeLastDate = walletRecordDAO.getRecordsFromAccountWithType(account, type, typeForStatistics.isIncome());
		if(recortTypeLastDate.size() > 0){
			DateTime tempDataTime = new DateTime(recortTypeLastDate.get(0).getDate().getTime());
			DateTime date = new DateTime(tempDataTime.getYear(), tempDataTime.getMonthOfYear(), 1, 0, 0, 0, 0);
			while(date.isBefore(new Date().getTime())){
				dateValues.add(date);
				date = date.plusMonths(1);
			}
			for(DateTime dateTime : dateValues){
				keyValueDTO = new KeyValueDTO();
				keyValueDTO.setKey(fmt.print(dateTime));
				List<WalletRecord> records = walletRecordDAO.getRecordsFromAccountWithType(account, type, typeForStatistics.isIncome(), new Date(dateTime.getMillis()), new Date(dateTime.plusMonths(1).getMillis()));
				BigDecimal valueBD = new BigDecimal(0);
				for(WalletRecord record : records){
					valueBD = valueBD.add(round(record.getValue(),2));
				}
				keyValueDTO.setValue(valueBD.toString());
				results.add(keyValueDTO);
			}
			return results;
		}else{
			return null;
		}
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

	
	/**
	 * Metoda zwraca sume wartości wydatkow/dochodow dla zadanych typow dla każdego miesiąca od pierwszego wpisu dla zadanych typów
	 *  
	 * @param sumForAccountByTypes - zadane typy
	 * @param principal - nazwa użytkownika
	 * @return ServiceResult
	 */
	public ServiceResult getSumForTypesForStatisticsPerMonth(TypesForStatistics sumForAccountByTypes, String principal) {
		ServiceResult result = new ServiceResult();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM");
		GraphListOfKeyValuesAndProperties graphData = new GraphListOfKeyValuesAndProperties();
		
		List<KeyValuesByObjectDTO> keyValuesList = new ArrayList<KeyValuesByObjectDTO>();
		Map<String, String> colors = new HashMap<String, String>();
		Map<String, List<RecordDTO>> dataRecordsForTypeMap;
		
		
		Map<WalletTypeDTO, List<RecordDTO>> recordsByType = new HashMap<WalletTypeDTO, List<RecordDTO>>();
		//get records by type
		for(Integer typeId : sumForAccountByTypes.getTypes()){
			WalletTypeDTO type = new WalletTypeDTO(walletTypeDAO.getById(typeId));
			colors.put(type.getName(), type.getColor());
			List<RecordDTO> records = walletService.getRecordsByType(typeId, sumForAccountByTypes.isIncome());
			recordsByType.put(type, records);
		}
		
		DateTime dateForGraph = findMinDateOfValues(recordsByType);
		
		while(dateForGraph.isBeforeNow()){
			KeyValuesByObjectDTO keyValues = new KeyValuesByObjectDTO();
			keyValues.setKey(dateFormatter.format(dateForGraph.getMillis()));
			keyValuesList.add(keyValues);
			dateForGraph = dateForGraph.plusMonths(1);
		}
		
		//build key multivalue dto for statistics
		for(Entry<WalletTypeDTO, List<RecordDTO>> entryByType : recordsByType.entrySet()){
			dataRecordsForTypeMap = transferToDataValueMap(entryByType.getValue(), dateFormatter);
			
			for(KeyValuesByObjectDTO kv : keyValuesList){
				if(dataRecordsForTypeMap.containsKey(kv.getKey())){
					KeyValueDTO record = new KeyValueDTO();
					record.setKey(entryByType.getKey().getName());
					record.setValue(sumRecordDTOs(dataRecordsForTypeMap.get(kv.getKey())));
					kv.addValue(record);
				}else{
					KeyValueDTO record = new KeyValueDTO();
					record.setKey(entryByType.getKey().getName());
					record.setValue("0");
					kv.addValue(record);
				}
			}
		}
		
		graphData.setProperty(colors);
		graphData.setKayValues(keyValuesList);
		result.setObject(graphData);
		result.addMessage("message");
		
		return result;
	}

	
	private DateTime findMinDateOfValues(Map<WalletTypeDTO, List<RecordDTO>> recordsByType) {
		DateTime minDate = new DateTime();
		for(Entry<WalletTypeDTO, List<RecordDTO>> entry : recordsByType.entrySet()){
			for(RecordDTO record : entry.getValue()){
				if(minDate.isAfter(entry.getValue().get(0).getDate())){
					minDate = new DateTime(entry.getValue().get(0).getDate());
				}
			}
		}
		return minDate;
	}

	/**
	 * Sortowanie rekordów po formacie daty.
	 * @param values lista rekordów
	 * @return
	 */
	private Map<String, List<RecordDTO>> transferToDataValueMap(List<RecordDTO> records, SimpleDateFormat dateFormatter) {
		Map<String, List<RecordDTO>> result = new HashMap<String, List<RecordDTO>>();
		
		String tempData = "";
		for(RecordDTO record : records){
			tempData = dateFormatter.format(record.getDate());
			if(result.containsKey(tempData)){
				result.get(tempData).add(record);
			}else{
				List<RecordDTO> values = new ArrayList<RecordDTO>();
				values.add(record);
				result.put(tempData, values);
			}
		}
		return result;
	}

}