package com.bizprout.web.app.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.UserMappingService;
import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.dto.ScreensDTO;
import com.bizprout.web.app.dto.UserMappingDTO;
import com.bizprout.web.app.repository.UserMappingRepositoryImpl;

@Service
public class UserMappingServiceImpl implements UserMappingService<CompanyDTO>{
	
	@Autowired  
	private SessionFactory factory;
	
	@Autowired
	private UserMappingRepositoryImpl usermappingrepository;
	@Autowired
	private BaseRepository<UserMappingDTO> baseRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<String> getCompanyList() {
		
		List<String> companylist = null;

		try {
			logger.info("inside getcompanylist method "+this.getClass());

			Session session = factory.getCurrentSession();

			companylist=session.createQuery("select tallyCmpName from CompanyDTO where status='Active'").list();

		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return companylist;
		
	}
	
	public List<ScreensDTO> getScreensList() {

		List<ScreensDTO> screenNameList = null;

		try {
			logger.info("inside get Screen Names method "+this.getClass());

			screenNameList = usermappingrepository.getScreenNameList();

		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return screenNameList;
	}
	
	public int updateUserMapping(UserMappingDTO uMapDto)
	{
		int id=0;
		try {
			id=baseRepository.save(uMapDto); 
			logger.info("inside UpdateCompany service " + uMapDto, this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return id;	
	}
	
	public List<UserMappingDTO> getUserMapData() {
		
		List<UserMappingDTO> companylist = null;

		try {
			logger.info("inside get user mapping method "+this.getClass());

			companylist=usermappingrepository.getUserMapData();				

		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return companylist;
		
	}

	public UserMappingDTO getUserMapByCmpUser(int cmpid, int userid) {
		
		UserMappingDTO usermapdto = null;
		try {
			logger.info("inside get user mapping method "+this.getClass());

			usermapdto=usermappingrepository.getUserMapByCmpUser(cmpid, userid);	////baseRepository.getList();			

		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return usermapdto;
		
	}
	
	public int deleteScreensByCmpidUserid(int cmpid, int userid)
	{
		int result=0;
		try {
			logger.info("inside deleteScreensByCmpidUserid "+this.getClass());

			result=usermappingrepository.deleteScreensByCmpidUserid(cmpid, userid);			

		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return result;
	}

	@Override
	public List<UserMappingDTO> getCmpByuserid(int userid) {
		
		List<UserMappingDTO> usermapdto = null;
		try {
			logger.info("inside get user mapping method "+this.getClass());

			usermapdto=usermappingrepository.getCmpByuserid(userid);	////baseRepository.getList();			

		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return usermapdto;
	}

}
