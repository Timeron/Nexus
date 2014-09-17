package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DaoImp;
import com.nexus.dao.Implementation.helper.PersonDaoHelper;
import com.nexus.dao.entity.NexusPerson;

@Repository
public class PersonDAO extends DaoImp{

	static Logger log = Logger.getLogger(PersonDAO.class.getName());
	
	public void savePerson(NexusPerson person) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(person);
		session.getTransaction().commit();
		session.close();
		log.info("Person saved");
	}

	@SuppressWarnings("unchecked")
	public List<NexusPerson> searchPerson(NexusPerson nexusPerson) {
		List<NexusPerson> nexusPersonList = new ArrayList<NexusPerson>();

		PersonDaoHelper personDaoHelper = new PersonDaoHelper();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery(personDaoHelper
				.buildQuerySearchPerson(nexusPerson));
		nexusPersonList = (List<NexusPerson>) query.list();
		session.close();
		if (nexusPersonList.size() > 0) {
			return nexusPersonList;
		} else {
			List<NexusPerson> emptyList = Collections.emptyList();
			return emptyList;
		}

	}
	
	public NexusPerson searchPerson(int id) {
		NexusPerson nexusPerson = new NexusPerson();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		nexusPerson = (NexusPerson) session.get(NexusPerson.class, id);
		session.close();
		return nexusPerson;
	}

	public void updatePerson(NexusPerson nexusPerson) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(nexusPerson);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<NexusPerson> getAllContacts() {
		List<NexusPerson> nexusPersonList = new ArrayList<NexusPerson>();
		PersonDaoHelper personDaoHelper = new PersonDaoHelper();
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery(personDaoHelper
				.buildGetAllPerson());
		nexusPersonList = (List<NexusPerson>) query.list();
		
		session.close();
		
		if (nexusPersonList.size() > 0) {
			return nexusPersonList;
		} else {
			List<NexusPerson> emptyList = Collections.emptyList();
			return emptyList;
		}
	}
}
