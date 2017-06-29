
package com.bizprout.web.app.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="db_vouchers")
public class DaybookDTO {

	@Column(name="cmp_id")
	private int cmpId;
	@Id
	@Column(name="vch_id")
	private String vchId;
	@Column(name="vch_name")
	private String vchName;
	@Column(name="vch_type")
	private String vchType;
	@Column(name="vch_number")
	private String vchNumber;
	@Column(name="vch_date")
	private Date vchDate;
	@Column(name="party_ledger")
	private String ledgerName;
	@Column(name="vch_amount")
	private Double vchAmount;
	@Column(name="vch_narration")
	private String vchNarration;

	public DaybookDTO() {
		super();

	}

	public DaybookDTO(int cmpId, String vchId, String vchType, String vchNumber,
			Date vchDate, String ledgerName, Double vchAmount) {
		super();
		this.cmpId = cmpId;
		this.vchId = vchId;
		this.vchType = vchType;
		this.vchNumber = vchNumber;
		this.vchDate = vchDate;
		this.ledgerName = ledgerName;
		this.vchAmount = vchAmount;
	}

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
	public String getVchType() {
		return vchType;
	}
	public void setVchType(String vchType) {
		this.vchType = vchType;
	}
	public String getVchNumber() {
		return vchNumber;
	}
	public void setVchNumber(String vchNumber) {
		this.vchNumber = vchNumber;
	}
	public Date getVchDate() {
		return vchDate;
	}
	public void setVchDate(Date vchDate) {
		this.vchDate = vchDate;
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
	public String getVchNarration() {
		return vchNarration;
	}

	public void setVchNarration(String vchNarration) {
		this.vchNarration = vchNarration;
	}	  



}