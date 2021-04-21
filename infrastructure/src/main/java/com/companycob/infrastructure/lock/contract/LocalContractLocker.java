package com.companycob.infrastructure.lock.contract;

import org.springframework.stereotype.Component;

import com.companycob.domain.lock.contract.ContractLocker;
import com.companycob.domain.model.entity.Contract;
import com.companycob.infrastructure.lock.local.LocalLockManager;

@Component
public class LocalContractLocker extends LocalLockManager<Contract> implements ContractLocker {

}
