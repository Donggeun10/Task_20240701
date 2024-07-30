package com.example.demo.controller;

import com.example.demo.exception.BadRequestParseException;
import com.example.demo.exception.DataSaveException;
import com.example.demo.exception.NotFoundContentTypeException;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.component.DataConverter;
import com.example.demo.entity.Employee;
import com.example.demo.enums.ContentType;
import com.example.demo.service.EmployeeCreateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeePostController {
	
	private final DataConverter dataConverter;
	private final EmployeeCreateService employeeWriteService;
	
	public EmployeePostController(DataConverter dataConverter, EmployeeCreateService employeeWriteService) {
		this.dataConverter = dataConverter;
		this.employeeWriteService = employeeWriteService;
	}
	
	@PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> postEmployeesFile(@RequestPart MultipartFile file, @RequestParam ContentType contentType) throws NotFoundContentTypeException, BadRequestParseException, DataSaveException {

		List<Employee> employees = dataConverter.parseRequestBodyToEmployeeList(contentType, file);
	
		employeeWriteService.insertEmployees(employees);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postEmployeesText(@RequestBody String body, @RequestParam ContentType contentType) throws NotFoundContentTypeException, BadRequestParseException, DataSaveException {

		List<Employee> employees = dataConverter.parseRequestBodyToEmployeeList(contentType, body);
		
		employeeWriteService.insertEmployees(employees);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
