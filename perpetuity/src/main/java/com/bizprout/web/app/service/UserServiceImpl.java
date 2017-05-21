package com.bizprout.web.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.common.repository.BaseRepository;
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
	public int CreateUser(UserDTO t) {
		int id=0;
		try {
			
			System.out.println("inside CreateUser service " + t);
			logger.info("inside CreateUser service " + t);
			//t.setUsertype(UserConstant.PP_SUPER_ADMIN);
			id=baseRepository.save(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public int UpdateUser(UserEditVO usereditVO) {

		try {
			System.out.println("inside UpdateUser service " + usereditVO);
			logger.info("inside UpdateUser service " + usereditVO);
		} catch (Exception e) {
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
			logger.error("Exception in getUsers \t" + e.getMessage());
		}
		return user;
	}

	public List<String> getUsernameList() {

		List<String> userNameList = null;

		try {
			logger.info("inside getUsernameList method ");

			userNameList = userRepository.getUsernameList();
					/*(List<String>) (Object) userRepository.getListOfProperty(UserDTO.class, "username");*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userNameList;
	}

	public UserDTO getUserData(UserVO uservo) {
		UserDTO user = null;
		try {
			logger.info("inside getUserData method...");
			user = userRepository.getUserData(uservo.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public UserDTO getUserDataById(int Userid) {
		UserDTO user = null;
		try {
			logger.info("inside getUserData method...");
			user = userRepository.getUserDataById(Userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public int updatePassword(String password, int cmpid) {
		int res = 0;
		try {
			logger.info("inside changerdPassword method...");
			res = userRepository.updatePassword(password, cmpid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
