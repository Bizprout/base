package com.bizprout.web.app.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
@Table(name="user_counter")
public class UserCounterDTO {
	
	@Id
	@Column(name="counter_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int counterid;
	
	@Column(name="user_id")
	@NotNull
	private int userid;
	
	@Column(name="login_date")
	@Type(type="timestamp")
	@NotNull
	private Date logindatetime;
	
	@Column(name="logout_date")
	@Type(type="timestamp")
	private Date logoutdatetime;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
	private UserDTO user;

	public int getCounterid() {
		return counterid;
	}

	public void setCounterid(int counterid) {
		this.counterid = counterid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Date getLogindatetime() {
		return logindatetime;
	}

	public void setLogindatetime(Date logindatetime) {
		this.logindatetime = logindatetime;
	}

	public Date getLogoutdatetime() {
		return logoutdatetime;
	}

	public void setLogoutdatetime(Date logoutdatetime) {
		this.logoutdatetime = logoutdatetime;
	}
	
}
