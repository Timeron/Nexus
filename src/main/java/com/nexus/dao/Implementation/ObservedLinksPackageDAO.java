package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DaoImp;
import com.nexus.dao.entity.ObservedLinksPackage;

@Repository
public class ObservedLinksPackageDAO extends DaoImp{
	
	static Logger log = Logger.getLogger(SiteTypeDAO.class.getName());
	
	public void save(ObservedLinksPackage observedLinksPackage){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(observedLinksPackage);
		session.getTransaction().commit();
		session.close();
		log.info("ObservedLinksPackage saved");
	}

	@SuppressWarnings("unchecked")
	public List<ObservedLinksPackage> getByUrl(String url) {
		List<ObservedLinksPackage> observedLinksPackage = new ArrayList<ObservedLinksPackage>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "FROM ObservedLinksPackage WHERE url = '"+url+"'";
		
		Query query = session.createQuery(hql);
		observedLinksPackage = (List<ObservedLinksPackage>) query.list();
		
		session.close();
		
		if (observedLinksPackage.size() > 0) {
			return observedLinksPackage;
		} else {
			List<ObservedLinksPackage> emptyList = Collections.emptyList();
			return emptyList;
		}
	}

}
