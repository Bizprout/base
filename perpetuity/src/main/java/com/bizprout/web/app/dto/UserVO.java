package com.bizprout.web.app.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

public class UserVO {
	
	@Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Username Should be a Valid Email ID!")
	@Email
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}	

}
