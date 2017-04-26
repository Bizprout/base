package com.bizprout.web.app.dto;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@Type(type="date")
	private Date appFromDate;
	
	@Column(name="upload_timer")
	@Type(type="time")
	private Date uploadTimer;
	
	@Column(name="download_timer")
	@Type(type="time")
	private Date dnldTimer;
	
	@Column(name="max_retrial")
	private int maxRetrial;
	
	@Column(name="status")
	private String status;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="client_id", nullable=false, insertable=false, updatable=false)
	private ClientDTO client;
		
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
	public ClientDTO getClient() {
		return client;
	}
	public void setClient(ClientDTO client) {
		this.client = client;
	}
}
