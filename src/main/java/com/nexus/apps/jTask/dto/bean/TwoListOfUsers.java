package com.nexus.apps.jTask.dto.bean;

import com.nexus.common.dto.NexusPersonDTO;
import java.util.List;

public class TwoListOfUsers {

	private List<NexusPersonDTO> users1;
	private List<NexusPersonDTO> users2;
	
	public List<NexusPersonDTO> getUsers1() {
		return users1;
	}
	public void setUsers1(List<NexusPersonDTO> usersWithAccessDTO) {
		this.users1 = usersWithAccessDTO;
	}
	public List<NexusPersonDTO> getUsers2() {
		return users2;
	}
	public void setUsers2(List<NexusPersonDTO> users2) {
		this.users2 = users2;
	}
	
	
	
}
