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

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.app.dto.EditPpMasterDTO;
import com.bizprout.web.app.dto.PpMasterDTO;

@Repository
public class PpMasterRepositoryImpl extends AbstractBaseRepository<PpMasterDTO>{
	
	@Autowired
	private SessionFactory factory;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<String> getPpMasterList(String mastertype) {
		Session session;
		Query qry=null;
		List<String> ppmasterlist = null;
		try {
			logger.info("Inside PpMasterRepositoryImpl......getPpMasterList method.......");

			session = factory.getCurrentSession();

			qry=session.createQuery("select ppmastername from PpMasterDTO where mastertype=:mastertyp");
			qry.setParameter("mastertyp",mastertype);
			ppmasterlist=qry.list();

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmasterlist;
	}
	
	public List<String> getPpParentName(String mastertype, String ppmastername) {
		Session session;
		Query qry=null;
		List<String> ppparentname = null;
		try {
			logger.info("Inside PpMasterRepositoryImpl......getPpParentName method.......");

			session = factory.getCurrentSession();

			qry=session.createQuery("select ppparentname from PpMasterDTO where mastertype=:mastertyp and ppmastername=:ppmasternme");
			qry.setParameter("mastertyp",mastertype);
			qry.setParameter("ppmasternme",ppmastername);
			ppparentname=qry.list();

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

			Query qry=session.createQuery("UPDATE PpMasterDTO set ppmastername=:editppmastername, ppparentname=:ppparentnme WHERE mastertype=:mastertyp and ppmastername=:ppmasternme");
			
			qry.setParameter("editppmastername", editppmasterDTO.getEditppmastername());
			qry.setParameter("ppparentnme", editppmasterDTO.getPpparentname());
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

}
