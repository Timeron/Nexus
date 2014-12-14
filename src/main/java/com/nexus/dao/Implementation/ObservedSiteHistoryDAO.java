package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DaoImp;
import com.nexus.dao.entity.ObservedSite;
import com.nexus.dao.entity.ObservedSiteHistory;

@Repository
public class ObservedSiteHistoryDAO extends DaoImp{
	
	static Logger log = Logger.getLogger(ObservedSiteHistoryDAO.class.getName());
	
	@SuppressWarnings("unchecked")
	public List<ObservedSiteHistory> getAll() throws HibernateJdbcException {
		List<ObservedSiteHistory> result = new ArrayList<ObservedSiteHistory>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		criteria = session.createCriteria(ObservedSiteHistory.class);
		result = criteria.list();
		
		session.close();

		if (result.size() > 0) {
			return result;
		} else {
			List<ObservedSiteHistory> emptyList = Collections.emptyList();
			return emptyList;
		}
	}
	
	public List<ObservedSiteHistory> getByObservedSite(ObservedSite observedSite) throws HibernateJdbcException {
		List<ObservedSiteHistory> result = new ArrayList<ObservedSiteHistory>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		criteria = session.createCriteria(ObservedSiteHistory.class);
		criteria.add(Restrictions.eq("observedSite", observedSite));
		result = criteria.list();
		
		session.close();

		if (result.size() > 0) {
			return result;
		} else {
			List<ObservedSiteHistory> emptyList = Collections.emptyList();
			return emptyList;
		}
	}

}
