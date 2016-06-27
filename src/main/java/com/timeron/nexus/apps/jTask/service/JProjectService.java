package com.timeron.nexus.apps.jTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.NexusDatabaseLibrary.Entity.JProject;
import com.timeron.NexusDatabaseLibrary.Entity.JTask;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.JTaskDAO;
import com.timeron.nexus.apps.jTask.dto.bean.JProjectDTO;

@Component
public class JProjectService {
	
	@Autowired
	JTaskDAO jTaskDao;
	@Autowired
	JProjectDAO jProjectDao;
	
	public JProjectDTO getProjectByTaskId(int taskId) {
		JTask jTask = jTaskDao.getById(taskId);
		JProject jProject = jProjectDao.getById(jTask.getProject().getId());
		JProjectDTO jProjectDto = new JProjectDTO(jProject);
		return jProjectDto;
	}

	
	
}
