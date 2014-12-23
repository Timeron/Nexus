package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DaoImp;
import com.nexus.dao.entity.ObservedSite;
import com.nexus.dao.entity.WalletAccount;

@Repository
public class WalletDAO extends DaoImp{
	
	static Logger log = Logger.getLogger(WalletDAO.class.getName());
	
	@SuppressWarnings("unchecked")
	public List<WalletAccount> getAll() throws HibernateJdbcException {
		List<WalletAccount> result = new ArrayList<WalletAccount>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		criteria = session.createCriteria(WalletAccount.class);
		result = criteria.list();
		
		session.close();
		
		if (result.size() > 0) {
			return result;
		} else {
			List<WalletAccount> emptyList = Collections.emptyList();
			return emptyList;
		}
	}

	public void save(WalletAccount walletAccount) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(walletAccount);
		session.getTransaction().commit();
		session.close();
	}

	public WalletAccount getById(int id) {
		WalletAccount result = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			criteria = session.createCriteria(WalletAccount.class);
			criteria.add(Restrictions.idEq(id));
			result = (WalletAccount) criteria.list().get(0);
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}
	
}
