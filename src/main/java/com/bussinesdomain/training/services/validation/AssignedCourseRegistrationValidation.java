package com.bussinesdomain.training.services.validation;

import org.springframework.stereotype.Service;

import com.bussinesdomain.training.client.master.MasterClient;
import com.bussinesdomain.training.exception.FeignExceptionHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignedCourseRegistrationValidation {

	private final MasterClient masterClient;

	public void checkIdUnitMeasureValid(Long idUnitMeasure) {
		try {
			masterClient.findUnitMeasureById(idUnitMeasure);
		} catch (Exception e) {
			FeignExceptionHandler.handleFeignException(e, idUnitMeasure, "unitMeasure");
		}
	}

}
