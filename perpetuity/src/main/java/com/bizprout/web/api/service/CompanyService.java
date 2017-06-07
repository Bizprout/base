package com.bizprout.web.api.service;

import java.util.List;

import com.bizprout.web.app.dto.CompanyDTO;

public interface CompanyService<T> {

	public CompanyDTO getClientStatus(int cmpid);

	public int updateCompany(CompanyDTO t);
	
	public List<CompanyDTO> getCompanyData(int cmpid);

	public int updateCompanyStatus(CompanyDTO t);
	
	public List<CompanyDTO> getCompanyIdName();
	
	public List<CompanyDTO> getCompanyIdNameall();
	
	public List<CompanyDTO> getallCompanyActive();
	
	public List<CompanyDTO> getCompanyDataall();
	
	public CompanyDTO getCompanyIdByName(String cmpname);
}
