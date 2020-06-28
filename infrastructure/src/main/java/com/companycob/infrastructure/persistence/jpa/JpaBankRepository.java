package com.companycob.infrastructure.persistence.jpa;

import com.companycob.infrastructure.persistence.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBankRepository extends JpaRepository<Bank, Integer> {

}
