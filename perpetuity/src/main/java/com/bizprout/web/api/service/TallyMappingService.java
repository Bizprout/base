package com.bizprout.web.api.service;

import java.util.List;

import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.dto.PpMasterDTO;
import com.bizprout.web.app.dto.TallyMappingDTO;
import com.bizprout.web.app.dto.TallyMastersDTO;

public interface TallyMappingService<T> {
	
	public List<CompanyDTO> getCompanyIdName(int clientid);
	
	public List<TallyMastersDTO> getTallyMasterNames(String mastertype, int cmpid);
	
	public List<PpMasterDTO> getPpMasterNames(String mastertype, int cmpid);
	
	public void insertTallyMapping(TallyMappingDTO tallymappingdto);
	
	public int updateTallyMapping(TallyMappingDTO tallymappingdto);
	
	public List<Integer> getPpMastersMapping(int cmpid, int ppid);
	
	public List<TallyMappingDTO> getTallyPpMappingData(int cmpid);
}
