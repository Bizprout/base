package com.bizprout.web.app.repository;


import java.util.List;

import org.hibernate.HibernateException;
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
  
  public List<Object> getVchData(ReportsDTO repdto) { 
	Session session = null;
    List<Object> vchData = null;
    
    java.util.Date vchFromDate = repdto.getFromDate();
    java.sql.Date sqlvchFromDate= new java.sql.Date(vchFromDate .getTime());
    java.util.Date vchToDate = repdto.getToDate();
    java.sql.Date sqlvchToDate = new java.sql.Date(vchToDate .getTime());
    
    try {
      logger.info("Inside CompanyRepositoryImpl......getCompanyData method......."+this.getClass());
      
      session = factory.getCurrentSession();
//      vchData= session.CreateQuery("select * from VouchersDTO vch, TallyMastersDTO as tm where vch.cmpId=:cmpid and tm.baseGrp=:value1") //conditions if any)
//    		  .setParameter("cmpid",1)
//    		  .SetString("value1","Payment")
//    		  .List<vch[]>();
      
      vchData= session.createQuery(" from VouchersDTO vch where vch.cmpId = ? and vch.vchDate>=? and vch.vchDate<=?")
    		  .setParameter(0, repdto.getCmpId())
    		  .setParameter(1,sqlvchFromDate)
    		  .setParameter(2,sqlvchToDate)
    		  .list();// and vchDate = ?

    }
    catch (HibernateException e)
    {
    	logger.error(e.getMessage()+"..."+this.getClass());
    }
    finally {
      session.close();
    }
    
    return vchData;
  }
  
  public List<Object> getPymtVchData(ReportsDTO repdto) { 
	Session session = null;
    List<Object> vchData = null;
    
    java.util.Date vchFromDate = repdto.getFromDate();
    java.sql.Date sqlvchFromDate= new java.sql.Date(vchFromDate .getTime());
    java.util.Date vchToDate = repdto.getToDate();
    java.sql.Date sqlvchToDate = new java.sql.Date(vchToDate .getTime());
    
    
    try {
      logger.info("Inside Report RepositoryImpl......Get Paymentts Data method......."+this.getClass());
      
      session = factory.getCurrentSession();
      vchData= session.createQuery(" from VouchersDTO where cmpId = ? and vch_type_id=? and vchDate>=? and vchDate<=? order by vchDate")
    		  .setParameter(0, repdto.getCmpId())
    		  .setParameter(1, 33)
    		  .setParameter(2,sqlvchFromDate)
    		  .setParameter(3,sqlvchToDate)
    		  .list();// and vchDate = ?
//      hql.setParameter(0,1);
//      hql.setParameter(1,sqlDate);
      
    }
    catch (HibernateException e)
    {
    	logger.error(e.getMessage()+"..."+this.getClass());
    }
    finally {
      session.close();
    }
    
    return vchData;
  }  
  

  public List<Object> getCompanyTB(ReportsDTO repdto) {
  {
    Session session = null;
    List<Object> compTB = null;
    try {
      logger.info("Inside CompanyRepositoryImpl......getCompanyData method......."+this.getClass());
      
      session = factory.getCurrentSession();
      compTB= session.createQuery(" from TrialBalanceDTO where cmpId=?")
    		  .setParameter(0, repdto.getCmpId())    		  
    		  .list();
//    		  session.createQuery(" Select baseGrp,SUM(opDrAmt) as opDrAmt,SUM(opCrAmt) as opCrAmt,SUM(vchDrAmt),SUM(vchCrAmt) from TrialBalanceDTO where cmpId = ? Group By baseGrp order by baseGrp")
//    		  .setParameter(0, 1).list();		
//      Query sql = session.createSQLQuery("select cmp_id,base_group_name,category,SUM(op_dr_amt) as opDrAmt,SUM(op_cr_amt) as opCrAmt,SUM(vch_dr_amt) as vchDrAmt,SUM(vch_cr_amt) as vchCrAmt from TB_Summary"
//      		+ " where cmp_id=? group by cmp_id,category,base_group_name order by category,base_group_name")
//      		.setParameter(0, 1);
      
//      Query sql = session.createSQLQuery("select cmp_id as cmpId,base_group_name as GroupName, SUM(op_dr_amt) as opDrAmt, SUM(op_cr_amt) as opCrAmt, SUM(vch_dr_amt) as vchDrAmt, SUM(vch_cr_amt) as vchCrAmt from TB_Summary where cmp_id=1 group by cmp_id,base_group_name");
//      compTB = sql.list();		//session.createQuery("select cmpId, SUM(opDrAmt), SUM(opCrAmt), SUM(vchDrAamt), SUM(vchCrAmt) from TrialBalanceDTO  group by cmpId").list();

//      for (Iterator iterator = 
//    		  compTB.iterator(); iterator.hasNext();){
//    	  	TrialBalanceDTO tb= (TrialBalanceDTO) iterator.next(); 
//}
    }
    catch (HibernateException e)    {
    	logger.error(e.getMessage()+"..."+this.getClass());
    }
    finally {
      session.close();
    }
    
    return compTB;
  }
  }
}