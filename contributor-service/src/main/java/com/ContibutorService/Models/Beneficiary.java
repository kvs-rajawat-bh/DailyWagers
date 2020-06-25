package com.ContibutorService.Models;

public class Beneficiary {
	
	private String name;
	private String contact;
	private String address;
	
	private String poc;
	private String pocContact;
	
	private String deliveryOption;
	private String handler;
	
	public Beneficiary(String name, String contact, String address, String poc, String pocContact, String deliveryOption, String handler) {
		super();
		this.name = name;
		this.contact = contact;
		this.address = address;
		this.poc = poc;
		this.pocContact = pocContact;
		this.deliveryOption = deliveryOption;
		this.handler = handler;
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
	public String getDeliveryOption() {
		return deliveryOption;
	}
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	
	
	

}
