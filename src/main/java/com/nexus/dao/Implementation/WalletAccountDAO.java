package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DaoImp;
import com.nexus.dao.entity.WalletAccount;
import com.nexus.dao.entity.WalletType;

@Repository
public class WalletAccountDAO extends DaoImp{

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

	public void save(WalletAccount row) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(row);
		session.getTransaction().commit();
	}

	public WalletAccount getById(int id) {
		WalletAccount result = new WalletAccount();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		criteria = session.createCriteria(WalletAccount.class);
		criteria.add(Restrictions.idEq(id));
		result = (WalletAccount) criteria.list().get(0);
		return result;
	}
	
}
