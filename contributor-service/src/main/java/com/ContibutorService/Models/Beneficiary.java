package com.ContibutorService.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Beneficiary {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String Ben_ID;
	private String Name;
	private String Phone;
	private String PostalAddress;
	private String POCName;
	private String PocContact;
	private String Whatsapp;
	private String PinCode;
	private String FamilySize;
	private String SourceofInformation;
	private String verifier;
	private boolean verified;
	private boolean addressConfirmed;
	private String comments;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBen_ID() {
		return Ben_ID;
	}
	public void setBen_ID(String ben_ID) {
		Ben_ID = ben_ID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getPostalAddress() {
		return PostalAddress;
	}
	public void setPostalAddress(String postalAddress) {
		PostalAddress = postalAddress;
	}
	public String getPOCName() {
		return POCName;
	}
	public void setPOCName(String pOCName) {
		POCName = pOCName;
	}
	public String getPocContact() {
		return PocContact;
	}
	public void setPocContact(String pocContact) {
		PocContact = pocContact;
	}
	public String getWhatsapp() {
		return Whatsapp;
	}
	public void setWhatsapp(String whatsapp) {
		Whatsapp = whatsapp;
	}
	public String getPinCode() {
		return PinCode;
	}
	public void setPinCode(String pinCode) {
		PinCode = pinCode;
	}
	public String getFamilySize() {
		return FamilySize;
	}
	public void setFamilySize(String familySize) {
		FamilySize = familySize;
	}
	public String getSourceofInformation() {
		return SourceofInformation;
	}
	public void setSourceofInformation(String sourceofInformation) {
		SourceofInformation = sourceofInformation;
	}
	public String getVerifier() {
		return verifier;
	}
	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public boolean isAddressConfirmed() {
		return addressConfirmed;
	}
	public void setAddressConfirmed(boolean addressConfirmed) {
		this.addressConfirmed = addressConfirmed;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	

}
