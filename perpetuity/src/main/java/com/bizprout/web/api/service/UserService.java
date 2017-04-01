package com.bizprout.web.api.service;

import java.util.List;

import com.bizprout.web.app.dto.UserDTO;

public interface UserService<T> {
	
	public void CreateUser(UserDTO t);
	
	public void UpdateUser(UserDTO t);
	
	public List<T> getUsers();
	
	public List<String> getUsernameList();

}
