package com.example.demo.controller;

import com.example.demo.exception.BadRequestNoContentPageException;
import com.example.demo.exception.NotFoundEmployeeException;
import com.example.demo.service.EmployeeReadService;
import com.example.demo.util.DataUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class EmployeeGetController {
	
	private final EmployeeReadService employeeReadService;
	
	public EmployeeGetController(EmployeeReadService employeeReadService) {
		this.employeeReadService = employeeReadService;
	}

	@Operation(summary = "모든 직원 목록 조회", responses = {
		@ApiResponse( responseCode = "200", description = "조회된 사용자 정보를 반환함.")
	})
	@GetMapping(value = "/employees/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllEmployees(){
		
		return ResponseEntity.ok(DataUtil.objectToString(employeeReadService.selectAllEmployees()));
	}

	@Operation(summary = "직원 이름으로 조회", responses = {
		@ApiResponse( responseCode = "200", description = "조회된 사용자 정보를 반환함."),
		@ApiResponse( responseCode = "404", description = "사용자 정보가 없음" )
	})
	@GetMapping(value = "/employee/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getEmployeeByName(@PathVariable String name) throws NotFoundEmployeeException{
		
		return ResponseEntity.ok(DataUtil.objectToString(employeeReadService.selectEmployeeByName(name)));
	}

	@Operation(summary = "직원 전화번호로 조회", responses = {
		@ApiResponse( responseCode = "200", description = "조회된 사용자 정보를 반환함."),
		@ApiResponse( responseCode = "404", description = "사용자 정보가 없음" )
	})
	@GetMapping(value = "/employees/tel/{tel}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getEmployeeByTel(@PathVariable String tel) throws NotFoundEmployeeException{

		return ResponseEntity.ok(DataUtil.objectToString(employeeReadService.selectEmployeeByTel(tel)));
	}

	@Operation(summary = "페이지별 직원 목록 조회", responses = {
		@ApiResponse( responseCode = "200", description = "조회된 사용자 정보를 반환함"),
		@ApiResponse( responseCode = "400", description = "요청한 페이지에 해당하는 데이터가 없음" )
	})
	@GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getEmployeesByPage(@RequestParam int page, @RequestParam int pageSize) throws BadRequestNoContentPageException{
		
		return ResponseEntity.ok(DataUtil.objectToString(employeeReadService.selectEmployeeListByPage(page, pageSize)));
	}
}
