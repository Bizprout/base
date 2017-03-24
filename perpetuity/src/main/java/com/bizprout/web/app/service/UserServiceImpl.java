package com.bizprout.web.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.BaseService;
import com.bizprout.web.app.dto.UserDTO;

@Service
public class UserServiceImpl implements BaseService<UserDTO>{
	
	@Autowired
	private BaseRepository<UserDTO> baseRepository;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public void testService(UserDTO userDTO) {
		System.out.println("inside test service " + userDTO);
		logger.info("inside test service " + userDTO);
		baseRepository.save(userDTO);		
	}


	public void updateservice(UserDTO userDTO) {
		System.out.println("inside updateservice " + userDTO);
		logger.info("inside Update service " + userDTO);
		baseRepository.update(userDTO);		
	}


	public UserDTO auth(UserDTO t) {		
		return null;
	}

}
