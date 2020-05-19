package com.ContibutorService.Models;

public class Donor {
	
	private String name;
	private String contact;
	private String city;
	private String email;
	private String beneficiaryName;
	private String beneficiaryContact;
	
	
	
	
	public Donor(String name, String contact, String email, String city) {
		super();
		this.name = name;
		this.contact = contact;
		this.city = city;
		this.email = email;
	}
	public String getName() {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getBeneficiaryContact() {
		return beneficiaryContact;
	}
	public void setBeneficiaryContact(String beneficiaryContact) {
		this.beneficiaryContact = beneficiaryContact;
	}
	
	

}
