package com.companycob.domain.lock.contract;

import com.companycob.domain.lock.LockManager;
import com.companycob.domain.model.entity.Contract;

public interface ContractLocker extends LockManager<Contract> {
    
}
