package com.companycob.infrastructure.persistence.jpa;

import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.persistence.BankRepository;
import com.companycob.infrastructure.persistence.converter.BankConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BankRepositoryImpl implements BankRepository {

    private final JpaBankRepository jpaBankRepository;

    @Autowired
    public BankRepositoryImpl(JpaBankRepository jpaBankRepository) {
        this.jpaBankRepository = jpaBankRepository;
    }

    @Override
    public Bank save(Bank entity) {
        var bankPersistence = BankConverter.bankDomainToBankPersistence(entity);
        var save = jpaBankRepository.save(bankPersistence);

        return BankConverter.bankPersistenceToBankDomain(save);
    }

    @Override
    public List<Bank> saveAll(List<Bank> entities) {
        var banks = entities
                .stream()
                .map(BankConverter::bankDomainToBankPersistence)
                .collect(Collectors.toList());

        var saveAll = jpaBankRepository.saveAll(banks);

        return saveAll
                .stream()
                .map(BankConverter::bankPersistenceToBankDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Bank entity) {
        var bankPersistence = BankConverter.bankDomainToBankPersistence(entity);
        jpaBankRepository.delete(bankPersistence);
    }

    @Override
    public Optional<Bank> findById(Integer integer) {
        var result = jpaBankRepository.findById(integer);
        return result.map(BankConverter::bankPersistenceToBankDomain);
    }

    @Override
    public List<Bank> findAll() {
        var all = jpaBankRepository.findAll();
        return all.stream().map(BankConverter::bankPersistenceToBankDomain).collect(Collectors.toList());
    }
}
