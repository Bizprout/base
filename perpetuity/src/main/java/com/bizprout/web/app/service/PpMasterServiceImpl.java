package com.bizprout.web.app.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.PpMasterService;
import com.bizprout.web.app.dto.EditPpMasterDTO;
import com.bizprout.web.app.dto.PpMasterDTO;
import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.repository.PpMasterRepositoryImpl;

@Service
public class PpMasterServiceImpl implements PpMasterService<PpMasterDTO> {

	@Autowired
	private BaseRepository<PpMasterDTO> baseRepository;
	
	@Autowired
	private PpMasterRepositoryImpl PpRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void CreatePpMaster(PpMasterDTO t) {
		
		try {
			System.out.println("inside CreatePpMaster service " + t);
			logger.info("inside CreatePpMaster service " + t);
			baseRepository.save(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getPpMastersName(String mastertype, String category, int cmpid, String ppmastername) {
		
		List<String> ppmasterlist = null;
		try {
			logger.info("inside getPpMastersName service method ");
			ppmasterlist = PpRepository.getPpMasterList(mastertype, category, cmpid, ppmastername);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ppmasterlist;
	}
	
	public List<String> getPpMastersNameall(String mastertype, String category, int cmpid) {
		
		List<String> ppmasterlist = null;
		try {
			logger.info("inside getPpMastersName service method ");
			ppmasterlist = PpRepository.getPpMasterListall(mastertype, category, cmpid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ppmasterlist;
	}
	
	public List<String> getPpMastersNamebyCostCategory(String mastertype, int cmpid, String ppmastername) {
		
		List<String> ppmasterlist = null;
		try {
			logger.info("inside getPpMastersName service method ");
			ppmasterlist = PpRepository.getPpMasterbyCostCategory(mastertype, cmpid, ppmastername);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ppmasterlist;
	}
	
	public List<String> getPpMastersNameByCompany(String mastertype, int cmpid) {
		
		List<String> ppmasterlist = null;
		try {
			logger.info("inside getPpMastersNameByCompany service method ");
			ppmasterlist = PpRepository.getPpMasterListByCompany(mastertype, cmpid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ppmasterlist;
	}

	public List<PpMasterDTO> getPpParentName(String mastertype, String ppmastername, int cmpid) {
		
		List<PpMasterDTO> ppparentname = null;
		try {
			logger.info("inside getPpMastersName service method ");
			ppparentname = PpRepository.getPpParentName(mastertype, ppmastername, cmpid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ppparentname;
	}

	public int UpdatePpMasters(EditPpMasterDTO editppmasterDTO) {
		
		try {
			System.out.println("inside UpdatePpMasters service " + editppmasterDTO);
			logger.info("inside UpdatePpMasters service " + editppmasterDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PpRepository.UpdatePpMaster(editppmasterDTO);
	}

	public List<PpMasterDTO> getPpMasterdata(int cmpid) {
		
		List<PpMasterDTO> ppmasterdata = null;
		try {
			logger.info("inside getPpMasterdata method ");
			ppmasterdata = PpRepository.getPpmasterData(cmpid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ppmasterdata;
	}
	
	

}
