package com.bizprout.web.app.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pp_masters")
public class PpMasterDTO {
	
	@Id
	@Column(name="master_id_index")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int masteridindex;
	
	@Column(name="cmp_id")
	private int cmpid;
	
	@Column(name="master_type")
	private String mastertype;
	
	@Column(name="name")
	private String ppmastername;
	
	@Column(name="group_name")
	private String ppparentname;
	
	public int getMasteridindex() {
		return masteridindex;
	}
	public void setMasteridindex(int masteridindex) {
		this.masteridindex = masteridindex;
	}
	public int getCmpid() {
		return cmpid;
	}
	public void setCmpid(int cmpid) {
		this.cmpid = cmpid;
	}
	public String getMastertype() {
		return mastertype;
	}
	public void setMastertype(String mastertype) {
		this.mastertype = mastertype;
	}
	public String getPpmastername() {
		return ppmastername;
	}
	public void setPpmastername(String ppmastername) {
		this.ppmastername = ppmastername;
	}
	public String getPpparentname() {
		return ppparentname;
	}
	public void setPpparentname(String ppparentname) {
		this.ppparentname = ppparentname;
	}
	
}
