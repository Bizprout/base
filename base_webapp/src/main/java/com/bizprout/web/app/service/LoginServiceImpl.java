package com.bizprout.web.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.service.LoginService;
import com.bizprout.web.app.dto.LoginDTO;
import com.bizprout.web.app.dto.LoginVO;
import com.bizprout.web.app.repository.LoginRepositoryImpl;

@Service
public class LoginServiceImpl implements LoginService<LoginDTO> {
	
	@Autowired
	private LoginRepositoryImpl loginRepository;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Override
	public LoginDTO authenticate(LoginVO loginVO) {
		
		System.out.println("inside authenticate fetch method...");
		logger.info("inside authenticate fetch method...");
		LoginDTO fromDb = this.loginRepository.getLoginUser(loginVO.getUsername(),loginVO.getPassword());
		System.out.println(fromDb);
		return fromDb;
		
	}
	
	

}
