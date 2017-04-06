package com.bizprout.web.app.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company_master")
public class CompanyDTO {
	
	@Id
	@Column(name="cmp_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cmpId;
	
	@Column(name="client_id")
	private int clientId;
	
	@Column(name="tally_guid")
	private String tallyGUID;
	
	@Column(name="tally_cmpname")
	private String tallyCmpName;
	
	@Column(name="appl_from_date")
	private Date appFromDate;
	
	@Column(name="upload_timer")
	private Date uploadTimer;
	
	@Column(name="download_timer")
	private Date dnldTimer;
	
	@Column(name="max_retrial")
	private int maxRetrial;
	
	@Column(name="status")
	private String status;
	
	public int getCmpId() {
		return cmpId;
	}
	public void setCmpId(int cmpId) {
		this.cmpId = cmpId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getTallyGUID() {
		return tallyGUID;
	}
	public void setTallyGUID(String tallyGUID) {
		this.tallyGUID = tallyGUID;
	}
	public String getTallyCmpName() {
		return tallyCmpName;
	}
	public void setTallyCmpName(String tallyCmpName) {
		this.tallyCmpName = tallyCmpName;
	}
	public Date getAppFromDate() {
		return appFromDate;
	}
	public void setAppFromDate(Date appFromDate) {
		this.appFromDate = appFromDate;
	}
	public Date getUploadTimer() {
		return uploadTimer;
	}
	public void setUploadTimer(Date uploadTimer) {
		this.uploadTimer = uploadTimer;
	}
	public Date getDnldTimer() {
		return dnldTimer;
	}
	public void setDnldTimer(Date dnldTimer) {
		this.dnldTimer = dnldTimer;
	}
	public int getMaxRetrial() {
		return maxRetrial;
	}
	public void setMaxRetrial(int maxRetrial) {
		this.maxRetrial = maxRetrial;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
