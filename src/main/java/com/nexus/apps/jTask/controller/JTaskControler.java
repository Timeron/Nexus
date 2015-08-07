package com.nexus.apps.jTask.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.nexus.apps.jTask.dto.list.JProjectListDTO;
import com.timeron.NexusDatabaseLibrary.Entity.JProject;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;

@Controller
@RequestMapping("/jtask")
public class JTaskControler {
	
	static Logger LOG = Logger.getLogger(JTaskControler.class.getName());
	
	@Autowired
	JProjectDAO jProjectDAO;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String mainSite(Model model){
		return "jtaskMainSite";
	}
	
	@RequestMapping(value = "/addProject", method = RequestMethod.GET)
	public String addProject(Model model){
		JProject jProject = new JProject();
		JProjectDTO jProjectDTO = new JProjectDTO(jProject);
		
		model.addAttribute("form", jProjectDTO);
		return "jtaskAddProject";
	}
	
	@RequestMapping(value = "/addTask", method = RequestMethod.GET)
	public String addTask(Model model){
		return "jtaskAddTask";
	}
	
	@RequestMapping(value = "/taskSearch", method = RequestMethod.GET)
	public String taskSearch(Model model){
		return "jTaskProjectTaskSearch";
	}
	
	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String projects(Model model){
		JProjectListDTO jProjectListDTO = new JProjectListDTO();
		jProjectListDTO.setProjectListDTO(jProjectDAO.getAll());
		
		model.addAttribute("projects", jProjectListDTO);
		return "jtaskProjects";
	}
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Model model){
		return "JTaskDashboard";
	}
	
	@RequestMapping(value = "/task", method = RequestMethod.GET)
	public String task(Model model){
		return "JTaskTask";
	}
	
	@RequestMapping(value = "/projectSearch", method = RequestMethod.GET)
	public String projectSearch(Model model){
		return "JTaskProjectSearch";
	}
}
