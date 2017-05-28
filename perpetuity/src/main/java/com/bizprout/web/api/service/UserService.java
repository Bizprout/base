package com.bizprout.web.api.service;

import java.util.List;

import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.dto.UserEditVO;

public interface UserService<T> {
	
	public int CreateUser(UserDTO t);
	
	public int UpdateUser(UserEditVO t);
	
	public List<T> getUsers();
	
	public List<String> getUsernameList();
	
	public UserDTO getUserData(String username);
	
	public UserDTO getUserDataById(int userid);
	
	public int updatePassword(String password, int cmpid);
	
	public int resetPassword(String username, String password);

}
