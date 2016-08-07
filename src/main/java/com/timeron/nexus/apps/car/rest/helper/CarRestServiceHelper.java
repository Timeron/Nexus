package com.timeron.nexus.apps.car.rest.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.NexusDatabaseLibrary.Entity.Fuel;
import com.timeron.NexusDatabaseLibrary.dao.FuelDAO;
import com.timeron.NexusDatabaseLibrary.dao.Enum.Direction;
import com.timeron.nexus.apps.car.dto.RecordMobileDTO;
import com.timeron.nexus.apps.car.rest.ResultCarMessages;
import com.timeron.nexus.common.service.ServiceResult;

@Component
public class CarRestServiceHelper {

	@Autowired
	private FuelDAO fuelDAO;
	
	public ServiceResult addRecord(RecordMobileDTO recordDTO) {
		ServiceResult result = new ServiceResult();
		Fuel fuel = new Fuel();
		fuel.setDistance(recordDTO.getDistance());
		if(recordDTO.getDate() != 0){
			fuel.setDate(new Date(recordDTO.getDate()));
		}else{
			fuel.setDate(new Date());
		}
		fuel.setLiters(recordDTO.getLiters());
		result.setSuccess(fuelDAO.save(fuel));
		if(result.isSuccess()){
			result.addMessage(ResultCarMessages.RECORD_ADDED);
		}
		return result;
	}

	public List<RecordMobileDTO> getRecords() {
		List<RecordMobileDTO> recordMobileDTOs = new ArrayList<RecordMobileDTO>(); 
		RecordMobileDTO recordMobileDTO;
		List<Fuel> fuels = fuelDAO.getAll("date", Direction.DESC);
		for(Fuel fuel : fuels){
			String formattedDate = new SimpleDateFormat("yyyy / MM / dd").format(fuel.getDate());
			recordMobileDTO = new RecordMobileDTO();
			recordMobileDTO.setDateString(formattedDate);
			recordMobileDTO.setDistance(fuel.getDistance());
			recordMobileDTO.setLiters(fuel.getLiters());
			recordMobileDTOs.add(recordMobileDTO);
		}
		return recordMobileDTOs;
	}

}
