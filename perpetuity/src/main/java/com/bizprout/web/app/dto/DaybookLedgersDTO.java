package com.bizprout.web.app.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="db_ledgers")
public class DaybookLedgersDTO {

	@Id
	@Column(name="ledid")
	private int vledid;
	@Column(name="cmp_id")
	private int cmpId;
	@Column(name="vch_id")
	private String vchId;
	@Column(name="name")
	private String ledgerName;
	@Column(name="amount")
	private Double vchAmount;
	@Column(name="amount_type")
	private Double vchAmountType;


	public int getCmpId() {
		return cmpId;
	}
	public void setCmpId(int cmpId) {
		this.cmpId = cmpId;
	}
	public String getVchId() {
		return vchId;
	}
	public void setVchId(String vchId) {
		this.vchId = vchId;
	}
	public String getLedgerName() {
		return ledgerName;
	}
	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}
	public Double getVchAmount() {
		return vchAmount;
	}
	public void setVchAmount(Double vchAmount) {
		this.vchAmount = vchAmount;
	}
	public Double getVchAmountType() {
		return vchAmountType;
	}
	public void setVchAmountType(Double vchAmountType) {
		this.vchAmountType = vchAmountType;
	}
	public int getVledid() {
		return vledid;
	}
	public void setVledid(int vledid) {
		this.vledid = vledid;
	}


}
