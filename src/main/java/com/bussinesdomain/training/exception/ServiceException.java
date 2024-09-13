package com.bussinesdomain.training.exception;

public class ServiceException extends RuntimeException {
    private Throwable throwable;

    public ServiceException(String message, Throwable e) {

        super(message);
        this.throwable = e;
    }
    
}
