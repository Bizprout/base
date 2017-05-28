package com.bizprout.web.app.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pp_screens")
public class ScreensDTO {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sid")
	private int sid;
	
	@Column(name="screen_name")
	private String screenName;
	
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
//	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
//	@JoinColumn(name="userScreenId", nullable=false, insertable=false, updatable=false)
//	private UserScreenAccess screenAccess;
//
//
//	public UserScreenAccess getScreenAccess() {
//		return screenAccess;
//	}
//	
//	public void setScreenAccess(UserScreenAccess screenAccess) {
//		this.screenAccess = screenAccess;
//	}
	

}
