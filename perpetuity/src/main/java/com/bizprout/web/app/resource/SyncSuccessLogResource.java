package com.bizprout.web.app.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizprout.web.api.service.SyncSuccessLogService;
import com.bizprout.web.app.dto.SyncSuccessLogDTO;

@RestController
@RequestMapping("/synclog")
public class SyncSuccessLogResource {
	
	@Autowired
	private SyncSuccessLogService<SyncSuccessLogDTO> syncservice;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@PostMapping(value="/getMastertypeData")
	public List<SyncSuccessLogDTO> getMastertypeData(@RequestBody SyncSuccessLogDTO syncDTO)
	{
		List<SyncSuccessLogDTO> syncsuccessdto = null;
		try {
			logger.debug("Request......getClientStatus......"+this.getClass());
			
			syncsuccessdto=syncservice.getMastertypeData(syncDTO.getCmpid());
		} catch (Exception e) {
			
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return syncsuccessdto;
	}
	
	@PostMapping(value="/getVouchertypeData")
	public List<SyncSuccessLogDTO> getVouchertypeData(@RequestBody SyncSuccessLogDTO syncDTO)
	{
		List<SyncSuccessLogDTO> syncsuccessdto = null;
		try {
			logger.debug("Request......getVouchertypeData......"+this.getClass());
			
			syncsuccessdto=syncservice.getVouchertypeData(syncDTO.getCmpid());
		} catch (Exception e) {
			
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return syncsuccessdto;
	}
	
	@PostMapping(value="/getalldatabycmpid")
	public List<SyncSuccessLogDTO> getAllSyncData(@RequestBody SyncSuccessLogDTO syncDTO)
	{
		List<SyncSuccessLogDTO> syncsuccessdto = null;
		try {
			logger.debug("Request......getAllSyncData......"+this.getClass());
			
			syncsuccessdto=syncservice.getAllSyncData(syncDTO.getCmpid());
		} catch (Exception e) {
			
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return syncsuccessdto;
	}

}
