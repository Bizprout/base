package com.bizprout.web.app.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.dto.UserEditVO;
import com.bizprout.web.app.resource.AESencrp;

@Repository
public class UserRepositoryImpl extends AbstractBaseRepository<UserDTO> {

	@Autowired
	private SessionFactory factory;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	public List<UserDTO> getusers() {
		List<UserDTO> user = null;
		try {
			logger.info("Inside getusers method......."+this.getClass());

			Session session = getSession();

			user= session.createQuery("from UserDTO where usertype!='PPsuperadmin'").list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return user;
	}

	public UserDTO getUserData(String username) {
		Session session;
		Query qry = null;
		try {
			logger.info("Inside getUserData method......."+this.getClass());

			session = getSession();

			qry=session.createQuery("from UserDTO where username=:username");

			qry.setParameter("username",username);

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return (UserDTO) qry.uniqueResult();
	}

	public UserDTO getUserDataById(int userid) 
	{
		logger.info("Inside getUserDataById method......."+this.getClass());

		UserDTO comp = null;
		try {
			Session session = getSession();

			Criteria cr = session.createCriteria(UserDTO.class)
					.add(Restrictions.eq("userid", userid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("username"), "username")
							.add(Projections.property("password"), "password"))
							.setResultTransformer(Transformers.aliasToBean(UserDTO.class));

			comp = (UserDTO) cr.uniqueResult();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}

		return comp;
	}

	public int UpdateUsers(UserEditVO usereditVO) {

		int result = 0;
		Session session = null;

		try {
			logger.info("Inside UpdateUsers method......."+this.getClass());

			session = getSession();

			Query qry=session.createQuery("UPDATE UserDTO set username=:editusername, usertype=:usertyp, userstatus=:userstat, emailid=:email, mobile=:phone WHERE username=:oldusername");

			qry.setParameter("editusername",usereditVO.getEditusername());
			qry.setParameter("usertyp", usereditVO.getUsertype());
			qry.setParameter("userstat", usereditVO.getUserstatus());
			qry.setParameter("email", usereditVO.getEmailid());
			qry.setParameter("phone", usereditVO.getMobile());
			qry.setParameter("oldusername", usereditVO.getUsername());

			result= qry.executeUpdate();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<String> getUsernameList()
	{
		Session session;
		Query qry=null;
		List<String> usernamelist = null;
		try {
			logger.info("Inside....getUsernameList method......."+this.getClass());

			session = getSession();

			qry=session.createQuery("select username from UserDTO where usertype!='PPsuperadmin'");
			usernamelist=qry.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return usernamelist;
	}

	public UserDTO getLoginUser(String username,String password) {

		Session session;
		Query qry = null;

		try {
			logger.info("Inside getLoginUser method......."+this.getClass());

			session = getSession();

			//encrypt the password to match db value
			password=AESencrp.encrypt(password);

			qry=session.createQuery("from UserDTO where username=:user and password=:pwd and userstatus='Active'");

			qry.setParameter("user",username);
			qry.setParameter("pwd",password);

		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return (UserDTO) qry.uniqueResult();
	}

	public int updatePassword(String password, int cmpid) {

		int result = 0;
		Session session = null;
		try {
			logger.info("Inside updatePassword method......."+this.getClass());

			session = getSession();

			Query qry=session.createQuery("UPDATE UserDTO set password=:pass WHERE userid=:userd");

			qry.setParameter("pass",password);
			qry.setParameter("userd",cmpid);

			result= qry.executeUpdate();
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return result;
	}

	public int resetPassword(String username, String password) {

		int result = 0;
		Session session = null;

		try {
			logger.info("Inside resetPassword method......."+this.getClass());

			session = getSession();

			Query qry=session.createQuery("UPDATE UserDTO set password=:pass WHERE username=:user");

			qry.setParameter("pass",password);
			qry.setParameter("user",username);

			result= qry.executeUpdate();
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return result;

	}

}
