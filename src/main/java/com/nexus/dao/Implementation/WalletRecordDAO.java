package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DaoImp;
import com.nexus.dao.entity.ObservedSite;
import com.nexus.dao.entity.WalletAccount;
import com.nexus.dao.entity.WalletRecord;

@Repository
public class WalletRecordDAO extends DaoImp{

	@SuppressWarnings("unchecked")
	public List<WalletRecord> getAll() throws HibernateJdbcException {
		List<WalletRecord> result = new ArrayList<WalletRecord>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		criteria = session.createCriteria(WalletRecord.class);
		result = criteria.list();
		
		session.close();
		
		if (result.size() > 0) {
			return result;
		} else {
			List<WalletRecord> emptyList = Collections.emptyList();
			return emptyList;
		}
	}

	public void save(WalletRecord walletRecord) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(walletRecord);
		session.getTransaction().commit();
		session.close();
		
	}

	public void update(WalletRecord walletRecord) {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(walletRecord);
			session.getTransaction().commit();
			session.close();
	}

	public List<WalletRecord> getRecordsFromAccount(WalletAccount currentAccount) {
		List<WalletRecord> result = new ArrayList<WalletRecord>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		criteria = session.createCriteria(WalletRecord.class);
		criteria.add(Restrictions.eq("walletAccount", currentAccount));
		result = criteria.list();
		
		session.close();
		
		if (result.size() > 0) {
			return result;
		} else {
			List<WalletRecord> emptyList = Collections.emptyList();
			return emptyList;
		}
	}
	
	public WalletRecord getById(int id) {
		WalletRecord result = new WalletRecord();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		criteria = session.createCriteria(WalletRecord.class);
		criteria.add(Restrictions.idEq(id));
		result = (WalletRecord) criteria.list().get(0);
		session.close();
		return result;
	}

	public void removeById(int id) {
		WalletRecord result = new WalletRecord();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		criteria = session.createCriteria(WalletRecord.class);
		criteria.add(Restrictions.idEq(id));
		result = (WalletRecord) criteria.list().get(0);
		session.delete(result);
		session.getTransaction().commit();
		session.close();
	}
	
}
