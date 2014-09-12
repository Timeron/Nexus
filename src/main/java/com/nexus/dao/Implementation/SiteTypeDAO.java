package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DaoImp;
import com.nexus.dao.entity.Site;
import com.nexus.dao.entity.SiteType;

@Repository
public class SiteTypeDAO extends DaoImp{

	static Logger log = Logger.getLogger(SiteTypeDAO.class.getName());
	
	public void saveSiteType(SiteType siteType){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(siteType);
		session.getTransaction().commit();
		session.close();
		log.info("Site saved");
	}
	
	@SuppressWarnings("unchecked")
	public List<SiteType> getAllSiteTypes() {
		List<SiteType> siteTypes = new ArrayList<SiteType>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "FROM SiteType";
		
		Query query = session.createQuery(hql);
		siteTypes = (List<SiteType>) query.list();
		
		session.close();
		
		if (siteTypes.size() > 0) {
			return siteTypes;
		} else {
			List<SiteType> emptyList = Collections.emptyList();
			return emptyList;
		}
	}

	@SuppressWarnings("unchecked")
	public SiteType getByDescription(String description) {
		List<SiteType> siteType = new ArrayList<SiteType>();
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String hql = "FROM SiteType WHERE description = '"+description+"'";
		
		Query query = session.createQuery(hql);
		siteType = (List<SiteType>) query.list();
		session.close();
		return siteType.get(0);
	}
	
}
