package com.bizprout.web.api.service;

import com.bizprout.web.app.dto.LoginVO;
import com.bizprout.web.app.dto.UserDTO;

public interface LoginService<T> {
	
	public UserDTO authenticate(LoginVO loginVO);

}
