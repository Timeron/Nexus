package com.nexus.apps.jTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.timeron.NexusDatabaseLibrary.Entity.JProject;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;

@Controller
@RequestMapping("/jtask")
public class JTaskControler {
	
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
	
//	@RequestMapping(value = "/project", method = RequestMethod.GET)
//	public String project(Model model, HttpServletRequest request, HttpServletResponse response){
//		
//		return "jtaskProject";
//	}
	
}
