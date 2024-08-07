package com.example.demo.exception;

/**
 * 정의 되지 않은 Content-Type 일 경우 발생
 * */
public class NotFoundContentTypeException extends RuntimeException {

	public NotFoundContentTypeException(String message) {
		super(message);
	}

}
