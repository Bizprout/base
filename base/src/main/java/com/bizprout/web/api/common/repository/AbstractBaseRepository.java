package com.bizprout.web.api.common.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractBaseRepository<T> implements BaseRepository<T> {

	@Autowired
	private SessionFactory factory;

	public void save(T t) {
		// TODO change into current session
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

	}

	@SuppressWarnings("unchecked")
	public T getEntity(T t) {
		Example baseDTO = Example.create(t);
		Session session = factory.getCurrentSession();
		Criteria criteria = session.createCriteria(t.getClass()).add(baseDTO);
		return (T) criteria.uniqueResult();

	}

}
