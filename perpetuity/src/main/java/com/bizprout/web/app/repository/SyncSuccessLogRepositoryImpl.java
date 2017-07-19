package com.bizprout.web.app.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bizprout.web.api.common.repository.AbstractBaseRepository;
import com.bizprout.web.app.dto.SyncSuccessLogDTO;

@Repository
public class SyncSuccessLogRepositoryImpl extends AbstractBaseRepository<SyncSuccessLogDTO> {
	
	@Autowired  
	private SessionFactory factory;

	Logger logger=LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	public List<SyncSuccessLogDTO> getMastertypeData(int cmpid)
	{
		List<SyncSuccessLogDTO> user=null;
		Query qry=null;
		try {
			logger.info("Inside getMastertypeData method......."+this.getClass());

			Session session = getSession();
			
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			String currdate=dateformat.format(new Date());
			Date parseDate=dateformat.parse(currdate);

			qry=session.createQuery("from SyncSuccessLogDTO where cmpid=:cmp and CONVERT(date,successdate)=:now");
			qry.setParameter("cmp", cmpid);
			qry.setParameter("now", parseDate);
			
			user=qry.list();

		} catch (HibernateException | ParseException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<SyncSuccessLogDTO> getVouchertypeData(int cmpid)
	{
		List<SyncSuccessLogDTO> user=null;
		Query qry=null;
		try {
			logger.info("Inside getVouchertypeData method......."+this.getClass());

			Session session = getSession();
			
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			String currdate=dateformat.format(new Date());
			Date parseDate=dateformat.parse(currdate);

			qry=session.createQuery("from SyncSuccessLogDTO where cmpid=:cmp and synctype='Voucher' and CONVERT(date,successdate)=:now");
			qry.setParameter("cmp", cmpid);
			qry.setParameter("now", parseDate);
			
			user=qry.list();

		} catch (HibernateException | ParseException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<SyncSuccessLogDTO> getAllSyncData(int cmpid)
	{
		List<SyncSuccessLogDTO> user=null;
		Query qry=null;
		try {
			logger.info("Inside getAllSyncData method......."+this.getClass());

			Session session = getSession();

			qry=session.createQuery("from SyncSuccessLogDTO where cmpid=:cmp ORDER BY successdate DESC");
			qry.setParameter("cmp", cmpid);
			
			user=qry.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}
		return user;
	}
}
