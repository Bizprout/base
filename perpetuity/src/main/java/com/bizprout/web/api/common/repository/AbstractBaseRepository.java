package com.bizprout.web.api.common.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bizprout.web.app.dto.UserDTO;

@Repository
public abstract class AbstractBaseRepository<T> implements BaseRepository<T> {

	@Autowired
	private SessionFactory factory;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	

	public void save(T t) {
		// TODO change into current session

		logger.info("Inside Abstract class save method.....");

		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(t);
			transaction.commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}

	}

	public void update(T t) {
		// TODO Auto-generated method stub

		logger.info("Inside Abstract class update method.....");

		Session sess = factory.openSession();
		Transaction transact = sess.beginTransaction();

		try {
			sess.update(t);
			transact.commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			transact.rollback();
		} finally {
			sess.close();
		}
	}

	@SuppressWarnings("unchecked")
	public T getEntity(T t) {

		logger.info("Inside Abstract class getEntity method.....");

		Example baseDTO = Example.create(t);
		Session session = factory.getCurrentSession();
		Criteria criteria = session.createCriteria(t.getClass()).add(baseDTO);
		return (T) criteria.uniqueResult();

	}

	public List<T> getList() {
		Session session = factory.getCurrentSession();

		return (List<T>) session.createCriteria(
				this.getClass().getTypeParameters().getClass()).list();
	}

	public List<Object> getListOfProperty(Class c,String propertyName) {
		Session session = factory.getCurrentSession();
		
		return session.createCriteria(c)
				.setProjection(Projections.property(propertyName)).list();
	}
}
