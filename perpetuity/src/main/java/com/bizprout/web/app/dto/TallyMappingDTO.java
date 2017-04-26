package com.bizprout.web.app.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tally_master_mapping")
public class TallyMappingDTO {
	
	@Id
	@Column(name="mapping_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int mappingId;
	
	@Column(name="cmp_id")
	private int cmpId;
	
	@Column(name="pp_id")
	private int ppId;
	
	@Column(name="tally_mster_id")
	private int tallyMasterId;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="cmp_id", nullable=false, insertable=false, updatable=false)
	private CompanyDTO cmpdto;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="pp_id", nullable=false, insertable=false, updatable=false)
	private PpMasterDTO ppmasterdto;

	public int getMappingId() {
		return mappingId;
	}

	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}

	public int getCmpId() {
		return cmpId;
	}

	public void setCmpId(int cmpId) {
		this.cmpId = cmpId;
	}

	public int getPpId() {
		return ppId;
	}

	public void setPpId(int ppId) {
		this.ppId = ppId;
	}

	public int getTallyMasterId() {
		return tallyMasterId;
	}

	public void setTallyMasterId(int tallyMasterId) {
		this.tallyMasterId = tallyMasterId;
	}

	public CompanyDTO getCmpdto() {
		return cmpdto;
	}

	public void setCmpdto(CompanyDTO cmpdto) {
		this.cmpdto = cmpdto;
	}

	public PpMasterDTO getPpmasterdto() {
		return ppmasterdto;
	}

	public void setPpmasterdto(PpMasterDTO ppmasterdto) {
		this.ppmasterdto = ppmasterdto;
	}	
	

}
