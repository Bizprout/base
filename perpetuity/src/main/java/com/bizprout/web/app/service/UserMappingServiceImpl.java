package com.bizprout.web.app.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.UserMappingService;
import com.bizprout.web.app.dto.ClientDTO;
import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.repository.UserMappingRepositoryImpl;

@Service
public class UserMappingServiceImpl implements UserMappingService<CompanyDTO>{
	
	@Autowired  
	private SessionFactory factory;
	
	@Autowired
	private UserMappingRepositoryImpl usermappingrepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<String> getCompanyList() {
		
		List<String> companylist = null;

		try {
			logger.info("inside getcompanylist method ");

			Session session = factory.getCurrentSession();

			companylist=session.createQuery("select tallyCmpName from CompanyDTO where status='Active'").list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return companylist;
		
	}

}
