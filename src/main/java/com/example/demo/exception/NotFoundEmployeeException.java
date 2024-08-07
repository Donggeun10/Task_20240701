package com.example.demo.exception;

/**
 * Employee 정보가 없을 경우 발생
 * */
public class NotFoundEmployeeException extends RuntimeException {

	public NotFoundEmployeeException(String message) {
		super(message);
	}

}
