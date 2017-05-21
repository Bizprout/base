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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.bind.annotation.MatrixVariable;


@Entity
@Table(name="company_master")
public class CompanyDTO {
	
	@Id
	@Column(name="cmp_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cmpId;
	
	@Column(name="client_id", nullable=true)
	private Integer clientId;
	
	@Column(name="tally_guid")
	private String tallyGUID;
	
	@Column(name="tally_cmpname")
	private String tallyCmpName;
	
	@NotNull(message="Sync date cannot be Blank!")
	@Column(name="appl_from_date")
	@Type(type="date")
	private Date appFromDate;
	
	@NotNull(message="Upload Timer cannot be Blank!")
	@Column(name="upload_timer")
	@Type(type="time")
	private Date uploadTimer;
	
	@NotNull(message="Download Timer cannot be Blank!")
	@Column(name="download_timer")
	@Type(type="time")
	private Date dnldTimer;
	
	@Column(name="book_from")
	@Type(type="date")
	private Date bookfrom;
	
	@Min(value=1, message="Retrials value cannot be 0!")
	@Column(name="max_retrial", nullable=true)
	private Integer maxRetrial;
	
	@NotBlank(message="Company Status cannot be Blank!")
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
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
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
	public Integer getMaxRetrial() {
		return maxRetrial;
	}
	public void setMaxRetrial(Integer maxRetrial) {
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
	public Date getBookfrom() {
		return bookfrom;
	}
	public void setBookfrom(Date bookfrom) {
		this.bookfrom = bookfrom;
	}
}
