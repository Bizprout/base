package com.bizprout.web.api.service;

import com.bizprout.web.app.dto.LoginVO;

public interface LoginService<T> {
	
	public T authenticate(LoginVO loginVO);

}
