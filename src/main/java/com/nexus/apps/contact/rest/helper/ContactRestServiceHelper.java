package com.nexus.apps.contact.rest.helper;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexus.apps.contact.dto.ContactDTO;
import com.nexus.apps.contact.dto.EventDTO;
import com.nexus.apps.contact.dto.OccasionDTO;
import com.nexus.apps.jTask.dto.bean.NexusPersonDTO;
import com.nexus.common.service.ServiceResult;
import com.timeron.NexusDatabaseLibrary.Entity.ContactEvent;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.dao.ContactEventDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;

@Component
public class ContactRestServiceHelper {

	static Logger LOG = Logger.getLogger(ContactRestServiceHelper.class);
	
	@Autowired
	NexusPersonDAO nexusPersonDAO = new NexusPersonDAO();
	
	@Autowired
	ContactEventDAO contactEventDAO = new ContactEventDAO();
	
	Locale localeObject=new Locale("pl");
	SimpleDateFormat format = new SimpleDateFormat("d MMMM", localeObject);
	
	public ServiceResult addContact(ContactDTO contactDTO, Principal principal) {
		ServiceResult result = new ServiceResult();
		NexusPerson nexusPerson = new NexusPerson();
		nexusPerson.setAddress(contactDTO.getAddress());
		if(!contactDTO.getBirthdayYear().isEmpty() && !contactDTO.getBirthdayMonth().isEmpty() && !contactDTO.getBirthdayDay().isEmpty()){
			nexusPerson.setBirthday(new Date(setToDate(Integer.parseInt(contactDTO.getBirthdayYear()), Integer.parseInt(contactDTO.getBirthdayMonth()), Integer.parseInt(contactDTO.getBirthdayDay())-1)));
		}
		nexusPerson.setCity(contactDTO.getCity());
		nexusPerson.setCountry(contactDTO.getCountry());
		nexusPerson.setCreateTimestamp(new Date());
		nexusPerson.setDescription(contactDTO.getDescription());
		nexusPerson.setEmailOffice(contactDTO.getEmailOffice());
		nexusPerson.setEmailPrv(contactDTO.getEmailPrv());
		nexusPerson.setFirstName(contactDTO.getFirstName());
		nexusPerson.setLastName(contactDTO.getLastName());
		if(!contactDTO.getNameDayMonth().isEmpty() && !contactDTO.getNameDayDay().isEmpty()){
			nexusPerson.setNameDay(new Date(setToDate(1900, Integer.parseInt(contactDTO.getNameDayMonth()), Integer.parseInt(contactDTO.getNameDayDay())-1)));
		}
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
	
	public ServiceResult updateContact(ContactDTO contactDTO, Principal principal) {
		ServiceResult result = new ServiceResult();
		NexusPerson nexusPerson = nexusPersonDAO.getById(contactDTO.getId());
		nexusPerson.setAddress(contactDTO.getAddress());
		if(!contactDTO.getBirthdayYear().isEmpty() && !contactDTO.getBirthdayMonth().isEmpty() && !contactDTO.getBirthdayDay().isEmpty()){
			nexusPerson.setBirthday(new Date(setToDate(Integer.parseInt(contactDTO.getBirthdayYear()), Integer.parseInt(contactDTO.getBirthdayMonth()), Integer.parseInt(contactDTO.getBirthdayDay())-1)));
		}
		nexusPerson.setCity(contactDTO.getCity());
		nexusPerson.setCountry(contactDTO.getCountry());
		nexusPerson.setCreateTimestamp(new Date());
		nexusPerson.setDescription(contactDTO.getDescription());
		nexusPerson.setEmailOffice(contactDTO.getEmailOffice());
		nexusPerson.setEmailPrv(contactDTO.getEmailPrv());
		nexusPerson.setFirstName(contactDTO.getFirstName());
		nexusPerson.setLastName(contactDTO.getLastName());
		if(!contactDTO.getNameDayMonth().isEmpty() && !contactDTO.getNameDayDay().isEmpty()){
			nexusPerson.setNameDay(new Date(setToDate(1900, Integer.parseInt(contactDTO.getNameDayMonth()), Integer.parseInt(contactDTO.getNameDayDay())-1)));
		}
		nexusPerson.setNick(null);
		nexusPerson.setNickLogo(null);
		nexusPerson.setPhone1(contactDTO.getPhone1());
		nexusPerson.setPhone2(contactDTO.getPhone2());
		nexusPerson.setPhone3(contactDTO.getPhone3());
		nexusPerson.setTags(contactDTO.getTags());
		nexusPerson.setPseudo(contactDTO.getPseudo());
		nexusPerson.setUpdateTimestamp(new Date());
		try{
			nexusPersonDAO.update(nexusPerson);
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
		day += 1; //day is counted from 0
		DateTime date = new DateTime(year, month, day, 0, 0, 0, 0);
		return date.getMillis();
	}

	public ServiceResult getContactDetails(int contactId, Principal principal) {
		ServiceResult result = new ServiceResult();
		try{
			result.setObject(new NexusPersonDTO(nexusPersonDAO.getById(contactId)));
			result.setSuccess(true);
		}catch(Exception ex){
			result.setSuccess(false);
		}
		return result;
	}

	public ServiceResult addEvent(EventDTO eventDTO, Principal principal) {
		List<NexusPerson> contacts = new ArrayList<NexusPerson>();
		ContactEvent event = new ContactEvent();
		event.setName(eventDTO.getName());
		event.setDescription(eventDTO.getDescription());
		event.setDate(new Date(setToDate(eventDTO.getEventYear(), eventDTO.getEventMonth(), eventDTO.getEventDay())));
		if(eventDTO.getContacts().size() > 0){
			for(ContactDTO contactDTO : eventDTO.getContacts()){
				NexusPerson person = nexusPersonDAO.getById(contactDTO.getId());
				contacts.add(person);
			}
		}
		event.setContacts(contacts);
		contactEventDAO.save(event);
		return null;
	}

	@SuppressWarnings("unchecked")
	public ServiceResult getOccasions(Principal principal) {
		List<OccasionDTO> occasionDTOs = new ArrayList<OccasionDTO>();
		ServiceResult result = new ServiceResult();
		List<ContactEvent> events = contactEventDAO.getAll();
		List<NexusPerson> userEvents = nexusPersonDAO.getAllWithEvent();
		OccasionDTO occasionDTO;
		for(ContactEvent event : events){
			occasionDTO = new OccasionDTO();
			occasionDTO.setName(event.getName());
			occasionDTO.setDescription(event.getDescription());
			occasionDTO.setDateStr(format.format(event.getDate().getTime()));
			occasionDTO.setDate(new DateTime(event.getDate().getTime()));
			occasionDTO.setEvent(true);
			for(NexusPerson person : event.getContacts()){
				occasionDTO.addUser(person);
			}
			occasionDTOs.add(occasionDTO);
		}
		for(NexusPerson person : userEvents){
			if(person.getBirthday() != null && person.getBirthday().getTime() != 0 ){
				occasionDTO = new OccasionDTO();
				occasionDTO.setName("Urodziny");
				occasionDTO.setDescription(new DateTime().getYear() - person.getBirthdayJoda().getYear()+" urodziny");
				occasionDTO.setDateStr(format.format(person.getBirthday().getTime()));
				occasionDTO.setDate(new DateTime(person.getBirthday().getTime()));
				occasionDTO.addUser(person);
				occasionDTO.setEvent(false);
				occasionDTOs.add(occasionDTO);
			}
			if(person.getNameDay() != null && person.getNameDay().getTime() != 0 ){
				occasionDTO = new OccasionDTO();
				occasionDTO.setName("Imieniny");
				occasionDTO.setDescription("");
				occasionDTO.setDateStr(format.format(person.getNameDay().getTime()));
				occasionDTO.setDate(new DateTime(person.getNameDay().getTime()));
				occasionDTO.addUser(person);
				occasionDTO.setEvent(false);
				occasionDTOs.add(occasionDTO);
			}
		}
		if(occasionDTOs.size() > 0){
			DateTime now = new DateTime();
			List<OccasionDTO> tempBefore = new ArrayList<OccasionDTO>();
			List<OccasionDTO> tempAfter = new ArrayList<OccasionDTO>();
			
			Collections.sort(occasionDTOs);
			for(OccasionDTO occ : occasionDTOs){
				if(occ.getDate().getDayOfYear() < now.getDayOfYear()){
					tempBefore.add(occ);
				}else{
					tempAfter.add(occ);
				}
			}
			tempAfter.addAll(tempBefore);
			
			result.setSuccess(true);
			result.setObject(tempAfter);
		}else{
			result.setSuccess(false);
		}
		
		return result;
	}


}
