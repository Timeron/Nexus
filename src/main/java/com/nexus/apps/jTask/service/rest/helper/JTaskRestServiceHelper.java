package com.nexus.apps.jTask.service.rest.helper;

import java.util.ArrayList;
import java.util.List;

import com.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.timeron.NexusDatabaseLibrary.Entity.JProject;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;

public class JTaskRestServiceHelper {

	JProjectDAO jProjectDAO = new JProjectDAO();
	
	public List<JProjectDTO> getProjectList(){
		List<JProjectDTO> projectListDTO = new ArrayList<JProjectDTO>();
		for(JProject project : jProjectDAO.getAll()){
			projectListDTO.add(new JProjectDTO(project));
		}
		return projectListDTO;
	}
	
}
