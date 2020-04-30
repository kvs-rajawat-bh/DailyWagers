package com.ContibutorService;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ContibutorService.Models.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long>{
	
	public List<Beneficiary> findByVerifiedAndAddressConfirmed(boolean verified, boolean addressConfirmed);

}
