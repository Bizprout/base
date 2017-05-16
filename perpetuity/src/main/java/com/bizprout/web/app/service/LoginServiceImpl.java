package com.bizprout.web.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.service.LoginService;
import com.bizprout.web.app.dto.LoginDTO;
import com.bizprout.web.app.dto.LoginVO;
import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.repository.LoginRepositoryImpl;
import com.bizprout.web.app.repository.UserRepositoryImpl;

@Service
public class LoginServiceImpl implements LoginService<UserDTO> {
	
	@Autowired
	private UserRepositoryImpl userrepository;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public UserDTO authenticate(LoginVO loginVO) {
		UserDTO fromDb = null;
		try {
			logger.info("inside authenticate fetch method...");
			fromDb= this.userrepository.getLoginUser(loginVO.getUsername(),loginVO.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fromDb;
	}
	
	

}
