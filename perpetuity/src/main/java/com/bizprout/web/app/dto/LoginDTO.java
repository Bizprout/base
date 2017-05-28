package com.bizprout.web.app.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "common_login_user_master")
public class LoginDTO {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int user_id;

	@Column(name = "login_username")
	private String UserName;

	@Column(name = "pwd")
	private String Password;

	@Column(name = "super_admin_type")
	private int SuperAdminType;

	@Column(name = "user_type")
	private int UserType;

	@Column(name = "mac_id")
	private String MacId;

	@Column(name = "ip_addr")
	private String IpAddress;

	@Column(name = "active_status")
	private int ActiveStatus;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getLoginUserName() {
		return UserName;
	}

	public void setLoginUserName(String loginUserName) {
		UserName = loginUserName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public int getSuperAdminType() {
		return SuperAdminType;
	}

	public void setSuperAdminType(int superAdminType) {
		SuperAdminType = superAdminType;
	}

	public int getUserType() {
		return UserType;
	}

	public void setUserType(int userType) {
		UserType = userType;
	}

	public String getMacId() {
		return MacId;
	}

	public void setMacId(String macId) {
		MacId = macId;
	}

	public String getIpAddress() {
		return IpAddress;
	}

	public void setIpAddress(String ipAddress) {
		IpAddress = ipAddress;
	}

	public int getActiveStatus() {
		return ActiveStatus;
	}

	public void setActiveStatus(int activeStatus) {
		ActiveStatus = activeStatus;
	}

	@Override
	public String toString() {
		return "LoginDTO [user_id=" + user_id + ", LoginUserName="
				+ UserName + ", SuperAdminType=" + SuperAdminType
				+ ", UserType=" + UserType + ", MacId=" + MacId
				+ ", IpAddress=" + IpAddress + ", ActiveStatus=" + ActiveStatus
				+ "]";
	}


}
