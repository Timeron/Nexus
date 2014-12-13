package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DaoImp;
import com.nexus.dao.entity.ObservedObject;

@Repository
public class ObservedObjectDAO extends DaoImp{

	static Logger log = Logger.getLogger(ObservedSiteDAO.class.getName());
	
	public void save(ObservedObject observedObject) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(observedObject);
		session.getTransaction().commit();
		session.close();
		log.info("ObservedObjects saved");
		
	}

	public ObservedObject getById(int id) {
		ObservedObject observedObject = null;
		session = sessionFactory.openSession();
		session.beginTransaction();
		criteria = session.createCriteria(ObservedObject.class);
		criteria.add(Restrictions.idEq(id));
		observedObject = (ObservedObject) criteria.list().get(0);
		return observedObject;
	}

	@SuppressWarnings("unchecked")
	public int getId(ObservedObject observedObject) {
		List<ObservedObject> observedObjects;
		session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "FROM observedObject WHERE productKay = '"+observedObject.getProductKay()+"'";
		Query query = session.createQuery(hql);
		observedObjects = (List<ObservedObject>) query.list();
		session.close();
		return observedObjects.get(0).getId();
	}

	@SuppressWarnings("unchecked")
	public List<ObservedObject> getAll() {
		List<ObservedObject> observedObjects = new ArrayList<ObservedObject>();
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		observedObjects = session.createCriteria(ObservedObject.class).list();
		session.close();
		
		if (observedObjects.size() > 0) {
			return observedObjects;
		} else {
			List<ObservedObject> emptyList = Collections.emptyList();
			return emptyList;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ObservedObject> search(ObservedObject searchParameters) {
		List<ObservedObject> observedObjects = null;
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		criteria = session.createCriteria(ObservedObject.class);
		if(searchParameters.getId()!=null){
			criteria.add(Restrictions.idEq(searchParameters.getId()));
		}
		if(!searchParameters.getName().isEmpty() && searchParameters.getName()!=null){
			criteria.add(Restrictions.like("name", searchParameters.getName().trim()));
		}
		if(!searchParameters.getProductKay().isEmpty() && searchParameters.getProductKay()!=null){
			criteria.add(Restrictions.like("productKay", searchParameters.getProductKay().trim()));
		}
		observedObjects = criteria.list();
		session.close();
		return observedObjects;
	}

}