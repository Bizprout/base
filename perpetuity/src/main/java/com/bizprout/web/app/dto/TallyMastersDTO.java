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
@Table(name="tally_masters")
public class TallyMastersDTO {
	
	@Id
	@Column(name="master_id_index")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int masterIdIndex;
	
	@NotNull(message="Company cannot be Blank!")
	@Column(name="cmp_id")
	private int cmpId;
	
	@Column(name="master_id")
	private int masterId;
	
	@NotBlank(message="Master Type cannot be Blank!")
	@Column(name="master_type")
	private String masterType;
	
	@NotBlank(message="Tally Master Name cannot be Blank!")
	@Column(name="name")
	private String tallyMasterName;
	
	@Column(name="category")
	private String category;
	
	@NotNull(message="PP Master Name cannot be Blank!")
	@Column(name="pp_id")
	private Integer ppid;
	
	@Column(name="base_group_name")
	private String basegroupname;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="pp_id", nullable=false, insertable=false, updatable=false)
	private PpMasterDTO ppmasterdto;
	
/*	@OneToMany(fetch = FetchType.EAGER,mappedBy = "tmdto")
	 private List<VouchersDTO> vouchersdto;*/

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

	public Integer getPpid() {
		return ppid;
	}

	public void setPpid(Integer ppid) {
		this.ppid = ppid;
	}

	public PpMasterDTO getPpmasterdto() {
		return ppmasterdto;
	}

	public void setPpmasterdto(PpMasterDTO ppmasterdto) {
		this.ppmasterdto = ppmasterdto;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBasegroupname() {
		return basegroupname;
	}

	public void setBasegroupname(String basegroupname) {
		this.basegroupname = basegroupname;
	}

/*	public List<VouchersDTO> getVouchersdto() {
		return vouchersdto;
	}

	public void setVouchersdto(List<VouchersDTO> vouchersdto) {
		this.vouchersdto = vouchersdto;
	}*/
	
	
}
