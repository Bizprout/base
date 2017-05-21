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
import com.bizprout.web.app.dto.EditPpMasterDTO;
import com.bizprout.web.app.dto.PpMasterDTO;
import com.bizprout.web.app.dto.UserDTO;

@Repository
public class PpMasterRepositoryImpl extends AbstractBaseRepository<PpMasterDTO>{
	
	@Autowired
	private SessionFactory factory;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<String> getPpMasterList(String mastertype, String category, int cmpid, String ppmastername) {
		Session session;
		Query qry=null;
		List<String> ppmasterlist = null;
		try {
			logger.info("Inside PpMasterRepositoryImpl......getPpMasterList method.......");

			session = factory.getCurrentSession();

			qry=session.createQuery("select ppmastername from PpMasterDTO where mastertype=:mastertyp and category=:cate and cmpid=:cmp and ppmastername<>:ppmasname");
			qry.setParameter("mastertyp",mastertype);
			qry.setParameter("cate",category);
			qry.setParameter("cmp",cmpid);
			qry.setParameter("ppmasname", ppmastername);
			ppmasterlist=qry.list();

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmasterlist;
	}
	
	public List<String> getPpMasterListall(String mastertype, String category, int cmpid) {
		Session session;
		Query qry=null;
		List<String> ppmasterlist = null;
		try {
			logger.info("Inside PpMasterRepositoryImpl......getPpMasterList method.......");

			session = factory.getCurrentSession();

			qry=session.createQuery("select ppmastername from PpMasterDTO where mastertype=:mastertyp and category=:cate and cmpid=:cmp");
			qry.setParameter("mastertyp",mastertype);
			qry.setParameter("cate",category);
			qry.setParameter("cmp",cmpid);
			ppmasterlist=qry.list();

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmasterlist;
	}
	
	public List<String> getPpMasterbyCostCategory(String mastertype, int cmpid, String ppmastername) {
		Session session;
		Query qry=null;
		List<String> ppmasterlist = null;
		try {
			logger.info("Inside PpMasterRepositoryImpl......getPpMasterList method.......");

			session = factory.getCurrentSession();

			qry=session.createQuery("select ppmastername from PpMasterDTO where mastertype=:mastertyp and cmpid=:cmp and ppmastername<>:ppmasname");
			qry.setParameter("mastertyp",mastertype);
			qry.setParameter("cmp",cmpid);
			qry.setParameter("ppmasname", ppmastername);
			ppmasterlist=qry.list();

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmasterlist;
	}
	
	public List<String> getPpMasterListByCompany(String mastertype, int cmpid) {
		Session session;
		Query qry=null;
		List<String> ppmasterlist = null;
		try {
			logger.info("Inside PpMasterRepositoryImpl......getPpMasterList method.......");

			session = factory.getCurrentSession();

			qry=session.createQuery("select ppmastername from PpMasterDTO where mastertype=:mastertyp and cmpid=:cmp");
			qry.setParameter("mastertyp",mastertype);
			qry.setParameter("cmp",cmpid);
			ppmasterlist=qry.list();

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmasterlist;
	}
	
	public List<PpMasterDTO> getPpParentName(String mastertype, String ppmastername, int cmpid) {
		Session session;
		Query qry=null;
		List<PpMasterDTO> ppparentname = null;
		try {
			logger.info("Inside PpMasterRepositoryImpl......getPpParentName method.......");

			session = factory.getCurrentSession();
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppparentname;
	}
	
	public int UpdatePpMaster(EditPpMasterDTO editppmasterDTO) {
		
		int result = 0;
		Session session = null;
		Transaction tx = null;

		try {
			logger.info("Inside getLoginUser method.......");

			session = factory.getCurrentSession();
			
			tx=session.beginTransaction();

			Query qry=session.createQuery("UPDATE PpMasterDTO set ppmastername=:editppmastername, ppparentname=:ppparentnme, category=:cat WHERE mastertype=:mastertyp and ppmastername=:ppmasternme");
			
			qry.setParameter("editppmastername", editppmasterDTO.getEditppmastername());
			qry.setParameter("ppparentnme", editppmasterDTO.getPpparentname());
			qry.setParameter("cat", editppmasterDTO.getCategory());
			qry.setParameter("mastertyp", editppmasterDTO.getMastertype());
			qry.setParameter("ppmasternme", editppmasterDTO.getPpmastername());

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
	
	public List<PpMasterDTO> getPpmasterData(int cmpid) {
		List<PpMasterDTO> ppmasterdata = null;
		try {
			logger.info("Inside getPpmasterData method.......");

			Session session = factory.getCurrentSession();

			Query q= session.createQuery("from PpMasterDTO where cmpid=:cmp");
			q.setParameter("cmp", cmpid);
			ppmasterdata=q.list();

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmasterdata;
	}

}
