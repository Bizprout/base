package com.bizprout.web.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.dto.UserEditVO;
import com.bizprout.web.app.dto.UserVO;

public interface UserService<T> {
	
	public int CreateUser(UserDTO t);
	
	public int UpdateUser(UserEditVO t);
	
	public List<T> getUsers();
	
	public List<String> getUsernameList();
	
	public UserDTO getUserData(UserVO t);
	
	public UserDTO getUserDataById(int userid);
	
	public int updatePassword(String password, int cmpid);

}
