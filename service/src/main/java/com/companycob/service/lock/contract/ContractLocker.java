package com.companycob.service.lock.contract;

import org.springframework.stereotype.Component;

import com.companycob.domain.model.entity.Contract;
import com.companycob.service.lock.local.LocalLockManager;

@Component
public class ContractLocker extends LocalLockManager<Contract> {

}
