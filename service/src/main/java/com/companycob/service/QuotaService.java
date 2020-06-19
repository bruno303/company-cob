package com.companycob.service;

import org.springframework.stereotype.Service;

import com.companycob.domain.model.dto.ValidationErrorsCollection;
import com.companycob.domain.model.entity.Quota;

@Service
public class QuotaService {

	public ValidationErrorsCollection verify(Quota quota) {
		var result = new ValidationErrorsCollection();
		
		if (quota.getContract() == null) {
			result.addError("contract", "Contract can't be empty");
		}
		
		if (quota.getDueDate() == null) {
			result.addError("dueDate", "DueDate can't be empty");
		}
		
		if (quota.getInitialValue() < 0) {
			result.addError("initialValue", "InitialValue can't be negative");
		}
		
		if (quota.getNumber() < 1) {
			result.addError("number", "Number must be greather than 0");
		}
		
		return result;
	}
	
}
