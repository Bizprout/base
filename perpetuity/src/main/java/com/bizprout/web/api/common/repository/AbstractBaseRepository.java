package com.bizprout.web.api.common.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractBaseRepository<T> implements BaseRepository<T> {

	@Autowired
	private SessionFactory factory;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public Session getSession()
	{
		return factory.getCurrentSession();
	}

	public int save(T t) {
		logger.info("Inside Abstract class save method....."+this.getClass());
				
		int id=0;
		try {
			Session session = getSession();
			id=(int) session.save(t);
		} catch (HibernateException he) {
			logger.error(he.getMessage()+"....."+this.getClass());
		} 
		return id;
	}

	public void update(T t) {
		logger.info("Inside Abstract class update method....."+this.getClass());

		Session sess = getSession();
		try {
			sess.update(t);			
		} catch (HibernateException he) {
			logger.error(he.getMessage()+"....."+this.getClass());
		}
	}

	@SuppressWarnings("unchecked")
	public T getEntity(T t) {
		
		Criteria criteria = null;

		try {
			logger.info("Inside Abstract class getEntity method....."+this.getClass());

			Example baseDTO = Example.create(t);
			Session session = getSession();
			criteria = session.createCriteria(t.getClass()).add(baseDTO);			
		}
		catch (Exception e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> getList() {
		
		Session session = null;
		
		logger.info("Inside Abstract class getList method....."+this.getClass());
		
		try {
		session = getSession();
		
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}
		return (List<T>) session.createCriteria(
				this.getClass().getTypeParameters().getClass()).list();
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Object> getListOfProperty(Class c,String propertyName) {
		Session session = null;
		
		logger.info("Inside Abstract class getListOfProperty method....."+this.getClass());
		
		try {
			session = getSession();
			
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}
		return session.createCriteria(c)
				.setProjection(Projections.property(propertyName)).list();
	}
}
