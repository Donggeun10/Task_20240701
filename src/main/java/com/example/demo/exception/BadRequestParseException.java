package com.example.demo.exception;

/**
 * Request Parameter Content-Type 과 Request Body 의 실제 데이터가 맞지 않을 경우 발생
 * */
public class BadRequestParseException extends Exception {

	public BadRequestParseException(String message) {
		super(message);
	}

}
