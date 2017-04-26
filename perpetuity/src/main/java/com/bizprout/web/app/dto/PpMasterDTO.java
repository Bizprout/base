package com.bizprout.web.app.dto;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="pp_masters")
public class PpMasterDTO {
	
	@Id
	@Column(name="master_id_index")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int masteridindex;
	
	@Column(name="cmp_id")
	private int cmpid;
	
	@Column(name="master_type")
	private String mastertype;
	
	@Column(name="name")
	private String ppmastername;
	
	@Column(name="group_name")
	private String ppparentname;
	
	@Column(name="cost_category")
	private String costcategory;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="cmp_id", nullable=false, insertable=false, updatable=false)
	private CompanyDTO companydto;
	
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
	public String getCostcategory() {
		return costcategory;
	}
	public void setCostcategory(String costcategory) {
		this.costcategory = costcategory;
	}
	public CompanyDTO getCompanydto() {
		return companydto;
	}
	public void setCompanydto(CompanyDTO companydto) {
		this.companydto = companydto;
	}

	
}
