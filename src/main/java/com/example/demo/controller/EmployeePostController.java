package com.example.demo.controller;

import com.example.demo.component.DataConverter;
import com.example.demo.entity.Employee;
import com.example.demo.enums.ContentType;
import com.example.demo.exception.BadRequestParseException;
import com.example.demo.exception.DataSaveException;
import com.example.demo.exception.NotFoundContentTypeException;
import com.example.demo.service.EmployeeCreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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

	@Operation(summary = "직원 정보 등록", description = "\\<input type\\=file\\>을 통해 csv, json 파일 업로드를 통해서 직원 정보 등록")
	@PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> postEmployeesFile(@RequestPart MultipartFile file, @RequestParam ContentType contentType) throws NotFoundContentTypeException, BadRequestParseException, DataSaveException {

		List<Employee> employees = dataConverter.parseRequestBodyToEmployeeList(contentType, file);
		employeeWriteService.insertEmployees(employees);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "직원 정보 등록", description = " \\<textarea\\>\\</textarea\\>에서 직접 데이터를 입력한 경우 application/json으로 직원 정보 등록, CSV 는 header 를 입력하지 않습니다.", responses = {
		@ApiResponse( responseCode = "201", description = "요청 데이터가 정상적으로 저장됨" ),
		@ApiResponse( responseCode = "400", description = "데이터 분석 중 오류가 발생함" ),
		@ApiResponse( responseCode = "404", description = "식별할 수 없는 유형의 데이터를 전송함" ),
		@ApiResponse( responseCode = "500", description = "데이터 저장 중 오류 발생함" )
	})
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postEmployeesText(@RequestBody String body, @RequestParam ContentType contentType) throws NotFoundContentTypeException, BadRequestParseException, DataSaveException {

		List<Employee> employees = dataConverter.parseRequestBodyToEmployeeList(contentType, body);
		employeeWriteService.insertEmployees(employees);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
