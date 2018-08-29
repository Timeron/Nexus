package com.timeron.nexus.apps.wallet.rest.helper.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.timeron.nexus.apps.wallet.exception.ValidationException;
import com.timeron.nexus.apps.wallet.rest.helper.WalletRestServiceHelperInterface;
import com.timeron.nexus.apps.wallet.service.dto.AccountDTO;
import com.timeron.nexus.apps.wallet.service.dto.AccountForDropdownDTO;
import com.timeron.nexus.apps.wallet.service.dto.HierarchyPieChartDTO;
import com.timeron.nexus.apps.wallet.service.dto.KeyValueListDTO;
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
import com.timeron.nexus.apps.wallet.service.dto.graph.KeyValuesByObjectDTO;
import com.timeron.nexus.apps.wallet.service.impl.WalletAccountServiceImpl;
import com.timeron.nexus.apps.wallet.service.impl.WalletRecordServiceImpl;
import com.timeron.nexus.apps.wallet.service.impl.WalletTypeServiceImpl;
import com.timeron.nexus.common.service.ServiceResult;

@Component
public class WalletRestServiceHelper implements WalletRestServiceHelperInterface{
	
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
	WalletRecordServiceImpl walletRecordService;
	@Autowired
	WalletAccountServiceImpl walletAccountService;
	@Autowired
	WalletTypeServiceImpl walletTypeService;

	SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MMM-dd", Locale.ENGLISH);
	SimpleDateFormat formatMonth = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
	
	public ServiceResult addAccount(NewAccountDTO accountDTO, Principal principal) {
		NexusPerson nexusPerson = nexusPersonDAO.getByNick(principal.getName());
		return walletAccountService.addAccount(accountDTO, nexusPerson);
	}

	public List<AccountForDropdownDTO> getAllUserAccounts(Principal principal) {
		return walletAccountService.getAllUserAccounts(principal);
	}
	
	public ServiceResult addNewRecord(RecordDTO recordDTO) {
		return walletRecordService.addNewRecord(recordDTO);
	}

	public ServiceResult updateRecord(RecordDTO recordDTO) {
		ServiceResult result;
		if(recordDTO.isTransfer()){
			result = walletRecordService.updateTransferRecord(recordDTO);
		}else{
			result = walletRecordService.updateRecord(recordDTO);
		}
		return result;
	}
	
	public ServiceResult updateTypes(RecordTypeListDTO typeListDTO) {
		return walletTypeService.updateTypes(typeListDTO);
	}

	public ServiceResult addNewType(RecordTypeDTO typeDTO) {
		return walletTypeService.addNewType(typeDTO);
	}

	public List<RecordTypeDTO> getAllRecordTypes() {
		return walletTypeService.getAllRecordTypes();
	}

	public List<RecordTypeDTO> getTypesValidForParent(Principal principal) {
		return walletTypeService.getTypesValidForParent(principal);
	}
	
	//TODO co to robi?
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
			toDay.setKey(formatDay.format(new Date()));
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
		if(account != null){
			List<WalletRecord> records = walletRecordDAO.getRecordsFromAccountWithAllTypes(account, sumForAccountByTypeDto.getIncome());
			return transformToPieChartByType(records);
		}
		return null;
	}
	
	@Override
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
		Map<String, List<WalletRecord>> tempRecordMap = new LinkedHashMap<String, List<WalletRecord>>();

		List<WalletRecord> recordInDayList;
		for(WalletRecord record : records){
			if(tempRecordMap.containsKey(formatDay.format(record.getDate().getMillis()))){
				tempRecordMap.get(formatDay.format(record.getDate().getMillis())).add(record);
			}else{
				recordInDayList = new ArrayList<WalletRecord>();
				recordInDayList.add(record);
				tempRecordMap.put(formatDay.format(record.getDate().getMillis()), recordInDayList);
			}
		}
		
		BigDecimal valueBD = new BigDecimal(0);
		for(Entry<String, List<WalletRecord>> entry : tempRecordMap.entrySet()){
			KeyValueDTO keyValueDTO;
			if(entry.getValue().size() > 1){
				for(WalletRecord record : entry.getValue()){
					valueBD = record.isIncome() ? valueBD.add(round(record.getValue(), 2)) : valueBD.add(round(record.getValue(), 2).negate());
				}
				keyValueDTO = new KeyValueDTO(entry.getKey(), valueBD.toString());
			}else{
				WalletRecord record = entry.getValue().get(0);
				valueBD = record.isIncome() ? valueBD.add(round(record.getValue(), 2)) : valueBD.add(round(record.getValue(), 2).negate());
				keyValueDTO = new KeyValueDTO(entry.getKey(), valueBD.toString());
			}
			dataValueDTOs.add(keyValueDTO);
		}
		
//		Calendar tempDate = Calendar.getInstance();
//		tempDate.setTimeInMillis(0);
//		BigDecimal valueBD = new BigDecimal(0);
//		Calendar currentDate = Calendar.getInstance();
//		
//		KeyValueDTO dataValueDTO = new KeyValueDTO();
//		for(WalletRecord record : records){
//			boolean multirow = false;
//			currentDate.setTime(record.getDate());
//			if(tempDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) && tempDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)){
//				multirow = true;
//				if(record.isIncome()){
//					valueBD = valueBD.add(round(record.getValue(), 2));
//				}else{
//					valueBD = valueBD.add(round(record.getValue(), 2).negate());
//				}
//			}else{
//				if(tempDate.getTimeInMillis()!=0){
//					String formatted = format.format(tempDate.getTime());
//					dataValueDTO.setKey(formatted);
//					if(multirow){
//						dataValueDTO.setValue(valueBD.toString());
//					}else{
//						if(record.isIncome()){
//							valueBD = valueBD.add(round(record.getValue(), 2));
//						}else{
//							valueBD = valueBD.add(round(record.getValue(), 2).negate());
//						}
//						dataValueDTO.setValue(valueBD.toString());
//						multirow = false;
//					}
//					
//					dataValueDTOs.add(dataValueDTO);
//				}else{
//					String formatted = format.format(currentDate.getTime());
//					dataValueDTO.setKey(formatted);
//					if(record.isIncome()){
//						valueBD = valueBD.add(round(record.getValue(), 2));
//					}else{
//						valueBD = valueBD.add(round(record.getValue(), 2).negate());
//					}
//					dataValueDTO.setValue(valueBD.toString());
//				}
//				tempDate.setTimeInMillis(currentDate.getTimeInMillis());
//				dataValueDTO = new KeyValueDTO();						
//			}
//		}
		return dataValueDTOs;
	}

	public List<KeyValueDTO> getSumForTypeForStatistics(TypeForStatistics typeForStatistics, Principal principal) {
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yy, MMM");
		
		List<KeyValueDTO> results = new ArrayList<KeyValueDTO>();
		KeyValueDTO keyValueDTO;
		List<DateTime> dateValues = new ArrayList<DateTime>();
		WalletAccount account = walletAccountDAO.getById(typeForStatistics.getAccount());
		WalletType type = walletTypeDAO.getById(typeForStatistics.getType());
		if(type == null){
			return null;
		}
		List<WalletRecord> recortTypeLastDate = walletRecordDAO.getRecordsFromAccountWithType(account, type, typeForStatistics.isIncome());
		if(recortTypeLastDate.size() > 0){
			DateTime tempDataTime = new DateTime(recortTypeLastDate.get(0).getDate().getMillis());
			DateTime date = new DateTime(tempDataTime.getYear(), tempDataTime.getMonthOfYear(), 1, 0, 0, 0, 0);
			while(date.isBefore(new DateTime())){
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
			List<RecordDTO> records;
			try {
				records = walletRecordService.getRecordsByType(typeId, sumForAccountByTypes.getAccount(), sumForAccountByTypes.isIncome());
				recordsByType.put(type, records);
			} catch (ValidationException e) {
				e.printStackTrace();
			}
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

	public ServiceResult getRecordsByDay(int accountId, int year, int month) {
		ServiceResult result = new ServiceResult();
		List<KeyValueListDTO<Integer, RecordDTO>> records = walletRecordService.getRecordsByDay(accountId, year, month);
		result.setSuccess(true);
		result.setObject(records);
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
