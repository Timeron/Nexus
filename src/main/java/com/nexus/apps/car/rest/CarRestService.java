package com.nexus.apps.car.rest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexus.apps.car.dto.RecordMobileDTO;
import com.nexus.apps.car.rest.helper.CarRestServiceHelper;

@RestController
@RequestMapping("/v1/car")
public class CarRestService {
	
	static Logger LOG = Logger.getLogger(CarRestService.class);
	Gson gson = new Gson();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	CarRestServiceHelper helper;
	
//	POST
	
	@RequestMapping(value="/addRecord", method = RequestMethod.POST)
	public String addRecord(@RequestBody String json){
		LOG.info("service: addRecord <- "+json);
		RecordMobileDTO recordDTO = gson.fromJson(json, RecordMobileDTO.class);
		String result = gson.toJson(helper.addRecord(recordDTO));
		LOG.info("service response: addRecord -> "+result);
		return result;
	}
//	GET
	
	@RequestMapping(value="/getRecords", method = RequestMethod.GET)
	public String getRecords(){
		LOG.info("service: getRecords");
		String result = gson.toJson(helper.getRecords());
		LOG.info("service response: getRecords -> "+result);
		return result;
	}
	
}
