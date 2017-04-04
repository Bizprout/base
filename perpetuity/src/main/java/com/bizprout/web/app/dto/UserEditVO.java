package com.bizprout.web.app.dto;

public class UserEditVO {
	
	private String Username;
	private String EditUsername;
	private int Usertype;
	private int Userstatus;
	
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getEditUsername() {
		return EditUsername;
	}
	public void setEditUsername(String editUsername) {
		EditUsername = editUsername;
	}
	public int getUsertype() {
		return Usertype;
	}
	public void setUsertype(int usertype) {
		Usertype = usertype;
	}
	public int getUserstatus() {
		return Userstatus;
	}
	public void setUserstatus(int userstatus) {
		Userstatus = userstatus;
	}
	
	

}
