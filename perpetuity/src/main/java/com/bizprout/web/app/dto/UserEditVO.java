package com.bizprout.web.app.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserEditVO {
	
	@NotBlank(message="Username Cannot be Blank!")
	private String username;
	
	@NotBlank(message="Edit Username Cannot be Blank!")
	private String editusername;
	
	@NotBlank(message="Usertype Cannot be Blank!")
	private String usertype;
	
	@NotBlank(message="Userstatus Cannot be Blank!")
	private String userstatus;
	
	@NotBlank(message="Email ID Cannot be Blank!")
	@Email(message="Email ID is not Valid!")
	private String emailid;
	
	private String mobile;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEditusername() {
		return editusername;
	}
	public void setEditusername(String editusername) {
		this.editusername = editusername;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	

}
