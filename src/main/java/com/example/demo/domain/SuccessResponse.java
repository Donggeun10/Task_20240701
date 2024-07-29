package com.example.demo.domain;

import lombok.Getter;

@Getter
public class SuccessResponse {

	private String successMessage = "";

	public SuccessResponse(String successMsg) {
		successMessage = successMsg;
	}
}
