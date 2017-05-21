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

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.dto.UserEditVO;

@Repository
public class CompanyRepositoryImpl extends AbstractBaseRepository<CompanyDTO>{

	@Autowired
	private SessionFactory factory;

	Logger logger=LoggerFactory.getLogger(this.getClass());

	public CompanyDTO getClientStatus(String companyname)
	{		
		Session session;
		Query qry=null;
		CompanyDTO compDTO = null;
		try {
			logger.info("Inside CompanyRepositoryImpl......getClientStatus method.......");

			session = factory.getCurrentSession();

			qry=session.createQuery("from CompanyDTO where tallyCmpName=:cmpname");
			qry.setParameter("cmpname",companyname);
			compDTO=(CompanyDTO) qry.uniqueResult();

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compDTO;
	}

	public int updateCompany(CompanyDTO companyDTO) {

		int result = 0;
		Session session = null;
		Transaction tx = null;

		try {
			logger.info("Inside updateCompany method.......");

			session = factory.getCurrentSession();

			tx=session.beginTransaction();

			Query qry=session.createQuery("UPDATE CompanyDTO set clientId=:clientid, appFromDate=:syncdate, uploadTimer=:uploadtime, dnldTimer=:dnldtime, maxRetrial=:retrials, status=:stat WHERE cmpId=:cmpid");
			
			qry.setParameter("clientid", companyDTO.getClientId());
			qry.setParameter("syncdate", companyDTO.getAppFromDate());
			qry.setParameter("uploadtime", companyDTO.getUploadTimer());
			qry.setParameter("dnldtime", companyDTO.getDnldTimer());
			qry.setParameter("retrials", companyDTO.getMaxRetrial());
			qry.setParameter("stat", companyDTO.getStatus());
			qry.setParameter("cmpid", companyDTO.getCmpId());

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
	
	public List<CompanyDTO> getCompanyData(int cmpid)
	{
		Session session;
		List<CompanyDTO> compDTO = null;
		try {
			logger.info("Inside CompanyRepositoryImpl......getCompanyData method.......");

			session = factory.getCurrentSession();

			Query q=session.createQuery(" from CompanyDTO where cmpId=:cmp");	
			q.setParameter("cmp", cmpid);
			compDTO=q.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return compDTO;
	}

	public int updateCompanyStatus(CompanyDTO companyDTO) {

		int result = 0;
		Session session = null;
		Transaction tx = null;

		try {
			logger.info("Inside updateCompanyStatus method.......");

			session = factory.getCurrentSession();

			tx=session.beginTransaction();

			Query qry=session.createQuery("UPDATE CompanyDTO set status=:stat WHERE clientId=:clientid");
			
			qry.setParameter("stat", companyDTO.getStatus());
			qry.setParameter("clientid", companyDTO.getClientId());

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
	
	public List<CompanyDTO> getCompanyIdName()
	{
		logger.info("Inside getCompanyIdName method.......");
		
		Session session = factory.getCurrentSession();

		Criteria cr = session.createCriteria(CompanyDTO.class)
				.add(Restrictions.eq("status", "Active"))
				.setProjection(Projections.projectionList()
						.add(Projections.property("cmpId"), "cmpId")
						.add(Projections.property("tallyCmpName"), "tallyCmpName"))
						.setResultTransformer(Transformers.aliasToBean(CompanyDTO.class));

		List<CompanyDTO> comp=cr.list();

		return comp;
	}
}
