package com.bizprout.web.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.service.ReportService;
import com.bizprout.web.app.dto.ReportsDTO;
import com.bizprout.web.app.repository.ReportRepositoryImpl;

@Service
public class ReportServiceImpl implements ReportService<ReportsDTO>
{
  @Autowired
  private ReportRepositoryImpl repRepository;
  
  public ReportServiceImpl() {}
  
  Logger logger = LoggerFactory.getLogger(getClass());
  
  public List<Object> getCmpTrialBal(ReportsDTO repdto) {
    List<Object> cmpTB = null;
    try
    {
      logger.info("inside ReportRepoImpl TBmethod "+this.getClass());
      
      cmpTB = repRepository.getCompanyTB(repdto);
      
    }
    catch (Exception e) {
    	logger.error(e.getMessage()+"..."+this.getClass());
    }
    return cmpTB;
  }
  
  public List<Object> getCmpVchData(ReportsDTO repdto) {
    List<Object> vchData = null;
    try
    {
      logger.info("inside ReportRepoImpl TBmethod "+this.getClass());
      
      vchData = repRepository.getVchData(repdto);
    }
    catch (Exception e) {
    	logger.error(e.getMessage()+"..."+this.getClass());
    }
    return vchData;
  }
  
  public List<Object> getPymtVchData(ReportsDTO repdto) {
	    List<Object> vchData = null;
	    try
	    {
	      logger.info("inside ReportService Impl pymt vch method "+this.getClass());
	      
	      vchData = repRepository.getPymtVchData(repdto);
	      
	    }
	    catch (Exception e) {
	    	logger.error(e.getMessage()+"..."+this.getClass());
	    }
	    return vchData;
	  }


}