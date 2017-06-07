package com.bizprout.web.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.TallyMappingService;
import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.dto.PpMasterDTO;
import com.bizprout.web.app.dto.TallyMappingDTO;
import com.bizprout.web.app.dto.TallyMastersDTO;
import com.bizprout.web.app.repository.TallyMappingRepositoryImpl;

@Service
@Transactional
public class TallyMappingServiceImpl implements TallyMappingService<TallyMappingDTO> {
	
	@Autowired
	private BaseRepository<TallyMappingDTO> baserepository;
	@Autowired
	private TallyMappingRepositoryImpl tallymappingrepo;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public List<CompanyDTO> getCompanyIdName(int clientid) {
		List<CompanyDTO> comp = null;
		try {
			logger.info("inside getCompanyIdName method "+this.getClass());
			comp = tallymappingrepo.getCompanyIdName(clientid);
		} catch (Exception e) {
			logger.error("Exception in getCompanyIdName method \t" + e.getMessage(), this.getClass());
		}
		return comp;
	}
	
	public List<TallyMastersDTO> getTallyMasterNames(String mastertype, int cmpid, String category)
	{
		List<TallyMastersDTO> tallymasters = null;
		try {
			logger.info("inside getTallyMasterNames method "+this.getClass());
			tallymasters = tallymappingrepo.getTallyMasterNames(mastertype, cmpid, category);
		} catch (Exception e) {
			logger.error("Exception in getTallyMasterNames method \t" + e.getMessage(), this.getClass());
		}
		return tallymasters;
	}
	
	public List<PpMasterDTO> getPpMasterNames(String mastertype, int cmpid)
	{
		List<PpMasterDTO> tallymasters = null;
		try {
			logger.info("inside getPpMasterNames method "+this.getClass());
			tallymasters = tallymappingrepo.getPpMasterNames(mastertype, cmpid);
		} catch (Exception e) {
			logger.error("Exception in getPpMasterNames method \t" + e.getMessage(), this.getClass());
		}
		return tallymasters;
	}
	
	public List<PpMasterDTO> getPpMasterNamesByCategory(String mastertype, int cmpid, String category)
	{
		List<PpMasterDTO> tallymasters = null;
		try {
			logger.info("inside getPpMasterNamesByCategory method "+this.getClass());
			tallymasters = tallymappingrepo.getPpMasterNamesByCategory(mastertype, cmpid, category);
		} catch (Exception e) {
			logger.error("Exception in getPpMasterNamesByCategory method \t" + e.getMessage(), this.getClass());
		}
		return tallymasters;
	}
	
	public void insertTallyMapping(TallyMappingDTO tallymappingdto)
	{
		try {
			logger.info("inside insertTallyMapping method "+this.getClass());
			baserepository.save(tallymappingdto);
		} catch (Exception e) {
			logger.error("Exception in insertTallyMapping method \t" + e.getMessage(), this.getClass());
		}
	}
	
	public List<Integer> getPpMastersMapping(int cmpid, int ppid, String category)
	{
		List<Integer> ppmapping=null;
		try {
			logger.info("inside getPpMasterMapping method "+this.getClass());
			ppmapping=tallymappingrepo.getPpMastersMapping(cmpid, ppid, category);
		} catch (Exception e) {
			logger.error("Exception in getPpMasterMapping method \t" + e.getMessage(), this.getClass());
		}
		return ppmapping;
	}
	
	public int deletePpidCmpid(int cmpid, int ppid)
	{
		int res=0;
		try {
			logger.info("inside deletePpidCmpid method "+this.getClass());
			res=tallymappingrepo.deletePpidCmpid(cmpid, ppid);
		} catch (Exception e) {
			logger.error("Exception in deletePpidCmpid method \t" + e.getMessage(), this.getClass());
		}
		return res;
	}

	public int updateTallyMapping(TallyMappingDTO tallymappingdto) {
		
		int result=0;
		try {
			logger.info("inside updateTallyMapping method "+this.getClass());
			result=tallymappingrepo.updateTallyMapping(tallymappingdto);
		} catch (Exception e) {
			logger.error("Exception in updateTallyMapping method \t" + e.getMessage(), this.getClass());
		}
		
		return result;
	}
	
	public List<TallyMastersDTO> getTallyPpMappingData(int cmpid, String mastertype, String category)
	{
		List<TallyMastersDTO> tallyppmappingdata=null;
		
		try {
			logger.info("inside getTallyPpMappingData method "+this.getClass());
			tallyppmappingdata = tallymappingrepo.getTallyPpMappingData(cmpid, mastertype, category);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return tallyppmappingdata;
	}
	
	public List<TallyMastersDTO> getTallyPpMappingDataMastertype(int cmpid, String mastertype)
	{
		List<TallyMastersDTO> tallyppmappingdata=null;
		
		try {
			logger.info("inside getTallyPpMappingData method "+this.getClass());
			tallyppmappingdata = tallymappingrepo.getTallyPpMappingDataMastertype(cmpid, mastertype);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return tallyppmappingdata;
	}
	
	public List<TallyMastersDTO> getTallyPpMappingDataWithoutCategory(int cmpid, String mastertype)
	{
		List<TallyMastersDTO> tallyppmappingdata=null;
		
		try {
			logger.info("inside getTallyPpMappingData method "+this.getClass());
			tallyppmappingdata = tallymappingrepo.getTallyPpMappingDataWithoutCategory(cmpid, mastertype);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return tallyppmappingdata;
	}
	
	public int savePpMasterMapping(List<TallyMastersDTO> tallymasterdto) {
		
		int result=0;
		try {
			logger.info("inside savePpMasterMapping method "+this.getClass());
			result=tallymappingrepo.savePpMasterMapping(tallymasterdto);
		} catch (Exception e) {
			logger.error("Exception in savePpMasterMapping method \t" + e.getMessage(), this.getClass());
		}
		
		return result;
	}
	
	public List<PpMasterDTO> getPpMasterIdNames(String mastertype, int cmpid, String ppmastername)
	{
		List<PpMasterDTO> ppmasteridname=null;
		
		try {
			logger.info("inside getPpMasterIdNames method "+this.getClass());
			ppmasteridname = tallymappingrepo.getPpMasterIdNames(cmpid, mastertype, ppmastername);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasteridname;
	}
	
	public List<TallyMastersDTO> getTallyMasterIdNames(String mastertype, int cmpid, String tallymastername)
	{
		List<TallyMastersDTO> tallymasters = null;
		try {
			logger.info("inside getTallyMasterIdNames method "+this.getClass());
			tallymasters = tallymappingrepo.getTallyMasterIdNames(mastertype, cmpid, tallymastername);
		} catch (Exception e) {
			logger.error("Exception in getTallyMasterIdNames method \t" + e.getMessage(), this.getClass());
		}
		return tallymasters;
	}


}
