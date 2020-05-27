package com.ContibutorService.Models;

public class Beneficiary {
	
	private String name;
	private String contact;
	private String address;
	
	private String poc;
	private String pocContact;
	
	public Beneficiary(String name, String contact, String address, String poc, String pocContact) {
		super();
		this.name = name;
		this.contact = contact;
		this.address = address;
		this.poc = poc;
		this.pocContact = pocContact;
	}
	public String getName() {
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPoc() {
		return poc;
	}
	public void setPoc(String poc) {
		this.poc = poc;
	}
	public String getPocContact() {
		return pocContact;
	}
	public void setPocContact(String pocContact) {
		this.pocContact = pocContact;
	}
	
	

}
