package com.timeron.nexus.apps.jTask.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.NexusDatabaseLibrary.Entity.JProject;
import com.timeron.NexusDatabaseLibrary.Entity.JTask;
import com.timeron.NexusDatabaseLibrary.Entity.JUserProject;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.JUserProjectDAO;
import com.timeron.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JTaskDTO;

@Component
public class ProjectImpl {
	
	@Autowired
	JProjectDAO jProjectDAO;
	@Autowired
	JUserProjectDAO jUserProjectDAO;

	public List<JProjectDTO> getUserProjectsWithTask(NexusPerson user){
		List<JProjectDTO> userProjectDTO = new ArrayList<JProjectDTO>();
		List<JUserProject> userProjects = jUserProjectDAO.getByUser(user);
		for(JUserProject entry : userProjects){
			JProject project = jProjectDAO.getById(entry.getProject().getId());
			JProjectDTO projectDTO = new JProjectDTO(project);
			if(!project.getTask().isEmpty() && project.getTask() != null){
				for(JTask jTask : project.getTask()){
					projectDTO.addTask(new JTaskDTO(jTask));
				}
			}
			userProjectDTO.add(projectDTO);
		}
		return userProjectDTO;
	}
	
}
