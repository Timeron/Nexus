package com.nexus.apps.wallet.rest;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexus.apps.wallet.rest.helper.WalletRestServiceHelper;
import com.nexus.apps.wallet.service.dto.NewAccountDTO;
import com.nexus.apps.wallet.service.dto.RecordDTO;

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

}
