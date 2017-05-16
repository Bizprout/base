package com.bizprout.web.app.dto;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="clients")
public class ClientDTO {
	
	@Id
	@Column(name="client_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clientId;
	
	@NotBlank(message="Client Name cannot be Blank!")
	@Column(name="client_name")
	private String clientName;
	
	@Column(name="contact_person")
	private String contactPerson;
	
	@NotEmpty(message="Email cannot be Blank!")
	@Email(message="Email address is not valid!")
	@Column(name="email")
	private String contactEmail;
	
	@Column(name="phone_no")
	private String contactTelPhone;
	
	@NotBlank(message="Client Status cannot be Blank!")
	@Column(name="status")
	private String status;
	
	@JsonIgnore
	@OneToMany(mappedBy="client")
	private Set<CompanyDTO> companydto;
	
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
	public String getContactTelPhone() {
		return contactTelPhone;
	}
	public void setContactTelPhone(String contactTelPhone) {
		this.contactTelPhone = contactTelPhone;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
