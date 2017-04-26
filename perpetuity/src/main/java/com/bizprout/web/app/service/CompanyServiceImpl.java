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

	public CompanyDTO getClientStatus(String companyname) {
		
		CompanyDTO compDTO = null ;
		try {
			logger.info("inside getClientStatus service method ");
			compDTO= companyrepository.getClientStatus(companyname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compDTO;
	}

	public int updateCompany(CompanyDTO companyDTO)
	{
		try {
			logger.info("inside UpdateCompany service " + companyDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companyrepository.updateCompany(companyDTO);
	}
	
	public List<CompanyDTO> getCompanyData()
	{
		try {
			logger.info("inside getCompanyData service ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companyrepository.getCompanyData();
	}
	
	public int updateCompanyStatus(CompanyDTO companyDTO)
	{
		try {
			logger.info("inside updateCompanyStatus service " + companyDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companyrepository.updateCompanyStatus(companyDTO);
	}
}
