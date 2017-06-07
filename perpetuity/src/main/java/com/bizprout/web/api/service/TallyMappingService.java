package com.bizprout.web.api.service;

import java.util.List;

import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.dto.PpMasterDTO;
import com.bizprout.web.app.dto.TallyMappingDTO;
import com.bizprout.web.app.dto.TallyMastersDTO;

public interface TallyMappingService<T> {
	
	public List<CompanyDTO> getCompanyIdName(int clientid);
	
	public List<TallyMastersDTO> getTallyMasterNames(String mastertype, int cmpid, String category);
	
	public List<PpMasterDTO> getPpMasterNames(String mastertype, int cmpid);
	
	public List<PpMasterDTO> getPpMasterNamesByCategory(String mastertype, int cmpid, String category);
	
	public void insertTallyMapping(TallyMappingDTO tallymappingdto);
	
	public int updateTallyMapping(TallyMappingDTO tallymappingdto);
	
	public List<Integer> getPpMastersMapping(int cmpid, int ppid, String category);
	
	public int deletePpidCmpid(int cmpid, int ppid);
	
	public List<TallyMastersDTO> getTallyPpMappingData(int cmpid, String mastertype, String category);
	
	public List<TallyMastersDTO> getTallyPpMappingDataMastertype(int cmpid, String mastertype);
	
	public List<TallyMastersDTO> getTallyPpMappingDataWithoutCategory(int cmpid, String mastertype);
	
	public int savePpMasterMapping(List<TallyMastersDTO> tallymasterdto);

	public List<PpMasterDTO> getPpMasterIdNames(String mastertype, int cmpid,
			String ppmastername);

	public List<TallyMastersDTO> getTallyMasterIdNames(String masterType,
			int cmpId, String tallyMasterName);
}
