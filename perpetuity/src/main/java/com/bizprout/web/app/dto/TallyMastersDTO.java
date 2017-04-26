package com.bizprout.web.app.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tally_masters")
public class TallyMastersDTO {
	
	@Id
	@Column(name="master_id_index")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int masterIdIndex;
	
	@Column(name="cmp_id")
	private int cmpId;
	
	@Column(name="master_id")
	private int masterId;
	
	@Column(name="master_type")
	private String masterType;
	
	@Column(name="name")
	private String tallyMasterName;

	public int getMasterIdIndex() {
		return masterIdIndex;
	}

	public void setMasterIdIndex(int masterIdIndex) {
		this.masterIdIndex = masterIdIndex;
	}

	public String getMasterType() {
		return masterType;
	}

	public void setMasterType(String masterType) {
		this.masterType = masterType;
	}

	public String getTallyMasterName() {
		return tallyMasterName;
	}

	public void setTallyMasterName(String tallyMasterName) {
		this.tallyMasterName = tallyMasterName;
	}

	public int getMasterId() {
		return masterId;
	}

	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}

	public int getCmpId() {
		return cmpId;
	}

	public void setCmpId(int cmpId) {
		this.cmpId = cmpId;
	}
	
	
}
