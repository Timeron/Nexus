package com.nexus.dao.Implementation;

import org.apache.log4j.Logger;
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

}
