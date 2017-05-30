package com.bizprout.web.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.service.LoginService;
import com.bizprout.web.app.dto.LoginVO;
import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.repository.UserRepositoryImpl;

@Service
@Transactional
public class LoginServiceImpl implements LoginService<UserDTO> {

	@Autowired
	private UserRepositoryImpl userRepository;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public UserDTO authenticate(LoginVO loginVO) {
		UserDTO fromDb = null;
		try {
			logger.info("inside authenticate fetch method...");
			fromDb = this.userRepository.getLoginUser(loginVO.getUsername(),
					loginVO.getPassword());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return fromDb;
	}

	public UserDTO findByUsername(String userName) {
		
		logger.info("inside findByUsername method...");
		
		try {
			UserDTO dto = userRepository.getUserData(userName);
			return dto;
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return null;

	}

}
