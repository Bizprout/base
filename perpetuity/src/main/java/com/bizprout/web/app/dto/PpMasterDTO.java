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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="pp_masters")
public class PpMasterDTO {
	
	@Id
	@Column(name="master_id_index")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int masteridindex;
	
	@Column(name="cmp_id")
	@NotNull(message="Company ID cannot be Blank!")
	private int cmpid;
	
	@NotBlank(message="Master Type cannot be Blank!")
	@Column(name="master_type")
	private String mastertype;
	
	@NotBlank(message="PP Master Name cannot be Blank!")
	@Column(name="name")
	private String ppmastername;
	
	@NotBlank(message="PP Parent Name cannot be Blank!")
	@Column(name="group_name")
	private String ppparentname;
	
	@Column(name="category")
	private String category;
	
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
	public CompanyDTO getCompanydto() {
		return companydto;
	}
	public void setCompanydto(CompanyDTO companydto) {
		this.companydto = companydto;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	
}
