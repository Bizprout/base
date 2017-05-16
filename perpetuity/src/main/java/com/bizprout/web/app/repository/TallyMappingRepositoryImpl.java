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
import com.bizprout.web.app.dto.ClientDTO;
import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.dto.PpMasterDTO;
import com.bizprout.web.app.dto.TallyMappingDTO;
import com.bizprout.web.app.dto.TallyMastersDTO;

@Repository
public class TallyMappingRepositoryImpl extends AbstractBaseRepository<TallyMappingDTO> {
	
	@Autowired  
	private SessionFactory factory;

	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public List<CompanyDTO> getCompanyIdName(int clientid)
	{
		logger.info("Inside getCompanyIdName method.......");
		
		Session session = factory.getCurrentSession();

		Criteria cr = session.createCriteria(CompanyDTO.class)
				.add(Restrictions.eq("clientId", clientid))
				.setProjection(Projections.projectionList()
						.add(Projections.property("cmpId"), "cmpId")
						.add(Projections.property("tallyCmpName"), "tallyCmpName"))
						.setResultTransformer(Transformers.aliasToBean(CompanyDTO.class));

		List<CompanyDTO> comp=cr.list();

		return comp;
	}
	
	public List<TallyMastersDTO> getTallyMasterNames(String mastertype, int cmpid)
	{
	logger.info("Inside getTallyMasterNames method.......");
		
		Session session = factory.getCurrentSession();

		Criteria cr = session.createCriteria(TallyMastersDTO.class)
				.add(Restrictions.eq("masterType", mastertype))
				.add(Restrictions.eq("cmpId", cmpid))
				.setProjection(Projections.projectionList()
						.add(Projections.property("cmpId"), "cmpId")
						.add(Projections.property("masterId"), "masterId")
						.add(Projections.property("tallyMasterName"), "tallyMasterName"))
						.setResultTransformer(Transformers.aliasToBean(TallyMastersDTO.class));

		List<TallyMastersDTO> tallymasters=cr.list();

		return tallymasters;
	}
	
	public List<PpMasterDTO> getPpMasterNames(String mastertype, int cmpid)
	{
	logger.info("Inside getPpMasterNames method.......");
		
		Session session = factory.getCurrentSession();

		Criteria cr = session.createCriteria(PpMasterDTO.class)
				.add(Restrictions.eq("mastertype", mastertype))
				.add(Restrictions.eq("cmpid", cmpid))
				.setProjection(Projections.projectionList()
						.add(Projections.property("masteridindex"), "masteridindex")
						.add(Projections.property("ppmastername"), "ppmastername"))
						.setResultTransformer(Transformers.aliasToBean(PpMasterDTO.class));

		List<PpMasterDTO> ppmasternames=cr.list();

		return ppmasternames;
	}
	
	
	public int updateTallyMapping(TallyMappingDTO tallymappingdto) {
		
		int result = 0;
		Session session = null;
		Transaction tx = null;

		try {
			logger.info("Inside updateTallyMapping method.......");

			session = factory.getCurrentSession();

			tx=session.beginTransaction();
			
			Query qry=session.createQuery("UPDATE TallyMappingDTO set ppId=:ppid WHERE cmpId=:cmpid and tallyMasterId=:tallymasterid");
			qry.setParameter("ppid", tallymappingdto.getPpId());
			qry.setParameter("cmpid", tallymappingdto.getCmpId());
			qry.setParameter("tallymasterid", tallymappingdto.getTallyMasterId());
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
	
	public List<Integer> getPpMastersMapping(int cmpid, int ppid)
	{
		Session session;
		Query qry=null;
		List<Integer> ppmapping = null;
		try {
			logger.info("Inside TallyMappingRepositoryImpl......getPpMastersMapping method.......");

			session = factory.getCurrentSession();

			qry=session.createQuery("select tallyMasterId from TallyMappingDTO where cmpId=:cmpid and ppId=:ppid");
			qry.setParameter("cmpid",cmpid);
			qry.setParameter("ppid",ppid);
			ppmapping=qry.list();

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmapping;
	}
	
	public List<TallyMastersDTO> getTallyPpMappingData(int cmpid, String mastertype)
	{
		List<TallyMastersDTO> tallyppmappingdata = null;
		Query qry=null;
		try {
			logger.info("Inside getTallyPpMappingData method.......");

			Session session = factory.getCurrentSession();

			qry= session.createQuery("from TallyMastersDTO where cmpId=:companyid and masterType=:mastertyp");
			qry.setParameter("companyid", cmpid);
			qry.setParameter("mastertyp", mastertype);
			
			tallyppmappingdata=qry.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return tallyppmappingdata;
	}
	
	public int savePpMasterMapping(List<TallyMastersDTO> tallymasterdto)
	{
		int result = 0;
		Session session = null;
		Transaction tx = null;

		try {
			logger.info("Inside savePpMasterMapping method.......");

			session = factory.getCurrentSession();

			tx=session.beginTransaction();
			
			for (TallyMastersDTO temp : tallymasterdto) {
				
				Query qry=session.createQuery("UPDATE TallyMastersDTO set ppid=:pp WHERE cmpId=:cmpid and masterId=:tallymasterid");
				qry.setParameter("pp", temp.getPpid());
				qry.setParameter("cmpid", temp.getCmpId());
				qry.setParameter("tallymasterid", temp.getMasterId());
				result= qry.executeUpdate();
			}	
			
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
	
	public List<PpMasterDTO> getPpMasterIdNames(int cmpid, String mastertype, String ppmastername)
	{
	logger.info("Inside getPpMasterIdNames method.......");
		
		Session session = factory.getCurrentSession();

		Criteria cr = session.createCriteria(PpMasterDTO.class)
				.add(Restrictions.eq("mastertype", mastertype))
				.add(Restrictions.eq("cmpid", cmpid))
				.add(Restrictions.eq("ppmastername", ppmastername))
				.setProjection(Projections.projectionList()
						.add(Projections.property("masteridindex"), "masteridindex"))
						.setResultTransformer(Transformers.aliasToBean(PpMasterDTO.class));

		List<PpMasterDTO> ppmasternames=cr.list();

		return ppmasternames;
	}
	
	public List<TallyMastersDTO> getTallyMasterIdNames(String mastertype, int cmpid, String tallymastername)
	{
	logger.info("Inside getTallyMasterIdNames method.......");
		
		Session session = factory.getCurrentSession();

		Criteria cr = session.createCriteria(TallyMastersDTO.class)
				.add(Restrictions.eq("masterType", mastertype))
				.add(Restrictions.eq("cmpId", cmpid))
				.add(Restrictions.eq("tallyMasterName", tallymastername))
				.setProjection(Projections.projectionList()
						.add(Projections.property("masterId"), "masterId"))
						.setResultTransformer(Transformers.aliasToBean(TallyMastersDTO.class));

		List<TallyMastersDTO> tallymasters=cr.list();

		return tallymasters;
	}
}
