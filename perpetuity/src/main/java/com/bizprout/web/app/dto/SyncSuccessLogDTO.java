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

@Entity
@Table(name="z_syncerror_log")
public class SyncSuccessLogDTO {
	
	@Id
	@Column(name="sync_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int syncid;
	
	@Column(name="cmp_id")
	private int cmpid;
	
	@Column(name="date")
	private Date successdate;
	
	@Column(name="sync_type")
	private String synctype;
	
	@Column(name="description")
	private String descript;

	public int getSyncid() {
		return syncid;
	}
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="cmp_id", nullable=false, insertable=false, updatable=false)
	private CompanyDTO compdto;

	public void setSyncid(int syncid) {
		this.syncid = syncid;
	}

	public int getCmpid() {
		return cmpid;
	}

	public void setCmpid(int cmpid) {
		this.cmpid = cmpid;
	}

	public String getSynctype() {
		return synctype;
	}

	public void setSynctype(String synctype) {
		this.synctype = synctype;
	}

	public Date getSuccessdate() {
		return successdate;
	}

	public void setSuccessdate(Date successdate) {
		this.successdate = successdate;
	}

	public CompanyDTO getCompdto() {
		return compdto;
	}

	public void setCompdto(CompanyDTO compdto) {
		this.compdto = compdto;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

}
