package com.bizprout.web.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.TallyMappingService;
import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.dto.PpMasterDTO;
import com.bizprout.web.app.dto.TallyMappingDTO;
import com.bizprout.web.app.dto.TallyMastersDTO;
import com.bizprout.web.app.repository.TallyMappingRepositoryImpl;

@Service
public class TallyMappingServiceImpl implements TallyMappingService<TallyMappingDTO> {
	
	@Autowired
	private BaseRepository<TallyMappingDTO> baserepository;
	@Autowired
	private TallyMappingRepositoryImpl tallymappingrepo;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public List<CompanyDTO> getCompanyIdName(int clientid) {
		List<CompanyDTO> comp = null;
		try {
			logger.info("inside getCompanyIdName method ");
			comp = tallymappingrepo.getCompanyIdName(clientid);
		} catch (Exception e) {
			logger.error("Exception in getCompanyIdName method \t" + e.getMessage());
		}
		return comp;
	}
	
	public List<TallyMastersDTO> getTallyMasterNames(String mastertype, int cmpid)
	{
		List<TallyMastersDTO> tallymasters = null;
		try {
			logger.info("inside getTallyMasterNames method ");
			tallymasters = tallymappingrepo.getTallyMasterNames(mastertype, cmpid);
		} catch (Exception e) {
			logger.error("Exception in getTallyMasterNames method \t" + e.getMessage());
		}
		return tallymasters;
	}
	
	public List<PpMasterDTO> getPpMasterNames(String mastertype, int cmpid)
	{
		List<PpMasterDTO> tallymasters = null;
		try {
			logger.info("inside getPpMasterNames method ");
			tallymasters = tallymappingrepo.getPpMasterNames(mastertype, cmpid);
		} catch (Exception e) {
			logger.error("Exception in getPpMasterNames method \t" + e.getMessage());
		}
		return tallymasters;
	}
	
	public void insertTallyMapping(TallyMappingDTO tallymappingdto)
	{
		try {
			logger.info("inside insertTallyMapping method ");
			baserepository.save(tallymappingdto);
		} catch (Exception e) {
			logger.error("Exception in insertTallyMapping method \t" + e.getMessage());
		}
	}
	
	public List<Integer> getPpMastersMapping(int cmpid, int ppid)
	{
		List<Integer> ppmapping=null;
		try {
			logger.info("inside getPpMasterMapping method ");
			ppmapping=tallymappingrepo.getPpMastersMapping(cmpid, ppid);
		} catch (Exception e) {
			logger.error("Exception in getPpMasterMapping method \t" + e.getMessage());
		}
		return ppmapping;
	}

	public int updateTallyMapping(TallyMappingDTO tallymappingdto) {
		
		int result=0;
		try {
			logger.info("inside updateTallyMapping method ");
			result=tallymappingrepo.updateTallyMapping(tallymappingdto);
		} catch (Exception e) {
			logger.error("Exception in updateTallyMapping method \t" + e.getMessage());
		}
		
		return result;
	}
	
	public List<TallyMastersDTO> getTallyPpMappingData(int cmpid, String mastertype)
	{
		List<TallyMastersDTO> tallyppmappingdata=null;
		
		try {
			logger.info("inside getTallyPpMappingData method ");
			tallyppmappingdata = tallymappingrepo.getTallyPpMappingData(cmpid, mastertype);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tallyppmappingdata;
	}
	
	public int savePpMasterMapping(List<TallyMastersDTO> tallymasterdto) {
		
		int result=0;
		try {
			logger.info("inside savePpMasterMapping method ");
			result=tallymappingrepo.savePpMasterMapping(tallymasterdto);
		} catch (Exception e) {
			logger.error("Exception in savePpMasterMapping method \t" + e.getMessage());
		}
		
		return result;
	}
	
	public List<PpMasterDTO> getPpMasterIdNames(String mastertype, int cmpid, String ppmastername)
	{
		List<PpMasterDTO> ppmasteridname=null;
		
		try {
			logger.info("inside getTallyPpMappingData method ");
			ppmasteridname = tallymappingrepo.getPpMasterIdNames(cmpid, mastertype, ppmastername);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ppmasteridname;
	}
	
	public List<TallyMastersDTO> getTallyMasterIdNames(String mastertype, int cmpid, String tallymastername)
	{
		List<TallyMastersDTO> tallymasters = null;
		try {
			logger.info("inside getTallyMasterIdNames method ");
			tallymasters = tallymappingrepo.getTallyMasterIdNames(mastertype, cmpid, tallymastername);
		} catch (Exception e) {
			logger.error("Exception in getTallyMasterIdNames method \t" + e.getMessage());
		}
		return tallymasters;
	}


}
