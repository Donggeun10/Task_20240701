package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
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

	@Operation(summary = "모든 직원 목록 조회")
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllEmployees(){
		
		return ResponseEntity.ok(DataUtil.objectToString(employeeReadService.selectAllEmployees()));
	}

	@Operation(summary = "직원 이름으로 조회")
	@GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getEmployeeByName(@PathVariable String name) throws NotFoundEmployeeException{
		
		return ResponseEntity.ok(DataUtil.objectToString(employeeReadService.selectEmployeeByName(name)));
	}

	@Operation(summary = "직원 전화번호로 조회")
	@GetMapping(value = "/tel/{tel}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getEmployeeByTel(@PathVariable String tel) throws NotFoundEmployeeException{

		return ResponseEntity.ok(DataUtil.objectToString(employeeReadService.selectEmployeeByTel(tel)));
	}

	@Operation(summary = "페이지별 직원 목록 조회")
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getEmployeesByPage(@RequestParam int page, @RequestParam int pageSize) throws BadRequestNoContentPageException{
		
		return ResponseEntity.ok(DataUtil.objectToString(employeeReadService.selectEmployeeListByPage(page, pageSize)));
	}
}
