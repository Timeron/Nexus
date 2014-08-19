package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DAO;
import com.nexus.dao.entity.NexusPerson;

@Repository
public class PersonDAO{
	
	@Autowired
	SessionFactory sessionFactory;

	public int getPerson(){
		return 0;
	}
	
	public void savePerson(NexusPerson person){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(person);
		session.getTransaction().commit();
		session.close();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<NexusPerson> searchPerson(NexusPerson person) {
		List <NexusPerson> nexusPersonList = new ArrayList<NexusPerson>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("FROM NexusPerson where firstName like 'M%'");
		nexusPersonList = (List<NexusPerson>) query.list();
		if(nexusPersonList.size()>0){
			return nexusPersonList;
		}else{
			List<NexusPerson> emptyList = Collections.emptyList();
			return emptyList;
		}
	}
}
