package com.bizprout.web.app.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.UserCounterService;
import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.dto.UserCounterDTO;
import com.bizprout.web.app.repository.UserCounterRepositoryImpl;

@Service
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
			e.printStackTrace();
		}
	}

	@Override
	public List<UserCounterDTO> getlastlogindatetime(int userid) {
		
		List<UserCounterDTO> usercounter=null;

		try {
			logger.info("Inside getlastlogindatetime method.......");

			usercounter=usercounterrepository.getlastlogindatetime(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return usercounter;
	}



}
