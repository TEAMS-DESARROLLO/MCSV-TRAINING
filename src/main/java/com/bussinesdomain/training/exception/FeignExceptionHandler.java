package com.bussinesdomain.training.exception;

import feign.FeignException;

public class FeignExceptionHandler {
	
	private FeignExceptionHandler() {}

	public static void handleFeignException( Exception e, Long id , String entityType ) {
		if( e instanceof FeignException.NotFound ) {
			throw new ModelNotFoundException("ID from "+ entityType +" not found , id : "+id);
		}else if( e instanceof FeignException.InternalServerError ) {
			throw new IllegalArgumentException("Internal server error when checking "+ entityType);
		}else if( e instanceof FeignException.FeignClientException) {
			throw new ServiceException("Client error when checking " + entityType);
		}else if( e instanceof FeignException) {
			throw new ServiceException("Error when checking " +entityType);
		}else {
			throw new UnexpectedErrorException("Unexpected error when checking " +entityType);
		}
	}
	
}
