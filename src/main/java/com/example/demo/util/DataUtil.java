package com.example.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataUtil {

	private DataUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String objectToString(Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			StackTraceElement[] stackTrace =  e.getStackTrace();
			log.error("{}:{}", e.getMessage(), stackTrace[0]);
		}
		
		return "";
	}
	
	public static String makeErrorLogMessage(Exception e) {
		StackTraceElement[] stackTrace =  e.getStackTrace();
		return String.format("%s:%s", e.getMessage(), stackTrace[0]);
	}
	
}
