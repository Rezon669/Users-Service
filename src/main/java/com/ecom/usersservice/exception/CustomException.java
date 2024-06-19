package com.ecom.usersservice.exception;

public class CustomException extends Exception {

	public CustomException(String string, Exception e) {
        super();
    }

    public CustomException(String message) {
        super(message);
    }
}
