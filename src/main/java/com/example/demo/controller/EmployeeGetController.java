package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.BadRequestNoContentPageException;
import com.example.demo.exception.NotFoundEmployeeException;
import com.example.demo.service.EmployeeReadService;
import com.example.demo.util.DataUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeeGetController {
	
	private final EmployeeReadService employeeReadService;
	
	public EmployeeGetController(EmployeeReadService employeeReadService) {
		this.employeeReadService = employeeReadService;
	}
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllEmployees(){
		
		return ResponseEntity.ok(DataUtil.objectToString(employeeReadService.selectAllEmployees()));
	}
	
	@GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getEmployeeByName(@PathVariable String name) throws NotFoundEmployeeException{
		
		return ResponseEntity.ok(DataUtil.objectToString(employeeReadService.selectEmployeeByName(name)));
	}

	@GetMapping(value = "/tel/{tel}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getEmployeeByTel(@PathVariable String tel) throws NotFoundEmployeeException{

		return ResponseEntity.ok(DataUtil.objectToString(employeeReadService.selectEmployeeByTel(tel)));
	}

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getEmployeesByPage(@RequestParam int page, @RequestParam int pageSize) throws BadRequestNoContentPageException{
		
		return ResponseEntity.ok(DataUtil.objectToString(employeeReadService.selectEmployeeListByPage(page, pageSize)));
	}
}
