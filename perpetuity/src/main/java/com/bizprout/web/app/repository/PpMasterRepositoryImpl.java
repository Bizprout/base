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
import com.bizprout.web.app.dto.EditPpMasterDTO;
import com.bizprout.web.app.dto.PpMasterDTO;

@Repository
public class PpMasterRepositoryImpl extends AbstractBaseRepository<PpMasterDTO>{
	
	@Autowired
	private SessionFactory factory;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	public List<String> getPpMasterList(String mastertype, String category, int cmpid, String ppmastername) {
		Session session;
		Query qry=null;
		List<String> ppmasterlist = null;
		try {
			logger.info("Inside PpMasterRepositoryImpl......getPpMasterList method......."+this.getClass());

			session = getSession();

			qry=session.createQuery("select ppmastername from PpMasterDTO where mastertype=:mastertyp and category=:cate and cmpid=:cmp and ppmastername<>:ppmasname");
			qry.setParameter("mastertyp",mastertype);
			qry.setParameter("cate",category);
			qry.setParameter("cmp",cmpid);
			qry.setParameter("ppmasname", ppmastername);
			ppmasterlist=qry.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasterlist;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getPpMasterListall(String mastertype, String category, int cmpid) {
		Session session;
		Query qry=null;
		List<String> ppmasterlist = null;
		try {
			logger.info("Inside PpMasterRepositoryImpl......getPpMasterListall method......."+this.getClass());

			session = getSession();

			qry=session.createQuery("select ppmastername from PpMasterDTO where mastertype=:mastertyp and category=:cate and cmpid=:cmp");
			qry.setParameter("mastertyp",mastertype);
			qry.setParameter("cate",category);
			qry.setParameter("cmp",cmpid);
			ppmasterlist=qry.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasterlist;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getPpMasterbyCostCategory(String mastertype, int cmpid, String ppmastername) {
		Session session;
		Query qry=null;
		List<String> ppmasterlist = null;
		try {
			logger.info("Inside PpMasterRepositoryImpl......getPpMasterbyCostCategory method......."+this.getClass());

			session = getSession();

			qry=session.createQuery("select ppmastername from PpMasterDTO where mastertype=:mastertyp and cmpid=:cmp and ppmastername<>:ppmasname");
			qry.setParameter("mastertyp",mastertype);
			qry.setParameter("cmp",cmpid);
			qry.setParameter("ppmasname", ppmastername);
			ppmasterlist=qry.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasterlist;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getPpMasterListByCompany(String mastertype, int cmpid) {
		Session session;
		Query qry=null;
		List<String> ppmasterlist = null;
		try {
			logger.info("Inside PpMasterRepositoryImpl......getPpMasterListByCompany method......."+this.getClass());

			session = getSession();

			qry=session.createQuery("select ppmastername from PpMasterDTO where mastertype=:mastertyp and cmpid=:cmp");
			qry.setParameter("mastertyp",mastertype);
			qry.setParameter("cmp",cmpid);
			ppmasterlist=qry.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasterlist;
	}
	
	@SuppressWarnings("unchecked")
	public List<PpMasterDTO> getPpParentName(String mastertype, String ppmastername, int cmpid) {
		Session session;
		List<PpMasterDTO> ppparentname = null;
		try {
			logger.info("Inside PpMasterRepositoryImpl......getPpParentName method......."+this.getClass());

			session = getSession();
			
			Criteria cr = session.createCriteria(PpMasterDTO.class)
					.add(Restrictions.eq("mastertype", mastertype))
					.add(Restrictions.eq("ppmastername", ppmastername))
					.add(Restrictions.eq("cmpid", cmpid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("ppparentname"), "ppparentname")
							.add(Projections.property("category"), "category"))
							.setResultTransformer(Transformers.aliasToBean(PpMasterDTO.class));
			ppparentname=cr.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppparentname;
	}
	
	public int UpdatePpMaster(EditPpMasterDTO editppmasterDTO) {
		
		int result = 0;
		Session session = null;
		try {
			logger.info("Inside UpdatePpMaster method......."+this.getClass());

			session = getSession();
			
			Query qry=session.createQuery("UPDATE PpMasterDTO set ppmastername=:editppmastername, ppparentname=:ppparentnme, category=:cat WHERE mastertype=:mastertyp and ppmastername=:ppmasternme");
			
			qry.setParameter("editppmastername", editppmasterDTO.getEditppmastername());
			qry.setParameter("ppparentnme", editppmasterDTO.getPpparentname());
			qry.setParameter("cat", editppmasterDTO.getCategory());
			qry.setParameter("mastertyp", editppmasterDTO.getMastertype());
			qry.setParameter("ppmasternme", editppmasterDTO.getPpmastername());

			 result= qry.executeUpdate();
			 
		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<PpMasterDTO> getPpmasterData(int cmpid) {
		List<PpMasterDTO> ppmasterdata = null;
		try {
			logger.info("Inside getPpmasterData method......."+this.getClass());

			Session session = getSession();

			Query q= session.createQuery("from PpMasterDTO where cmpid=:cmp");
			q.setParameter("cmp", cmpid);
			ppmasterdata=q.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasterdata;
	}

}
