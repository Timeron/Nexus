package com.nexus.apps.car.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexus.apps.car.dto.AddRecordDTO;
import com.nexus.apps.car.dto.EditRecordDTO;
import com.nexus.apps.car.dto.MainSiteDTO;
import com.nexus.apps.car.dto.RecordsDTO;
import com.timeron.NexusDatabaseLibrary.Entity.Fuel;
import com.timeron.NexusDatabaseLibrary.dao.FuelDAO;

@Controller
@RequestMapping("/car")
public class CarController {
	
	static Logger log = Logger.getLogger(
			CarController.class.getName());
	
	FuelDAO fuelDAO = new FuelDAO();
	
	@RequestMapping("/")
	public String mainSite(ModelMap  model){
		MainSiteDTO mainSiteDTO = new MainSiteDTO();
		List<Fuel> records = fuelDAO.getAll();
		
		if(records.size()>0){
			mainSiteDTO.setTotalDistance(totalDistance(records));
			mainSiteDTO.setTotalFuel(totalFuel(records));
			mainSiteDTO.setAverageFuelConsumption(averageFuelConsumption(records));
			mainSiteDTO.setLastAverageFuelConsumption(lastAverageFuelConsumption(records));
			mainSiteDTO.setLastDistance(lastDistance(records));
			mainSiteDTO.setLastFuel(lastFuel(records));
		}
		
		model.addAttribute("form", mainSiteDTO);
		return "carMainSite";
		
	}

	@RequestMapping("/addRecord")
	public String addRecord(ModelMap model){
		AddRecordDTO addRecordDTO = new AddRecordDTO();
		model.addAttribute("form", addRecordDTO);
		return "carAddRecord";
	}
	
	@RequestMapping("/carRecords")
	public String records(ModelMap model){
		RecordsDTO recordDTO = new RecordsDTO();
		recordDTO.setRecords(fuelDAO.getAll());
		model.addAttribute("form", recordDTO);
		return "carRecords";
	}
	
	@RequestMapping("/addRecordResult")
	public String addRecordResult(ModelMap model, @ModelAttribute("addRecordDTO") AddRecordDTO addRecordDTO){
		Fuel fuel;
		fuel = addRecordDTO.getRecord();
		
		fuelDAO.save(fuel);
		
		model.addAttribute("form", addRecordDTO);
		return "addRecordResult";
	}
	
	@RequestMapping("carEditRecord")
	public String editRecord(ModelMap model, HttpServletRequest request, HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("id"));
		EditRecordDTO editRecordDTO = new EditRecordDTO();
		editRecordDTO.setNewFuel(new Fuel());
		editRecordDTO.setOldFuel(fuelDAO.getById(id));
		model.addAttribute("form", editRecordDTO);
		
		return "carEditRecord";
	}
	
	@RequestMapping("/editRecordResult")
	public String editRecordResult(ModelMap model, @ModelAttribute("editRecordDTO") EditRecordDTO editRecordDTO){
		Fuel fuel = editRecordDTO.getNewFuel();
		fuelDAO.update(fuel);
		
		RecordsDTO recordDTO = new RecordsDTO();
		recordDTO.setRecords(fuelDAO.getAll());
		model.addAttribute("form", recordDTO);
		
		model.addAttribute("form", recordDTO);
		return "carRecords";
	}
	
	@RequestMapping("carRemoveRecord")
	public String removeRecord(ModelMap model, HttpServletRequest request, HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("id"));
		fuelDAO.removeById(id);
		
		RecordsDTO recordDTO = new RecordsDTO();
		recordDTO.setRecords(fuelDAO.getAll());
		model.addAttribute("form", recordDTO);
		
		return "carRecords";
	}
	
	private float lastFuel(List<Fuel> records) {
		float lastFuel = records.get(records.size()-1).getLiters();
		return lastFuel;
	}

	private float lastDistance(List<Fuel> records) {
		float lastDistance = records.get(records.size()-1).getDistance();
		return lastDistance;
	}

	private float lastAverageFuelConsumption(List<Fuel> records) {
		float lastFuel = records.get(records.size()-1).getLiters();
		float lastDistance = records.get(records.size()-1).getDistance();
		return lastFuel*100/lastDistance;
	}

	private float averageFuelConsumption(List<Fuel> records) {
		float averageFuelConsumption = totalFuel(records)*100/totalDistance(records);
		return averageFuelConsumption;
	}

	private float totalFuel(List<Fuel> records) {
		float liters = 0;
		
		for(Fuel record: records){
			liters += record.getLiters();
		}
		return liters;
	}

	private float totalDistance(List<Fuel> records) {
		float distance = 0;
		
		for(Fuel record: records){
			distance += record.getDistance();
		}
		return distance;
	}

}
