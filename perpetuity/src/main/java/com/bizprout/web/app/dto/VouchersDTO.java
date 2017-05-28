package com.bizprout.web.app.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@Table(name="vouchers")
public class VouchersDTO
{
  @Column(name="cmp_id")
  private int cmpId;
  @Id
  @Column(name="vch_id")
  private String vch_id;
  @Column(name="vch_date")
  @Type(type="date")
  private Date vchDate;
  @Column(name="vch_type_id")
  private int vchTypeId;
  @Column(name="vch_number")
  private String vchNumber;
  @Column(name="party_ledger")
  private String partyLedger;
  @Column(name="is_optional")
  private String isOptional;
  @Column(name="is_cancelled")
  private String isCancelled;
  @Column(name="is_deleted")
  private String isDeleted;
  @Column(name="authorized_by")
  private String authBy;
  @Column(name="vch_amount")
  private float vchAmt;
  @Column(name="vch_narration")
  private String vchNarration;
  @Column(name="sync_status")
  private int syncStatus;
  
  public VouchersDTO() {}
  
/*  @ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
  @JoinColumn(name="vch_type_id", referencedColumnName="master_id",nullable=false,insertable=false,updatable=false)
  private TallyMastersDTO tmdto;
  
  public TallyMastersDTO getTmdto() {
	return tmdto;
}

public void setTmdto(TallyMastersDTO tmdto) {
	this.tmdto = tmdto;
}*/


public void setVchAmt(float vchAmt) {
	this.vchAmt = vchAmt;
}

public int getCmpId()
  {
    return cmpId;
  }
  
  public void setCmpId(int cmpId) { this.cmpId = cmpId; }
  
  public String getVch_id() {
    return vch_id;
  }
  
  public void setVch_id(String vch_id) { this.vch_id = vch_id; }
  
  public Date getVchDate() {
    return vchDate;
  }
  
  public void setVchDate(Date vchDate) { this.vchDate = vchDate; }
  
  public int getVchTypeId() {
    return vchTypeId;
  }
  
  public void setVchTypeId(int vchTypeId) { this.vchTypeId = vchTypeId; }
  
  public String getVchNumber() {
    return vchNumber;
  }
  
  public void setVchNumber(String vchNumber) { this.vchNumber = vchNumber; }
  
  public String getPartyLedger() {
    return partyLedger;
  }
  
  public void setPartyLedger(String partyLedger) { this.partyLedger = partyLedger; }
  
  public String getIsOptional() {
    return isOptional;
  }
  
  public void setIsOptional(String isOptional) { this.isOptional = isOptional; }
  
  public String getIsCancelled() {
    return isCancelled;
  }
  
  public void setIsCancelled(String isCancelled) { this.isCancelled = isCancelled; }
  
  public String getIsDeleted() {
    return isDeleted;
  }
  
  public void setIsDeleted(String isDeleted) { this.isDeleted = isDeleted; }
  
  public String getAuthBy() {
    return authBy;
  }
  
  public void setAuthBy(String authBy) { this.authBy = authBy; }
  
  public Float getVchAmt() {
    return vchAmt;
  }
  
  public void setVchAmt(Float vchAmt) { this.vchAmt = vchAmt; }
  
  public String getVchNarration() {
    return vchNarration;
  }
  
  public void setVchNarration(String vchNarration) { this.vchNarration = vchNarration; }
  
  public int getSyncStatus() {
    return syncStatus;
  }
  
  public void setSyncStatus(int syncStatus) { this.syncStatus = syncStatus; }
}
