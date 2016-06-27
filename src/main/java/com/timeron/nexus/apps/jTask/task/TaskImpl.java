package com.timeron.nexus.apps.jTask.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.NexusDatabaseLibrary.Entity.JTask;
import com.timeron.NexusDatabaseLibrary.dao.JTaskDAO;
import com.timeron.nexus.apps.jTask.dto.bean.JTaskDTO;

@Component
public class TaskImpl {
	
	@Autowired
	JTaskDAO jTaskDAO;

	public List<JTaskDTO> getSubTasks(Integer taskId){
		List<JTaskDTO> subTasksDTO = new ArrayList<JTaskDTO>();
		JTaskDTO taskDTO;
		List<JTask> subTasks = jTaskDAO.getByMainTask(jTaskDAO.getById(taskId));
		for(JTask taskEntry : subTasks){
			taskDTO = new JTaskDTO(taskEntry);
			subTasksDTO.add(taskDTO);
		}
		return subTasksDTO;
	}
}
