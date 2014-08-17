package com.nexus.dao.Implementation;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nexus.dao.entity.NexusPerson;

@Repository
public class PersonDAO {
	
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

	public NexusPerson searchPerson(NexusPerson person) {
		NexusPerson nexusPerson = new NexusPerson();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("FROM NexusPerson where firstName = 'Mi&#322;osz'");
		nexusPerson = (NexusPerson) query.list().get(0);
		return nexusPerson;
	}
}
