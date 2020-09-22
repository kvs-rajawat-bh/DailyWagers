package com.ContibutorService.Models;

public class Insurance {
	
	private String name;
	private String contact;
	private String city;
	private String email;
	
	
	
	public Insurance(String name, String contact, String email, String city) {
		super();
		this.name = name;
		this.contact = contact;
		this.city = city;
		this.email = email;
	}
	public String  getName() {
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
	
	

}
