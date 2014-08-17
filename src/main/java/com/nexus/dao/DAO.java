package com.nexus.dao;

import org.hibernate.SessionFactory;

public interface DAO {

	public SessionFactory getSessionFactory();
	public void setSessionFactory(SessionFactory sessionFactory);
	
}
