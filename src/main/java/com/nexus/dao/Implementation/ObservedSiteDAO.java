package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DaoImp;
import com.nexus.dao.entity.ObservedSite;

@Repository
public class ObservedSiteDAO extends DaoImp{

	static Logger log = Logger.getLogger(ObservedSiteDAO.class.getName());
	
	@SuppressWarnings("unchecked")
	public List<ObservedSite> getAll() throws HibernateJdbcException {
		List<ObservedSite> observedSite = new ArrayList<ObservedSite>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "FROM ObservedSite";
		
		Query query = session.createQuery(hql);
		observedSite = (List<ObservedSite>) query.list();
		
		session.close();
		
		if (observedSite.size() > 0) {
			return observedSite;
		} else {
			List<ObservedSite> emptyList = Collections.emptyList();
			return emptyList;
		}
	}
}
