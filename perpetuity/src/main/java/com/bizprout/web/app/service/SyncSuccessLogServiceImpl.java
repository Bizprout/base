package com.bizprout.web.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.service.SyncSuccessLogService;
import com.bizprout.web.app.dto.SyncSuccessLogDTO;
import com.bizprout.web.app.repository.SyncSuccessLogRepositoryImpl;

@Service
@Transactional
public class SyncSuccessLogServiceImpl implements SyncSuccessLogService<SyncSuccessLogDTO>{
	
	@Autowired
	private SyncSuccessLogRepositoryImpl synclogrepo;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public List<SyncSuccessLogDTO> getMastertypeData(int cmpid) {
		List<SyncSuccessLogDTO> res = null;
		try {
			logger.info("inside getMastertypeData ", this.getClass());
			res = synclogrepo.getMastertypeData(cmpid);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}		
		return res;
	}
	
	public List<SyncSuccessLogDTO> getVouchertypeData(int cmpid) {
		List<SyncSuccessLogDTO> res = null;
		try {
			logger.info("inside getVouchertypeData ", this.getClass());
			res = synclogrepo.getVouchertypeData(cmpid);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}		
		return res;
	}

}
