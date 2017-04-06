package com.bizprout.web.app.repository;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.app.dto.LoginDTO;
import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.dto.UserEditVO;

@Repository
@Transactional
public class UserRepositoryImpl extends AbstractBaseRepository<UserDTO> {

	@Autowired
	private SessionFactory factory;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<UserDTO> getusers() {
		List<UserDTO> user = null;
		try {
			logger.info("Inside getusers method.......");

			Session session = factory.getCurrentSession();

			 user= session.createQuery("from UserDTO").list();

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public UserDTO getUserData(String username) {
		Session session;
		Query qry = null;
		try {
			logger.info("Inside getLoginUser method.......");

			session = factory.getCurrentSession();

			qry=session.createQuery("from UserDTO where username=:username");

			qry.setParameter("username",username);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (UserDTO) qry.uniqueResult();
	}
	
	public int UpdateUsers(UserEditVO usereditVO) {
		
		int result = 0;
		Session session = null;
		Transaction tx = null;

		try {
			logger.info("Inside getLoginUser method.......");

			session = factory.getCurrentSession();
			
			tx=session.beginTransaction();

			Query qry=session.createQuery("UPDATE UserDTO set username=:editusername, usertype=:usertyp, userstatus=:userstat WHERE username=:oldusername");

			qry.setParameter("editusername",usereditVO.getEditusername());
			qry.setParameter("usertyp", usereditVO.getUsertype());
			qry.setParameter("userstat", usereditVO.getUserstatus());
			qry.setParameter("oldusername", usereditVO.getUsername());

			 result= qry.executeUpdate();
			 tx.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			session.close();
		}
		return result;
	}

}
