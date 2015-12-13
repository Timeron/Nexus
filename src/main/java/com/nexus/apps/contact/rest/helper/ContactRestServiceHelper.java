package com.nexus.apps.contact.rest.helper;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.mvel2.optimizers.impl.refl.nodes.ArrayLength;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.nexus.apps.contact.dto.NewContactDTO;
import com.nexus.apps.jTask.dto.bean.NexusPersonDTO;
import com.nexus.common.NexusApplicationsDTO;
import com.nexus.common.service.ServiceResult;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;

@Component
public class ContactRestServiceHelper {

	static Logger LOG = Logger.getLogger(ContactRestServiceHelper.class);
	
	@Autowired
	NexusPersonDAO nexusPersonDAO = new NexusPersonDAO();
	
	public ServiceResult addContact(NewContactDTO contactDTO, Principal principal) {
		ServiceResult result = new ServiceResult();
		NexusPerson nexusPerson = new NexusPerson();
		nexusPerson.setAddress(contactDTO.getAddress());
		nexusPerson.setBirthday(new Date(setToDate(Integer.parseInt(contactDTO.getBirthdayYear()), Integer.parseInt(contactDTO.getBirthdayMonth()), Integer.parseInt(contactDTO.getBirthdayDay()))));
		nexusPerson.setCity(contactDTO.getCity());
		nexusPerson.setCountry(contactDTO.getCountry());
		nexusPerson.setCreateTimestamp(new Date());
		nexusPerson.setDescription(contactDTO.getDescription());
		nexusPerson.setEmailOffice(contactDTO.getEmailOffice());
		nexusPerson.setEmailPrv(contactDTO.getEmailPrv());
		nexusPerson.setFirstName(contactDTO.getFirstName());
		nexusPerson.setLastName(contactDTO.getLastName());
		nexusPerson.setNameDay(new Date(setToDate(1900, Integer.parseInt(contactDTO.getNameDayMonth()), Integer.parseInt(contactDTO.getNameDayDay()))));
		nexusPerson.setNick(null);
		nexusPerson.setNickLogo(null);
		nexusPerson.setPhone1(contactDTO.getPhone1());
		nexusPerson.setPhone2(contactDTO.getPhone2());
		nexusPerson.setPhone3(contactDTO.getPhone3());
		nexusPerson.setTags(contactDTO.getTags());
		nexusPerson.setPseudo(contactDTO.getPseudo());
		nexusPerson.setUpdateTimestamp(new Date());
		try{
			nexusPersonDAO.save(nexusPerson);
			result.setSuccess(true);
		}catch(Exception ex){
			result.setSuccess(false);
			ex.printStackTrace();
		}
		
		return result;
	}

	public ServiceResult getContacts(Principal principal) {
		ServiceResult result = new ServiceResult();
		List<NexusPersonDTO> nexusPersonDTOs = new ArrayList<NexusPersonDTO>();
		try{
			for(NexusPerson person : nexusPersonDAO.getAll()){
				NexusPersonDTO nexusPersonDTO = new NexusPersonDTO(person);
				nexusPersonDTOs.add(nexusPersonDTO);
			}
			result.setObject(nexusPersonDTOs);
			result.setSuccess(true);
		}catch(Exception ex){
			result.setSuccess(false);
		}
		return result;
	}

	private long setToDate(int year, int month, int day) {
		DateTime date = new DateTime(year, month, day, 0, 0, 0, 0);
		return date.getMillis();
	}
}
