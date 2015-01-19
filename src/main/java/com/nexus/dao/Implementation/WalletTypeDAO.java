package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DaoImp;
import com.nexus.dao.entity.WalletRecord;
import com.nexus.dao.entity.WalletType;

@Repository
public class WalletTypeDAO extends DaoImp{

	@SuppressWarnings("unchecked")
	public List<WalletType> getAll() throws HibernateJdbcException {
		List<WalletType> result = new ArrayList<WalletType>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		criteria = session.createCriteria(WalletType.class);
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("name"));
		result = criteria.list();
		
		session.close();
		
		if (result.size() > 0) {
			return result;
		} else {
			List<WalletType> emptyList = Collections.emptyList();
			return emptyList;
		}
	}

	public void save(WalletType walletType) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(walletType);
		session.getTransaction().commit();
	}

	public WalletType getById(int walletType) {
		WalletType result = new WalletType();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		criteria = session.createCriteria(WalletType.class);
		criteria.add(Restrictions.idEq(walletType));
		result = (WalletType) criteria.list().get(0);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<WalletType> getAllParents() {
		List<WalletType> result = new ArrayList<WalletType>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		criteria = session.createCriteria(WalletType.class);
		criteria.add(Restrictions.isNull("parentType"));
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("name"));
		result = criteria.list();
		
		session.close();
		
		if (result.size() > 0) {
			return result;
		} else {
			List<WalletType> emptyList = Collections.emptyList();
			return emptyList;
		}
	}

	public void removeById(int id) {
		WalletType result = new WalletType();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		criteria = session.createCriteria(WalletType.class);
		criteria.add(Restrictions.idEq(id));
		result = (WalletType) criteria.list().get(0);
		session.delete(result);
		session.getTransaction().commit();
		session.close();
	}
	
	public void update(WalletType walletType) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(walletType);
		session.getTransaction().commit();
		session.close();
}
	
}
