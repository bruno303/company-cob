package com.companycob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.persistence.BankRepository;

@Service
public class BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    public List<Bank> findAll() {
        return bankRepository.findAll();
    }
}
