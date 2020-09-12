package com.companycob.service.arrears;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.companycob.domain.model.entity.Quota;
import com.companycob.utils.date.LocalDateUtils;

@Service
public class ArrearsDaysService {

	public long calculateArrearsDaysInSingleQuota(final Quota quota) {
		return LocalDateUtils.differenceInDays(quota.getDueDate(), LocalDate.now());
	}

}
