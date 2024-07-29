package com.example.demo.controller;

import com.example.demo.domain.SuccessResponse;
import com.example.demo.exception.NotFoundEmployeeException;
import com.example.demo.service.EmployeeDeleteService;
import com.example.demo.util.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeeDeleteController {

	private final EmployeeDeleteService employeeDeleteService;

    public EmployeeDeleteController(EmployeeDeleteService employeeDeleteService) {
        this.employeeDeleteService = employeeDeleteService;
    }

    @DeleteMapping(value="/tel/{tel}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteEmployeeByTel(@PathVariable(value="tel") String tel) throws NotFoundEmployeeException {

		employeeDeleteService.deleteEmployeeByTel(tel);

		return ResponseEntity.ok(DataUtil.objectToString(new SuccessResponse(String.format("전화번호(%s)가 삭제 되었습니다.", tel))));
	}

}
