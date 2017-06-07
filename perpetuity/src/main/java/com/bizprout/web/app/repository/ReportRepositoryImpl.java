package com.bizprout.web.app.repository;


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
import com.bizprout.web.app.dto.ReportsDTO;

@Repository
public class ReportRepositoryImpl extends AbstractBaseRepository<ReportsDTO>
{
	@Autowired
	private SessionFactory factory;
	Logger logger = LoggerFactory.getLogger(getClass());


	public ReportRepositoryImpl() {}

	@SuppressWarnings("unchecked")
	public List<Object> getCmpDaybook(ReportsDTO repdto) { 
		Session session = null;
		List<Object> vchData = null;

		java.util.Date vchFromDate = repdto.getFromDate();
		java.sql.Date sqlvchFromDate= new java.sql.Date(vchFromDate .getTime());
		java.util.Date vchToDate = repdto.getToDate();
		java.sql.Date sqlvchToDate = new java.sql.Date(vchToDate .getTime());

		try {
			logger.info("Inside......getCmpDaybook method.......");

			session = getSession();
			//27-05-17 8pm
			vchData= session.createQuery(" from DaybookDTO where cmpId = ? and vchDate>=? and and vchDate<=? Order By vchDate DESC")
					.setParameter(0, repdto.getCmpId())
					.setParameter(2,sqlvchFromDate)
					.setParameter(3,sqlvchToDate)
					.list();

		}
		catch (HibernateException e)
		{
			logger.error(e.getMessage()+"..."+this.getClass());
		}

		return vchData;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getCmpVchData(ReportsDTO repdto) { 
		String repName="",vchType= "";
		Session session = null;
		List<Object> vchData = null;

		java.util.Date vchFromDate = repdto.getFromDate();
		java.sql.Date sqlvchFromDate= new java.sql.Date(vchFromDate .getTime());
		java.util.Date vchToDate = repdto.getToDate();
		java.sql.Date sqlvchToDate = new java.sql.Date(vchToDate .getTime());
		repName = repdto.getReportName();
		
		if (repName.equalsIgnoreCase("Daybook")) 
			vchType = "All";
		else if (repName.equalsIgnoreCase("Daybook-Ledgers"))
			vchType = "VchLedgers";
		else if (repName.equalsIgnoreCase("Payment Register")) 
			vchType = "Payment";
		else if (repName.equalsIgnoreCase("Sales Register"))
			vchType = "Sales";
		else if (repName.equalsIgnoreCase("Purchase Register"))
			vchType = "Purchase";
		else if (repName.equalsIgnoreCase("Receipt Register"))
			vchType = "Receipt";
		else if (repName.equalsIgnoreCase("Journal Register"))
			vchType = "Journal";
		else
			vchType = "Payment";
		
		try {
			logger.info("Inside Report RepositoryImpl......Get Vouchers Data getCmpVchData method.......");
			session = getSession();
			if (vchType.equalsIgnoreCase("All")){
				vchData= session.createQuery(" from DaybookDTO where cmpId = ? and vchDate >= ?  and vchDate <= ? Order By vchDate DESC")
						.setParameter(0, repdto.getCmpId())
						.setDate(1,sqlvchFromDate)
						.setDate(2,sqlvchToDate)
						.list();
			}
			else if (vchType.equalsIgnoreCase("VchLedgers")){
				vchData= session.createQuery(" from DaybookLedgersDTO where cmpId = ? and vchDate>=? and vchDate<=? Order By vchDate DESC")
						.setParameter(0, repdto.getCmpId())
						.setDate(1,sqlvchFromDate)
						.setDate(2,sqlvchToDate)
						.list();
				
			}
			else {
				vchData= session.createQuery(" from DaybookDTO where cmpId = ? and vchType=? and vchDate>=? and vchDate<=? Order By vchDate DESC")
						.setParameter(0, repdto.getCmpId())
						.setParameter(1, vchType)
						.setDate(2,sqlvchFromDate)
						.setDate(3,sqlvchToDate)
						.list();
			}
		}
		catch (HibernateException e)
		{
			logger.error(e.getMessage()+"...."+this.getClass());
		}

		return vchData;
	}  

	@SuppressWarnings("unchecked")
	public List<Object> getVchLedgers(String voucherId) { 

		Session session = null;
		List<Object> vchLedgers= null;

		try {
			logger.info("Inside Report RepositoryImpl...... getVchLedgers method.......");
			session = factory.getCurrentSession();
			
			vchLedgers = session.createQuery(" from DaybookLedgersDTO where vchId = ?")
					.setParameter(0, voucherId)
					.list();
		}
		catch (HibernateException e)
		{
			logger.error(e.getMessage()+"..."+this.getClass());
		}

		return vchLedgers;  
	}

	@SuppressWarnings("unchecked")
	public List<Object> getCompanyTB(ReportsDTO repdto) {
		{
			Session session = null;
			List<Object> compTB = null;
			String hql;
			try {
				logger.info("Inside......getCompanyTB method.......");
				session = getSession();

				if (repdto.getReportName().equalsIgnoreCase("Trial Balance"))
					hql = "Select new  com.bizprout.web.app.dto.TrialBalanceDTO(baseGrp, SUM(opDrAmt), SUM(opCrAmt), SUM(vchDrAmt), SUM(vchCrAmt)) "
						+ "		from TrialBalanceDTO where cmpId = ? Group by cmpId,baseGrp";
				else
					hql = "from TrialBalanceDTO where cmpId=? and (opDrAmt!=0 or opCrAmt!=0 or vchDrAmt!=0 or vchCrAmt!=0)"; /*"Select new  com.bizprout.web.app.dto.TrialBalanceDTO(name, SUM(opDrAmt), SUM(opCrAmt), SUM(vchDrAmt), SUM(vchCrAmt),"
							+ "SUM(opDrAmt-opCrAmt+vchDrAmt-vchCrAmt) as total from TrialBalanceDTO where cmpId = ? and total!=0 Group by cmpId,name )";*/
					compTB = session.createQuery(hql)
							.setParameter(0,repdto.getCmpId())
							.list();
			}
			catch (HibernateException e)    {
				logger.error(e.getMessage()+"..."+this.getClass());
			}

			return compTB;
		}
	}
}