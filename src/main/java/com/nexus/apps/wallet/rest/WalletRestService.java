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

@RestController
@RequestMapping("/v1/wallet")
public class WalletRestService {
	
	static Logger LOG = Logger.getLogger(WalletRestService.class);
	Gson gson = new Gson();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	WalletRestServiceHelper helper;
	
	@RequestMapping(value="/addAccount", method = RequestMethod.POST)
	public String addAccount(@RequestBody String json, Principal principal){
		LOG.info("service: addAccount <- "+json);
		NewAccountDTO accountDTO = gson.fromJson(json, NewAccountDTO.class);
		String result = gson.toJson(helper.addAccount(accountDTO, principal));
		LOG.info("service response: getAllProjects -> "+result);
		return result;
	}

}
