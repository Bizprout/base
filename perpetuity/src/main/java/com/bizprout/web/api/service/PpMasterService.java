package com.bizprout.web.api.service;

import java.util.List;

import com.bizprout.web.app.dto.EditPpMasterDTO;
import com.bizprout.web.app.dto.PpMasterDTO;

public interface PpMasterService<T> {

	public void CreatePpMaster(PpMasterDTO t);
	
	public List<String> getPpMastersName(String ppmasterlist);
	
	public List<String> getPpParentName(String mastertype, String ppmastername);
	
	public int UpdatePpMasters(EditPpMasterDTO t);

}
