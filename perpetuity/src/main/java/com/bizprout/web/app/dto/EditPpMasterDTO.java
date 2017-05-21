package com.bizprout.web.app.dto;

import org.hibernate.validator.constraints.NotBlank;

public class EditPpMasterDTO {
	
	@NotBlank(message="Master Type cannot be Blank!")
	private String mastertype;
	
	@NotBlank(message="PP Master Name cannot be Blank!")
	private String ppmastername;
	
	@NotBlank(message="Edit PP Master Name cannot be Blank!")
	private String editppmastername;
	
	@NotBlank(message="PP Parent Name cannot be Blank!")
	private String ppparentname;

	private String category;
	
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
	public String getEditppmastername() {
		return editppmastername;
	}
	public void setEditppmastername(String editppmastername) {
		this.editppmastername = editppmastername;
	}
	public String getPpparentname() {
		return ppparentname;
	}
	public void setPpparentname(String ppparentname) {
		this.ppparentname = ppparentname;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}


}
