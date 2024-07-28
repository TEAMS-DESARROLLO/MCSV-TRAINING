package com.bussinesdomain.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UnexpectedErrorException extends RuntimeException {

	public UnexpectedErrorException(String message) {
		super(message);
	}

}
