package com.nexus.apps.jTask.dto.list;

import java.util.ArrayList;
import java.util.List;

import com.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.timeron.NexusDatabaseLibrary.Entity.JProject;

public class JProjectListDTO {

	List<JProjectDTO> projectListDTO = new ArrayList<JProjectDTO>();

	public List<JProjectDTO> getProjectListDTO() {
		return projectListDTO;
	}

	public void setProjectListDTO(List<JProject> projectList) {
		if(!projectList.isEmpty()){
			for(JProject project : projectList){
				projectListDTO.add(new JProjectDTO(project));
			}
		}
	}
}
