package com.bizprout.web.app.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.app.dto.ScreensDTO;
import com.bizprout.web.app.dto.UserMappingDTO;

@Repository
public class UserMappingRepositoryImpl extends AbstractBaseRepository<UserMappingDTO>{

	@Autowired
	private SessionFactory factory;

	@Autowired
	private BaseRepository<UserMappingDTO> baseRepo;


	Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<ScreensDTO> getScreenNameList()
	{
		Session session;
		Query qry=null;
		List<ScreensDTO> screenNames= null;
		try {
			logger.info("Inside UserRepositoryImpl......getScreenNameList method......."+this.getClass());

			session = factory.getCurrentSession();

			qry=session.createQuery(" from ScreensDTO");
			screenNames=qry.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return screenNames;
	}

	public List<UserMappingDTO> getUserMapData(){
		Session session;
		Query qry=null;
		List<UserMappingDTO> userAccess= null;
		try {
			logger.info("Inside UserRepositoryImpl......get User Mapping List method......."+this.getClass());

			session = factory.getCurrentSession();
			qry=session.createQuery("from UserMappingDTO");
			userAccess=qry.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return userAccess;
	}


	public UserMappingDTO getUserMapByCmpUser(int cmpid, int userid){

		logger.info("Inside getUserMapByCmpUser method......."+this.getClass());

		UserMappingDTO usermapDTO = null;
		try {
			Session session = factory.getCurrentSession();

			Criteria cr = session.createCriteria(UserMappingDTO.class)
					.add(Restrictions.eq("cmpId", cmpid))
					.add(Restrictions.eq("userid", userid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("screenId"), "screenId"))
							.setResultTransformer(Transformers.aliasToBean(UserMappingDTO.class));

			usermapDTO = (UserMappingDTO) cr.uniqueResult();
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}

		return usermapDTO;
	}

	public List<UserMappingDTO> getCmpByuserid(int userid){

		logger.info("Inside getCmpByuserid method......."+this.getClass());

		Session session;
		Query qry=null;
		List<UserMappingDTO> compmap= null;
		try {
			logger.info("Inside UserRepositoryImpl......get User Mapping List method......."+this.getClass());

			session = factory.getCurrentSession();
			qry=session.createQuery("from UserMappingDTO where userid=:user");
			qry.setParameter("user",userid);
			compmap=qry.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return compmap;
	}

	public int deleteScreensByCmpidUserid(int cmpid, int userid)
	{
		int result = 0;
		Transaction tx = null;
		Session session;
		Query qry=null;

		try {
			logger.info("Inside UserMappingRepositoryImpl......deleteScreensByCmpidUserid method......."+this.getClass());

			session = factory.getCurrentSession();

			tx=session.beginTransaction();

			qry=session.createQuery("Delete from UserMappingDTO where cmpId=:cmpid and userid=:userd");
			qry.setParameter("cmpid",cmpid);
			qry.setParameter("userd",userid);

			result= qry.executeUpdate();
			tx.commit();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return result;
	}


}
