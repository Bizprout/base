package com.bizprout.web.app.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.BaseService;
import com.bizprout.web.api.service.UserService;
import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.repository.UserRepositoryImpl;

@Service
public class UserServiceImpl implements UserService<UserDTO> {

	@Autowired
	private BaseRepository<UserDTO> baseRepository;

	@Autowired
	private UserRepositoryImpl userRepository;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public void CreateUser(UserDTO t) {
		System.out.println("inside test service " + t);
		logger.info("inside test service " + t);
		baseRepository.save(t);
	}

	public void UpdateUser(UserDTO t) {
		System.out.println("inside updateservice " + t);
		logger.info("inside Update service " + t);
		baseRepository.update(t);
	}

	public List<UserDTO> getUsers() {

		logger.info("inside getUsers method ");
		List<UserDTO> user = userRepository.getList();
		return user;
	}

	public List<String> getUsernameList() {

		logger.info("inside getUsernameList method ");

		List<String> userNameList = (List<String>) (Object) userRepository
				.getListOfProperty(UserDTO.class,"username");
		return userNameList;
	}
}
