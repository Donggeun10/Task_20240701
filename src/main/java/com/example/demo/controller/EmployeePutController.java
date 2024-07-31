package com.example.demo.controller;

import com.example.demo.domain.SuccessResponse;
import com.example.demo.entity.Employee;
import com.example.demo.exception.DataSaveException;
import com.example.demo.exception.NotFoundEmployeeException;
import com.example.demo.service.EmployeeUpdateService;
import com.example.demo.util.DataUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeePutController {

	private final EmployeeUpdateService employeeUpdateService;

    public EmployeePutController(EmployeeUpdateService employeeUpdateService) {
        this.employeeUpdateService = employeeUpdateService;
    }

	@Operation(summary = "직원 전화번호로 변경", responses = {
		@ApiResponse( responseCode = "202", description = "요청 전화번호에 해당하는 사용자 정보 변경 성공" ),
		@ApiResponse( responseCode = "404", description = "사용자 정보가 없음" ),
		@ApiResponse( responseCode = "500", description = "데이터 변경 중 오류 발생함" )
	})
    @PutMapping(value="/tel/{tel}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> putEmployeeByTel(@PathVariable(value="tel") String tel, @RequestBody Employee employee) throws NotFoundEmployeeException, DataSaveException {

		employeeUpdateService.updateEmployeeByTel(tel, employee);

		return ResponseEntity.accepted().body(DataUtil.objectToString(new SuccessResponse(String.format("전화번호(%s)가 변경되었습니다.", tel))));
	}

}
