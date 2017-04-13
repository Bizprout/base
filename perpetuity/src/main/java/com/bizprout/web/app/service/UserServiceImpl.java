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
import com.bizprout.web.app.dto.UserEditVO;
import com.bizprout.web.app.dto.UserVO;
import com.bizprout.web.app.repository.UserRepositoryImpl;

@Service
public class UserServiceImpl implements UserService<UserDTO> {

	@Autowired
	private BaseRepository<UserDTO> baseRepository;

	@Autowired
	private UserRepositoryImpl userRepository;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public void CreateUser(UserDTO t) {
		try {
			System.out.println("inside CreateUser service " + t);
			logger.info("inside CreateUser service " + t);
			baseRepository.save(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int UpdateUser(UserEditVO usereditVO) {
		
		try {
			System.out.println("inside UpdateUser service " + usereditVO);
			logger.info("inside UpdateUser service " + usereditVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userRepository.UpdateUsers(usereditVO);
	}

	public List<UserDTO> getUsers() {
		List<UserDTO> user = null;
		try {
			logger.info("inside getUsers method ");
			user = userRepository.getusers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public List<String> getUsernameList() {
		
		List<String> userNameList = null;

		try {
			logger.info("inside getUsernameList method ");

			userNameList = (List<String>) (Object) userRepository
					.getListOfProperty(UserDTO.class,"username");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userNameList;
	}

	public UserDTO getUserData(UserVO uservo) {
		UserDTO user = null;
		try {
			logger.info("inside getUserData method...");
			user=userRepository.getUserData(uservo.getUsername());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
}
