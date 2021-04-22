package com.companycob.infrastructure.lock.contract;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.companycob.domain.lock.contract.ContractLocker;
import com.companycob.domain.model.entity.Contract;
import com.companycob.infrastructure.annotations.ConditionalOnLocalLock;
import com.companycob.infrastructure.lock.local.LocalLockManager;

@Component
@ConditionalOnLocalLock
public class LocalContractLocker extends LocalLockManager<Contract> implements ContractLocker {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalContractLocker.class);

    @PostConstruct
    public void init() {
        LOGGER.info("Using local lock manager");
    }

}
