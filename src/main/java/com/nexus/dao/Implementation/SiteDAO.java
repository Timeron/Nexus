package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DaoImp;
import com.nexus.dao.entity.ObservedLinksPackage;
import com.nexus.dao.entity.Site;

@Repository
public class SiteDAO extends DaoImp{
	
	@Autowired
	ObservedLinksPackageDAO observedLinksPackageDAO;
	@Autowired
	SiteTypeDAO siteTypeDAO;
	
	static Logger log = Logger.getLogger(SiteDAO.class.getName());
	
	public void saveSite(Site site){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(site);
		session.getTransaction().commit();
		session.close();
//		if(!site.getObservedLinksPackage().isEmpty()){
//			for(ObservedLinksPackage observedLinksPackage : site.getObservedLinksPackage()){
//				observedLinksPackage.setSite(site);
//				observedLinksPackage.setSiteType(siteTypeDAO.getByDescription(observedLinksPackage.getSiteType().getDescription()));
//				observedLinksPackageDAO.save(observedLinksPackage);
//			}
//		}
		log.info("Site saved");
	}

	@SuppressWarnings("unchecked")
	public List<Site> getAllSites() {
		List<Site> sites = new ArrayList<Site>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "FROM Site";
		
		
		Query query = session.createQuery(hql);
		sites = (List<Site>) query.list();
		
		session.close();
		
		if (sites.size() > 0) {
			return sites;
		} else {
			List<Site> emptyList = Collections.emptyList();
			return emptyList;
		}
	}

	public Site getSiteById(Integer id) {
		Site site = new Site();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		site = (Site) session.get(Site.class, id);
		session.close();
		return site;
		
	}
	
}
