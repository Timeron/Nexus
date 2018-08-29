package com.timeron.nexus.apps.wallet.rest;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.timeron.nexus.apps.wallet.rest.helper.impl.WalletRestServiceHelper;
import com.timeron.nexus.apps.wallet.service.dto.AccountDTO;
import com.timeron.nexus.apps.wallet.service.dto.AccountWithPeriodDTO;
import com.timeron.nexus.apps.wallet.service.dto.NewAccountDTO;
import com.timeron.nexus.apps.wallet.service.dto.RecordDTO;
import com.timeron.nexus.apps.wallet.service.dto.RecordTypeDTO;
import com.timeron.nexus.apps.wallet.service.dto.RecordTypeListDTO;
import com.timeron.nexus.apps.wallet.service.dto.SumForAccountByType;
import com.timeron.nexus.apps.wallet.service.dto.TypeForStatistics;
import com.timeron.nexus.apps.wallet.service.dto.TypesForStatistics;

@RestController
@RequestMapping("/v1/wallet")
public class WalletRestService {
	
	static Logger LOG = Logger.getLogger(WalletRestService.class);
	Gson gson = new Gson();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	WalletRestServiceHelper helper;
	
	@RequestMapping(value="/getAllRecordTypes", method = RequestMethod.GET)
	public String getAllRecordTypes(){
		LOG.info("service: getAllRecordTypes");
		String result = gson.toJson(helper.getAllRecordTypes());
		LOG.info("service response: getAllRecordTypes -> "+result);
		return result;
	}
	
	@RequestMapping(value="/getAllUserAccounts", method = RequestMethod.GET)
	public String getAllUserAccounts(Principal principal){
		LOG.info("service: getAllUserAccounts");
		String result = gson.toJson(helper.getAllUserAccounts(principal));
		LOG.info("service response: getAllUserAccounts -> "+result);
		return result;
	}
	
	@RequestMapping(value="/getAllAccountsAndRecords", method = RequestMethod.GET)
	public String getAllAccountsAndRecords(Principal principal){
		LOG.info("service: getAllAccountsAndRecords");
		String result = gson.toJson(helper.getAllAccountsAndRecords(principal));
//		String result = gson.toJson(helper.getAllUserAccounts(principal));
		LOG.info("service response: getAllAccountsAndRecords -> "+result);
		return result;
	}
	
	@RequestMapping(value="/getTypesValidForParent", method = RequestMethod.GET)
	public String getTypeValidForParent(Principal principal){
		LOG.info("service: getTypeValidForParent");
		String result = gson.toJson(helper.getTypesValidForParent(principal));
		LOG.info("service response: getTypeValidForParent -> "+result);
		return result;
	}
	
	
//	POST
	
	@RequestMapping(value="/addAccount", method = RequestMethod.POST)
	public String addAccount(@RequestBody String json, Principal principal){
		LOG.info("service: addAccount <- "+json);
		NewAccountDTO accountDTO = gson.fromJson(json, NewAccountDTO.class);
		String result = gson.toJson(helper.addAccount(accountDTO, principal));
		LOG.info("service response: addAccount -> "+result);
		return result;
	}
	
	@RequestMapping(value="/addNewRecord", method = RequestMethod.POST)
	public String addNewRecord(@RequestBody String json){
		LOG.info("service: addNewRecord <- "+json);
		RecordDTO recordDTO = gson.fromJson(json, RecordDTO.class);
		String result = gson.toJson(helper.addNewRecord(recordDTO));
		LOG.info("service response: addNewRecord -> "+result);
		return result;
	}
	
	@RequestMapping(value="/addType", method = RequestMethod.POST)
	public String addType(@RequestBody String json){
		LOG.info("service: addType <- "+json);
		RecordTypeDTO typeDTO = gson.fromJson(json, RecordTypeDTO.class);
		String result = gson.toJson(helper.addNewType(typeDTO));
		LOG.info("service response: addType -> "+result);
		return result;
	}
	
	@RequestMapping(value="/updateTypes", method = RequestMethod.POST)
	public String updateTypes(@RequestBody String json){
		LOG.info("service: updateTypes <- "+json);
		RecordTypeListDTO typeListDTO = gson.fromJson(json, RecordTypeListDTO.class);
		String result = gson.toJson(helper.updateTypes(typeListDTO));
		LOG.info("service response: updateTypes -> "+result);
		return result;
	}
	
	@RequestMapping(value="/updateRecord", method = RequestMethod.POST)
	public String updateRecord(@RequestBody String json){
		LOG.info("service: updateRecord <- "+json);
		RecordDTO typeListDTO = gson.fromJson(json, RecordDTO.class);
		String result = gson.toJson(helper.updateRecord(typeListDTO));
		LOG.info("service response: updateRecord -> "+result);
		return result;
	}
//	CHARTS
	
	@RequestMapping(value="/getRecordsForAccountByDay", method = RequestMethod.POST)
	public String getRecordsForAccountByDay(@RequestBody String json, Principal principal){
		LOG.info("service: getRecordsForAccountByDay");
		AccountDTO accountDTO = gson.fromJson(json, AccountDTO.class);
		String result = gson.toJson(helper.getRecordsForAccountByDay(accountDTO.getId(), principal));
		LOG.info("service response: getRecordsForAccountByDay -> "+result);
		return result;
	}
	
	@RequestMapping(value="/getSumForAccountByType", method = RequestMethod.POST)
	public String getSumForAccountByType(@RequestBody String json, Principal principal){
		LOG.info("service: getSumForAccountByType <- "+json);
		SumForAccountByType sumForAccountByType = gson.fromJson(json, SumForAccountByType.class);
		String result = gson.toJson(helper.getSumForAccountByType(sumForAccountByType, principal));
		LOG.info("service response: getSumForAccountByType -> "+result);
		return result;
	}
	
	@RequestMapping(value="/getSumForTypeInTypeHierarchy", method = RequestMethod.POST)
	public String getSumForTypeInTypeHierarchy(@RequestBody String json, Principal principal){
		LOG.info("service: getSumForTypeInTypeHierarchy");
		SumForAccountByType sumForAccountByType = gson.fromJson(json, SumForAccountByType.class);
		String result = gson.toJson(helper.getSumForTypeInTypeHierarchy(sumForAccountByType, principal));
		LOG.info("service response: getSumForTypeInTypeHierarchy -> "+result);
		return result;
	}

	@RequestMapping(value="/getSumForAccountByParentType", method = RequestMethod.POST)
	public String getSumForAccountByParentType(@RequestBody String json, Principal principal){
		LOG.info("service: getSumForAccountByParentType");
		SumForAccountByType sumForAccountByType = gson.fromJson(json, SumForAccountByType.class);
		String result = gson.toJson(helper.getSumForAccountByParentType(sumForAccountByType, principal));
		LOG.info("service response: getSumForAccountByParentType -> "+result);
		return result;
	}
	
	@RequestMapping(value="/getSumForTypeForStatistics", method = RequestMethod.POST)
	public String getSumForTypeForStatistics(@RequestBody String json, Principal principal){
		LOG.info("service: getSumForTypeForStatistics");
		TypeForStatistics sumForAccountByType = gson.fromJson(json, TypeForStatistics.class);
		String result = gson.toJson(helper.getSumForTypeForStatistics(sumForAccountByType, principal));
		LOG.info("service response: getSumForTypeForStatistics -> "+result);
		return result;
	}
	
	@RequestMapping(value="/getSumForTypesForStatistics", method = RequestMethod.POST)
	public String getSumForTypesForStatistics(@RequestBody String json, Principal principal){
		LOG.info("service: getSumForTypesForStatistics  <- "+json);
		TypesForStatistics sumForAccountByTypes = gson.fromJson(json, TypesForStatistics.class);
		String result = gson.toJson(helper.getSumForTypesForStatisticsPerMonth(sumForAccountByTypes, principal.getName()));
		LOG.info("service response: getSumForTypesForStatistics -> "+result);
		return result;
	}
	
	@RequestMapping(value="/getRecordsByDay", method = RequestMethod.POST)
	public String getRecordsByDay(@RequestBody String json, Principal principal){
		LOG.info("service: getRecordsByDay  <- "+json);
		AccountWithPeriodDTO requestDTO = gson.fromJson(json, AccountWithPeriodDTO.class);
		String result = gson.toJson(helper.getRecordsByDay(requestDTO.getAccountId(), requestDTO.getYear(), requestDTO.getMonth()));
		LOG.info("service response: getRecordsByDay -> "+result);
		return result;
	}

}
