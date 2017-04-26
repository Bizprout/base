package com.bizprout.web.api.service;

import java.util.List;

import com.bizprout.web.app.dto.CompanyDTO;

public interface CompanyService<T> {

	public CompanyDTO getClientStatus(String companyname);

	public int updateCompany(CompanyDTO t);
	
	public List<CompanyDTO> getCompanyData();

	public int updateCompanyStatus(CompanyDTO t);
}
