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
@Table(name="user_mapping")		//,uniqueConstraints=@UniqueConstraint(columnNames={"cmp_id","user_id"}))
public class UserMappingDTO {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_access_id")
	private int id;	
	
	@Column(name="cmp_id")
	@NotNull(message="Company Name cannot be blank")
	private int cmpId;
	
	@Column(name="user_id")
	@NotNull(message="User Name cannot be blank")
	private int userid;
	
	@Column(name="screen_id")
	@NotBlank(message="Select atleast one screen name")
	private String screenId;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
	private UserDTO userdto;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="cmp_id", nullable=false, insertable=false, updatable=false)
	private CompanyDTO compdto;
	
/*	 @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL) // EAGER forces outer join
	 @JoinColumn(name = "screen_id", referencedColumnName="sid", nullable=false, insertable=false, updatable=false)
	 @Where(clause = "in screenId") // "id" is A's PK... modify as needed
	 private ScreensDTO screendto;*/
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCmpId() {
		return cmpId;
	}
	public void setCmpId(int cmpId) {
		this.cmpId = cmpId;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getScreenId() {
		return screenId;
	}
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	public UserDTO getUserdto() {
		return userdto;
	}
	public void setUserdto(UserDTO userdto) {
		this.userdto = userdto;
	}
	public CompanyDTO getCompdto() {
		return compdto;
	}
	public void setCompdto(CompanyDTO compdto) {
		this.compdto = compdto;
	}	

}
