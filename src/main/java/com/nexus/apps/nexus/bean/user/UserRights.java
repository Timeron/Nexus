package com.nexus.apps.nexus.bean.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexus.apps.nexus.dto.NexusUserApplicationRefDTO;
import com.nexus.common.dto.NexusPersonDTO;
import com.timeron.NexusDatabaseLibrary.Entity.NexusApplication;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;
import com.timeron.NexusDatabaseLibrary.Entity.NexusUser;
import com.timeron.NexusDatabaseLibrary.Entity.NexusUserApplicationRef;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusUserApplicationDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusUserDAO;

@Component
public class UserRights {

	@Autowired
	NexusPersonDAO nexusPersonDAO;
	@Autowired
	NexusUserDAO nexusUserDAO;
	@Autowired
	NexusUserApplicationDAO nexusUserApplicationDAO;

	public List<NexusPersonDTO> getUsersWithAccessToNexus() {

		List<NexusPersonDTO> personInNexus = new ArrayList<NexusPersonDTO>();

		List<NexusPerson> allUsers = nexusPersonDAO.getAll();
		List<NexusUser> usersInNexus = nexusUserDAO.getAll();

		for (NexusPerson entryPerson : allUsers) {
			for (NexusUser entryUser : usersInNexus) {
				if (entryPerson.getNick().equals(entryUser.getUsername())) {
					personInNexus.add(new NexusPersonDTO(entryPerson));
				}
			}
		}
		return personInNexus;
	}

	public List<NexusPersonDTO> getUsersWithAccessToApplication(
			NexusApplication app) {
		List<NexusPersonDTO> usersDTO = new ArrayList<NexusPersonDTO>();
		List<NexusPerson> users = new ArrayList<NexusPerson>();
		users = nexusUserApplicationDAO.getUsersWithAccessToApp(app);
		for (NexusPerson user : users) {
			usersDTO.add(new NexusPersonDTO(user));
		}
		return usersDTO;
	}

	public void removeAccessToApplicationForAllUsers(NexusApplication app) throws TransactionException {
		List<NexusUserApplicationRefDTO> dtoList = new ArrayList<NexusUserApplicationRefDTO>();
		for (NexusUserApplicationRef userAppsRef : nexusUserApplicationDAO
				.getAllWithAccessToApp(app)) {
			dtoList.add(new NexusUserApplicationRefDTO(userAppsRef));
		}
		for (NexusUserApplicationRefDTO userAppsRef : dtoList) {
			nexusUserApplicationDAO.removeById(userAppsRef.getId());
		}
	}

}
