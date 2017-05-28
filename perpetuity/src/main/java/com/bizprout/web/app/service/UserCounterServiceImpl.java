package com.bizprout.web.app.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.UserCounterService;
import com.bizprout.web.app.dto.UserCounterDTO;
import com.bizprout.web.app.repository.UserCounterRepositoryImpl;

@Service
@Transactional
public class UserCounterServiceImpl implements UserCounterService<UserCounterDTO> {


	@Autowired
	private SessionFactory factory;

	@Autowired
	private BaseRepository<UserCounterDTO> baserepository;

	@Autowired
	private UserCounterRepositoryImpl usercounterrepository;

	Logger logger=LoggerFactory.getLogger(this.getClass());

	@Override
	public void insertusercounter(UserCounterDTO usercounterdto) {

		try {
			logger.debug("inside insert user counter method "+this.getClass());
			baserepository.save(usercounterdto);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
	}

	@Override
	public UserCounterDTO getlastlogindatetime(int userid) {
		
		UserCounterDTO usercounter=null;

		try {
			logger.info("Inside getlastlogindatetime method......."+this.getClass());

			usercounter=usercounterrepository.getlastlogindatetime(userid);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		
		return usercounter;
	}



}
