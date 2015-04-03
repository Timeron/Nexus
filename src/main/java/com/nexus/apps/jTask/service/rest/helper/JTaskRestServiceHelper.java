package com.nexus.apps.jTask.service.rest.helper;

import com.nexus.apps.jTask.dto.JProjectListDTO;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;

public class JTaskRestServiceHelper {

	JProjectDAO jProjectDAO = new JProjectDAO();
	
	public JProjectListDTO getProjectList(){
		JProjectListDTO projectListDTO = new JProjectListDTO();
		projectListDTO.setProjectList(jProjectDAO.getAll());
		return projectListDTO;
	}
	
}
