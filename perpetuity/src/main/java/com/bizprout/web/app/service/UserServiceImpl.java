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
			logger.info("inside CreateUser service " + t, this.getClass());
			//t.setUsertype(UserConstant.PP_SUPER_ADMIN);
			id=baseRepository.save(t);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return id;
	}

	public int UpdateUser(UserEditVO usereditVO) {

		try {
			logger.info("inside UpdateUser service " + usereditVO, this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return userRepository.UpdateUsers(usereditVO);
	}

	public List<UserDTO> getUsers() {
		List<UserDTO> user = null;
		try {
			logger.info("inside getUsers method "+this.getClass());
			user = userRepository.getusers();
		} catch (Exception e) {
			logger.error("Exception in getUsers \t" + e.getMessage(), this.getClass());
		}
		return user;
	}

	public List<String> getUsernameList() {

		List<String> userNameList = null;

		try {
			logger.info("inside getUsernameList method "+this.getClass());

			userNameList = userRepository.getUsernameList();
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return userNameList;
	}

	public UserDTO getUserData(String username) {
		UserDTO user = null;
		try {
			logger.info("inside getUserData method..."+this.getClass());
			user = userRepository.getUserData(username);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return user;
	}
	
	public UserDTO getUserDataById(int Userid) {
		UserDTO user = null;
		try {
			logger.info("inside getUserData method..."+this.getClass());
			user = userRepository.getUserDataById(Userid);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return user;
	}
	
	public int updatePassword(String password, int cmpid) {
		int res = 0;
		try {
			logger.info("inside changerdPassword method..."+this.getClass());
			res = userRepository.updatePassword(password, cmpid);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return res;
	}
	
	@Override
	public int resetPassword(String username, String password) {
		int res = 0;
		try {
			logger.info("inside resetPassword method..."+this.getClass());
			res = userRepository.resetPassword(username, password);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return res;
	}

}
