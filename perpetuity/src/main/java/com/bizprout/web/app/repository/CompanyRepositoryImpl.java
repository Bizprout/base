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
import com.bizprout.web.app.dto.CompanyDTO;

@Repository
public class CompanyRepositoryImpl extends AbstractBaseRepository<CompanyDTO>{

	@Autowired
	private SessionFactory factory;

	Logger logger=LoggerFactory.getLogger(this.getClass());

	public CompanyDTO getClientStatus(int cmpid)
	{		
		Session session;
		Query qry=null;
		CompanyDTO compDTO = null;
		try {
			logger.info("Inside CompanyRepositoryImpl......getClientStatus method......."+this.getClass());

			session = getSession();

			qry=session.createQuery("from CompanyDTO where cmpId=:cmpid");
			qry.setParameter("cmpid",cmpid);
			compDTO=(CompanyDTO) qry.uniqueResult();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return compDTO;
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyDTO> getCompanyDataall()
	{		
		Session session;
		Query qry=null;
		List<CompanyDTO> compDTO = null;
		try {
			logger.info("Inside CompanyRepositoryImpl......getCompanyDataall method......."+this.getClass());

			session = getSession();

			qry=session.createQuery("from CompanyDTO");
			compDTO=qry.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return compDTO;
	}
	
	public CompanyDTO getCompanyIdByName(String cmpname)
	{		
		Session session;
		Query qry=null;
		CompanyDTO compDTO = null;
		try {
						
			logger.info("Inside CompanyRepositoryImpl......getCompanyIdByName method......."+this.getClass());

			session = getSession();

			qry=session.createQuery("from CompanyDTO where tallyCmpName=:cmpname");
			qry.setParameter("cmpname",cmpname);
			compDTO=(CompanyDTO) qry.uniqueResult();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return compDTO;
	}

	public int updateCompany(CompanyDTO companyDTO) {

		int result = 0;
		Session session = null;

		try {
			logger.info("Inside updateCompany method......."+this.getClass());

			session = getSession();

			Query qry=session.createQuery("UPDATE CompanyDTO set clientId=:clientid, appFromDate=:syncdate, uploadTimer=:uploadtime, dnldTimer=:dnldtime, maxRetrial=:retrials, status=:stat WHERE cmpId=:cmpid");
			
			qry.setParameter("clientid", companyDTO.getClientId());
			qry.setParameter("syncdate", companyDTO.getAppFromDate());
			qry.setParameter("uploadtime", companyDTO.getUploadTimer());
			qry.setParameter("dnldtime", companyDTO.getDnldTimer());
			qry.setParameter("retrials", companyDTO.getMaxRetrial());
			qry.setParameter("stat", companyDTO.getStatus());
			qry.setParameter("cmpid", companyDTO.getCmpId());

			result= qry.executeUpdate();
			
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyDTO> getCompanyData(int cmpid)
	{
		Session session;
		List<CompanyDTO> compDTO = null;
		try {
			logger.info("Inside CompanyRepositoryImpl......getCompanyData method......."+this.getClass());

			session = getSession();

			Query q=session.createQuery(" from CompanyDTO where cmpId=:cmp");	
			q.setParameter("cmp", cmpid);
			compDTO=q.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return compDTO;
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyDTO> getallCompanyActive()
	{
		Session session;
		List<CompanyDTO> compDTO = null;
		try {
			logger.info("Inside CompanyRepositoryImpl......getCompanyData method......."+this.getClass());

			session = getSession();

			Query q=session.createQuery(" from CompanyDTO where status='Active'");	
			compDTO=q.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return compDTO;
	}

	public int updateCompanyStatus(CompanyDTO companyDTO) {

		int result = 0;
		Session session = null;

		try {
			logger.info("Inside updateCompanyStatus method......."+this.getClass());

			session = getSession();

			Query qry=session.createQuery("UPDATE CompanyDTO set status=:stat WHERE clientId=:clientid");
			
			qry.setParameter("stat", companyDTO.getStatus());
			qry.setParameter("clientid", companyDTO.getClientId());

			result= qry.executeUpdate();
			
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyDTO> getCompanyIdName()
	{
		logger.info("Inside getCompanyIdName method......."+this.getClass());
		
		List<CompanyDTO> comp=null;
		try {
			Session session = getSession();

			Criteria cr = session.createCriteria(CompanyDTO.class)
					.add(Restrictions.eq("status", "Active"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("cmpId"), "cmpId")
							.add(Projections.property("tallyCmpName"), "tallyCmpName"))
							.setResultTransformer(Transformers.aliasToBean(CompanyDTO.class));

			comp = cr.list();
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}

		return comp;
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyDTO> getCompanyIdNameall()
	{
		logger.info("Inside getCompanyIdNameall method......."+this.getClass());
		
		List<CompanyDTO> comp=null;
		try {
			Session session = getSession();

			Criteria cr = session.createCriteria(CompanyDTO.class)
					.setProjection(Projections.projectionList()
							.add(Projections.property("cmpId"), "cmpId")
							.add(Projections.property("tallyCmpName"), "tallyCmpName"))
							.setResultTransformer(Transformers.aliasToBean(CompanyDTO.class));

			comp = cr.list();
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}

		return comp;
	}
}
