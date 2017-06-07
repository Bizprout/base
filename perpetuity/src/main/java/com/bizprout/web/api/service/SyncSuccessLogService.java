package com.bizprout.web.api.service;

import java.util.List;

import com.bizprout.web.app.dto.SyncSuccessLogDTO;

public interface SyncSuccessLogService<T> {
	
	public List<SyncSuccessLogDTO> getMastertypeData(int cmpid);
	
	public List<SyncSuccessLogDTO> getVouchertypeData(int cmpid);

}
