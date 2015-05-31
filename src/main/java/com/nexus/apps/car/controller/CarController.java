package com.nexus.apps.car.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.nexus.apps.car.dto.AddRecordDTO;
import com.nexus.apps.car.dto.CarReportDTO;
import com.nexus.apps.car.dto.EditRecordDTO;
import com.nexus.apps.car.dto.MainSiteDTO;
import com.nexus.apps.car.dto.RecordsDTO;
import com.nexus.apps.car.dto.chart.ConsumptionChart;
import com.nexus.apps.car.dto.chart.DateValueChart;
import com.nexus.chart.Chart;
import com.timeron.NexusDatabaseLibrary.Entity.Fuel;
import com.timeron.NexusDatabaseLibrary.dao.FuelDAO;
import com.timeron.NexusDatabaseLibrary.dao.Enum.Direction;

@Controller
@RequestMapping("/car")
public class CarController {
	
	static Logger log = Logger.getLogger(
			CarController.class.getName());
	
	@Autowired
	FuelDAO fuelDAO;
	
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
	
	@RequestMapping("/carEditRecord")
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
		
		return "carRecords";
	}
	
	@RequestMapping("/carRemoveRecord")
	public String removeRecord(ModelMap model, HttpServletRequest request, HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("id"));
		fuelDAO.removeById(id);
		
		RecordsDTO recordDTO = new RecordsDTO();
		recordDTO.setRecords(fuelDAO.getAll());
		model.addAttribute("form", recordDTO);
		
		return "carRecords";
	}
	
	@RequestMapping("/carReport")
	public String carReport(ModelMap model){
		CarReportDTO carReportDTO = new CarReportDTO();
		Gson gson = new Gson();
		List<Fuel> records = fuelDAO.getAll("date", Direction.ASC);
		List<Fuel> buildFuelList = buildFuelList(records);
		
		if(records.size()>0){
			String chartConsumption = gson.toJson(buildConsumptionChart(buildFuelList));
			String chartDistance = gson.toJson(buildDistanceChart(buildFuelList));
			String chartRefuel = gson.toJson(buildRefuelChart(records));
			
			carReportDTO.setChart(chartConsumption);
			carReportDTO.setChartDistance(chartDistance);
			carReportDTO.setChartRefuel(chartRefuel);
			
			carReportDTO.setRecords(buildFuelList);
			carReportDTO.setTotalDistance(totalDistance(records));
			carReportDTO.setTotalFuel(totalFuel(records));
			carReportDTO.setAverageFuelConsumption(averageFuelConsumption(records));
			carReportDTO.setLastAverageFuelConsumption(lastAverageFuelConsumption(records));
			carReportDTO.setLastDistance(lastDistance(records));
			carReportDTO.setLastFuel(lastFuel(records));
		}
		
		model.addAttribute("form", carReportDTO);
		return "carReport";
	}
	
	private float lastFuel(List<Fuel> records) {
		
		int currentRecord = records.size()-1;
		float lastFuel = records.get(currentRecord).getLiters();
		while(records.get(currentRecord).getDistance() == 0){
			currentRecord--;
			lastFuel += records.get(currentRecord).getLiters();
		}
		return lastFuel;
	}

	private float lastDistance(List<Fuel> records) {
		int currentRecord = records.size()-1;
		float lastDistance = records.get(currentRecord).getDistance();
		if(lastDistance==0){
			while(lastDistance==0){
				lastDistance = records.get(--currentRecord).getDistance();
			}
		}
		
		return lastDistance;
	}

	private float lastAverageFuelConsumption(List<Fuel> records) {
		float lastFuel = lastFuel(records);
		float lastDistance = lastDistance(records);
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
	
	private List<Fuel> buildFuelList(List<Fuel> records){
		List<Fuel> fuelList = new ArrayList<Fuel>();
		int currentRecord = records.size()-1;
		float liters = 0;
		float distance = 0;
		boolean city = false;
		boolean mixed = false;
		Date date = new Date();
		
		while(currentRecord >= 0){
			Fuel fuel = new Fuel();
			liters = records.get(currentRecord).getLiters();
			distance = records.get(currentRecord).getDistance();
			city = records.get(currentRecord).isCity();
			mixed = records.get(currentRecord).isMixed();
			date = records.get(currentRecord).getDate();
			while(records.get(currentRecord).getDistance() == 0){
				currentRecord--;
				liters += records.get(currentRecord).getLiters();
				distance = records.get(currentRecord).getDistance();
			}
			currentRecord--;
			fuel.setLiters(liters);
			fuel.setDistance(distance);
			fuel.setCity(city);
			fuel.setMixed(mixed);
			fuel.setDate(date);
			fuelList.add(fuel);
		}
		return fuelList;
	}
	
	private float consumption(float liters, float kilometers){
		if(kilometers > 0){
			return liters*100/kilometers;
		}
		return 0;
	}
	
	private Chart buildConsumptionChart(List<Fuel> records){
		
		Chart consumptionList = new Chart();

		int id = 0;
		for (Fuel record : records) {
			ConsumptionChart consumptionChart = new ConsumptionChart();
			consumptionChart.setInterval(id++);
			consumptionChart.setValue(consumption(record.getLiters(),
					record.getDistance()));
			consumptionList.add(consumptionChart);
		}
		return consumptionList;
	}
	
	private Chart buildDistanceChart(List<Fuel> records){
		Chart distanceList = new Chart();
		DateValueChart chartRow;
		for (Fuel record : records) {
			chartRow = new DateValueChart();
			chartRow.setDate(record.getDate());
			chartRow.setValue(record.getDistance());
			distanceList.add(chartRow);
		}
		
		return distanceList;
	}
	
	private Chart buildRefuelChart(List<Fuel> records){
		Chart refuelList = new Chart();
		DateValueChart chartRow;
		for (Fuel record : records) {
			chartRow = new DateValueChart();
			chartRow.setDate(record.getDate());
			chartRow.setValue(record.getLiters());
			refuelList.add(chartRow);
		}
		
		return refuelList;
	}

}
