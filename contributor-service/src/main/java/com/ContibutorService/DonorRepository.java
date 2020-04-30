package com.ContibutorService;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ContibutorService.Models.Donor;

public interface DonorRepository extends JpaRepository<Donor, Long>{


}
