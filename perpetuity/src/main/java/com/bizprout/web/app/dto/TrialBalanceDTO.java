package com.bizprout.web.app.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.Table(name="TB_Summary")
public class TrialBalanceDTO
{
  @Column(name="cmp_id")
  private int cmpId;
  @Id
  private String name;
  private String category;
  @Column(name="base_group_name")
  private String baseGrp;
  @Column(name="op_dr_amt")
  private float opDrAmt;
  @Column(name="op_cr_amt")
  private float opCrAmt;
  @Column(name="vch_dr_amt")
  private float vchDrAmt;
  @Column(name="vch_cr_amt")
  private float vchCrAmt;
  
  public TrialBalanceDTO() {}
  
  public int getCmpId()
  {
    return cmpId;
  }
  
  public void setCmpId(int cmpId) { this.cmpId = cmpId; }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) { this.name = name; }
  
  public String getCategory()
  {
    return category;
  }
  
  public void setCategory(String category) {
    this.category = category;
  }
  
  public String getBaseGrp() { return baseGrp; }
  
  public void setBaseGrp(String baseGrp) {
    this.baseGrp = baseGrp;
  }
  
  public float getOpDrAmt() {
    return opDrAmt;
  }
  
  public void setOpDrAmt(float opDrAmt) { this.opDrAmt = opDrAmt; }
  
  public float getOpCrAmt() {
    return opCrAmt;
  }
  
  public void setOpCrAmt(float opCrAmt) { this.opCrAmt = opCrAmt; }
  
  public float getVchDrAmt() {
    return vchDrAmt;
  }
  
  public void setVchDrAmt(float vchDrAmt) { this.vchDrAmt = vchDrAmt; }
  
  public float getVchCrAmt() {
    return vchCrAmt;
  }
  
  public void setVchCrAmt(float vchCrAmt) { this.vchCrAmt = vchCrAmt; }
}