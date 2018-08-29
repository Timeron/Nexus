package com.timeron.nexus.apps.wallet.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.timeron.nexus.apps.wallet.rest.helper.impl.WalletRestAndroidServiceHelper;
import com.timeron.nexus.apps.wallet.service.dto.RecordDTO;
import com.timeron.nexus.common.service.ServiceResult;

@RestController
@RequestMapping("/v1/walletAndroid")
public class WalletRestAndroidService {
	
	static Logger LOG = Logger.getLogger(WalletRestService.class);
	Gson gson = new Gson();
	
	@Autowired
	WalletRestAndroidServiceHelper helper;

	/**
	 * GET
	 */
	
	/**
	 * Used to check if server is working
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/availability", method = RequestMethod.GET)
	public String availability(){
		LOG.info("Android service: availability");
		String result = gson.toJson(helper.availability());
		LOG.info("Android service response: availability -> "+result);
		return result;
	}
	
	
	/**
	 * POST
	 */
	
	/**
	 * Add new record from Android
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/addNewRecord", method = RequestMethod.POST)
	public String addNewRecord(@RequestBody String json){
		LOG.info("Android service: addNewRecord <- "+json);
//		String resultStr = "";
//		ServiceResult result = new ServiceResult();
//		resultStr = gson.toJson(result);
		RecordDTO recordDTO = gson.fromJson(json, RecordDTO.class);
		recordDTO.setId(0);
		recordDTO.setUpdated(0);
		String result = gson.toJson(helper.addNewRecord(recordDTO));
		LOG.info("Android service response: addNewRecord -> "+result);
		return result;
	}
	
	
}
