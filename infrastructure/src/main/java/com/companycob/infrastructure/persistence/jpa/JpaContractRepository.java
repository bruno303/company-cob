package com.companycob.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.companycob.infrastructure.persistence.jpa.entity.Contract;

@Repository
public interface JpaContractRepository extends JpaRepository<Contract, Long> {

	List<Contract> findByContractNumber(String contractNumber);
	
}
