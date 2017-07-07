package com.bizprout.web.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.PpMasterService;
import com.bizprout.web.app.dto.EditPpMasterDTO;
import com.bizprout.web.app.dto.PpMasterDTO;
import com.bizprout.web.app.repository.PpMasterRepositoryImpl;

@Service
@Transactional
public class PpMasterServiceImpl implements PpMasterService<PpMasterDTO> {

	@Autowired
	private BaseRepository<PpMasterDTO> baseRepository;

	@Autowired
	private PpMasterRepositoryImpl PpRepository;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public void CreatePpMaster(PpMasterDTO t) {

		try {
			logger.info("inside CreatePpMaster service " + t, this.getClass());
			baseRepository.save(t);

		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
	}

	public List<String> getPpMastersName(String mastertype, String category, int cmpid, String ppmastername, List<String> child) {

		List<String> ppmasterlist = null;
		try {
			logger.info("inside getPpMastersName service method "+this.getClass());
			ppmasterlist = PpRepository.getPpMasterList(mastertype, category, cmpid, ppmastername, child);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasterlist;
	}
	
	public List<String> getPpMastersChild(String mastertype, int cmpid, String ppmastername) {

		List<String> ppmasterchildlist = null;
		try {
			logger.info("inside getPpMastersChild service method "+this.getClass());
			ppmasterchildlist = PpRepository.getPpMasterChildList(mastertype, cmpid, ppmastername);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasterchildlist;
	}

	public List<String> getPpMastersNameall(String mastertype, String category, int cmpid) {

		List<String> ppmasterlist = null;
		try {
			logger.info("inside getPpMastersNameall service method "+this.getClass());
			ppmasterlist = PpRepository.getPpMasterListall(mastertype, category, cmpid);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasterlist;
	}

	public List<String> getPpMastersNamebyCostCategory(String mastertype, int cmpid, String ppmastername) {

		List<String> ppmasterlist = null;
		try {
			logger.info("inside getPpMastersNamebyCostCategory service method "+this.getClass());
			ppmasterlist = PpRepository.getPpMasterbyCostCategory(mastertype, cmpid, ppmastername);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasterlist;
	}

	public List<String> getPpMastersNameByCompany(String mastertype, int cmpid) {

		List<String> ppmasterlist = null;
		try {
			logger.info("inside getPpMastersNameByCompany service method "+this.getClass());
			ppmasterlist = PpRepository.getPpMasterListByCompany(mastertype, cmpid);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasterlist;
	}

	public List<PpMasterDTO> getPpParentName(String mastertype, String ppmastername, int cmpid) {

		List<PpMasterDTO> ppparentname = null;
		try {
			logger.info("inside getPpParentName service method "+this.getClass());
			ppparentname = PpRepository.getPpParentName(mastertype, ppmastername, cmpid);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppparentname;
	}

	public int UpdatePpMasters(EditPpMasterDTO editppmasterDTO) {

		try {
			logger.info("inside UpdatePpMasters service " + editppmasterDTO, this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return PpRepository.UpdatePpMaster(editppmasterDTO);
	}

	public List<PpMasterDTO> getPpMasterdata(int cmpid, String mastertype) {

		List<PpMasterDTO> ppmasterdata = null;
		try {
			logger.info("inside getPpMasterdata method ", this.getClass());
			ppmasterdata = PpRepository.getPpmasterData(cmpid, mastertype);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasterdata;
	}



}
