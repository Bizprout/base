package com.bizprout.web.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.service.CompanyService;
import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.repository.CompanyRepositoryImpl;

@Service
public class CompanyServiceImpl implements CompanyService<CompanyDTO> {

	@Autowired
	private CompanyRepositoryImpl companyrepository;

	Logger logger=LoggerFactory.getLogger(this.getClass());

	public CompanyDTO getClientStatus(int cmpid) {
		
		CompanyDTO compDTO = null ;
		try {
			logger.info("inside getClientStatus service method "+this.getClass());
			compDTO= companyrepository.getClientStatus(cmpid);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return compDTO;
	}

	public int updateCompany(CompanyDTO companyDTO)
	{
		try {
			logger.info("inside UpdateCompany service " + companyDTO, this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return companyrepository.updateCompany(companyDTO);
	}
	
	public List<CompanyDTO> getCompanyData(int cmpid)
	{
		try {
			logger.info("inside getCompanyData service "+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return companyrepository.getCompanyData(cmpid);
	}
	
	public int updateCompanyStatus(CompanyDTO companyDTO)
	{
		try {
			logger.info("inside updateCompanyStatus service " + companyDTO, this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return companyrepository.updateCompanyStatus(companyDTO);
	}
	
	public List<CompanyDTO> getCompanyIdName()
	{
		try {
			logger.info("inside getCompanyIdName service "+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return companyrepository.getCompanyIdName();
	}
	
	public List<CompanyDTO> getCompanyIdNameall()
	{
		try {
			logger.info("inside getCompanyIdNameall service "+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return companyrepository.getCompanyIdNameall();
	}

	@Override
	public CompanyDTO getCompanyIdByName(String cmpname) {
		try {
			logger.info("inside getCompanyData service "+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return companyrepository.getCompanyIdByName(cmpname);
	}
}
