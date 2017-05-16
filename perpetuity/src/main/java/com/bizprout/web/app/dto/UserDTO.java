package com.bizprout.web.app.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name = "users")
public class UserDTO {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userid;
	
	@NotBlank(message="Username Cannot be Blank!")
	@Column(name = "username")
	@Size(min=6, message="Username must be min 6 char!")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@NotBlank(message="Usertype Cannot be Blank!")
	@Column(name = "user_type")
	private String usertype;

	@NotBlank(message="Userstatus Cannot be Blank!")
	@Column(name = "user_status")
	private String userstatus;
	
	@NotBlank(message="Email Cannot be Blank!")
	@Email(message="Email ID is not Valid!")
	@Column(name = "email_id")
	private String emailid;
	
	@Column(name = "mobile_no")
	private String mobile;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
