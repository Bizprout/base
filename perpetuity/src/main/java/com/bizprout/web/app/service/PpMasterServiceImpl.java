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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String> getPpMastersName(String mastertype) {
		
		List<String> ppmasterlist = null;
		try {
			logger.info("inside getPpMastersName service method ");
			ppmasterlist = PpRepository.getPpMasterList(mastertype);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmasterlist;
	}

	public List<String> getPpParentName(String mastertype, String ppmastername) {
		
		List<String> ppparentname = null;
		try {
			logger.info("inside getPpMastersName service method ");
			ppparentname = PpRepository.getPpParentName(mastertype, ppmastername);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppparentname;
	}

	public int UpdatePpMasters(EditPpMasterDTO editppmasterDTO) {
		
		try {
			System.out.println("inside UpdatePpMasters service " + editppmasterDTO);
			logger.info("inside UpdatePpMasters service " + editppmasterDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PpRepository.UpdatePpMaster(editppmasterDTO);
	}
	
	

}
