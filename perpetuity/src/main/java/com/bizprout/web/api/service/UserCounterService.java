package com.bizprout.web.api.service;

import java.util.List;

import com.bizprout.web.app.dto.UserCounterDTO;

public interface UserCounterService<T> {

	public void insertusercounter(UserCounterDTO usercounterdto);
	
	public UserCounterDTO getlastlogindatetime(int userid);
}
