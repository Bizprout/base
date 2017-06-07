package com.bizprout.web.app.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.Table(name = "TB_Summary")
public class TrialBalanceDTO {
	
	@Id
	private int tbid;
	@Column(name = "cmp_id")
	private int cmpId;
	@Column(name = "name")
	private String name;
	@Column(name="category")
	private String category;
	@Column(name = "base_group_name")
	private String baseGrp;
	@Column(name = "op_dr_amt")
	private Double opDrAmt;
	@Column(name = "op_cr_amt")
	private Double opCrAmt;
	@Column(name = "vch_dr_amt")
	private Double vchDrAmt;
	@Column(name = "vch_cr_amt")
	private Double vchCrAmt;

	public TrialBalanceDTO() {
	}

	public TrialBalanceDTO(String baseGrp, Double opDrAmt, Double opCrAmt,
			Double vchDrAmt, Double vchCrAmt) {
		super();
		this.baseGrp = baseGrp;
		this.opDrAmt = opDrAmt;
		this.opCrAmt = opCrAmt;
		this.vchDrAmt = vchDrAmt;
		this.vchCrAmt = vchCrAmt;
	}

	public int getCmpId() {
		return cmpId;
	}

	public void setCmpId(int cmpId) {
		this.cmpId = cmpId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBaseGrp() {
		return baseGrp;
	}

	public void setBaseGrp(String baseGrp) {
		this.baseGrp = baseGrp;
	}

	public Double getOpDrAmt() {
		return opDrAmt;
	}

	public void setOpDrAmt(Double opDrAmt) {
		this.opDrAmt = opDrAmt;
	}

	public Double getOpCrAmt() {
		return opCrAmt;
	}

	public void setOpCrAmt(Double opCrAmt) {
		this.opCrAmt = opCrAmt;
	}

	public Double getVchDrAmt() {
		return vchDrAmt;
	}

	public void setVchDrAmt(Double vchDrAmt) {
		this.vchDrAmt = vchDrAmt;
	}

	public Double getVchCrAmt() {
		return vchCrAmt;
	}

	public void setVchCrAmt(Double vchCrAmt) {
		this.vchCrAmt = vchCrAmt;
	}

	public int getTbid() {
		return tbid;
	}

	public void setTbid(int tbid) {
		this.tbid = tbid;
	}
	
	
	
}

