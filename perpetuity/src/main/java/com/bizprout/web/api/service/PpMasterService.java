package com.bizprout.web.api.service;

import java.util.List;

import com.bizprout.web.app.dto.EditPpMasterDTO;
import com.bizprout.web.app.dto.PpMasterDTO;

public interface PpMasterService<T> {

	public void CreatePpMaster(PpMasterDTO t);
	
	public List<String> getPpMastersName(String ppmasterlist, String category, int cmpid, String ppmastername, List<String> child);
	
	public List<String> getPpMastersChild(String ppmasterlist, int cmpid, String ppmastername);

	public List<String> getPpMastersNameall(String ppmasterlist, String category, int cmpid);
	
	public List<String> getPpMastersNamebyCostCategory(String ppmasterlist, int cmpid, String ppmastername);
	
	public List<String> getPpMastersNameByCompany(String ppmasterlist, int cmpid);
	
	public List<PpMasterDTO> getPpParentName(String mastertype, String ppmastername, int cmpid);
	
	public int UpdatePpMasters(EditPpMasterDTO t);
	
	public List<PpMasterDTO> getPpMasterdata(int cmpid, String mastertype);

}
