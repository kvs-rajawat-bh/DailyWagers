package com.ContibutorService.Models;

public class Beneficiary {
	
	private String name;
	private String contact;
	private String address;
	
	
	
	
	public Beneficiary(String name, String contact, String address) {
		super();
		this.name = name;
		this.contact = contact;
		this.address = address;
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
	
	

}
