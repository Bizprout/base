package com.bizprout.web.api.service;

import java.util.Date;

import com.bizprout.web.app.dto.UserCounterDTO;

public interface UserCounterService<T> {

	public void insertusercounter(UserCounterDTO usercounterdto);
	
	public UserCounterDTO getlastlogindatetime(int userid);

	public int updateLogoutTime(int userid, Date logindatetime, Date logoutdatetime);
}
