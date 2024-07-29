package com.example.demo.service;

import com.example.demo.component.DataValidator;
import com.example.demo.entity.Employee;
import com.example.demo.exception.DataSaveException;
import com.example.demo.repository.EmployeeRespository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeCreateService {
	
	private final EmployeeRespository employeeRepository;
	private final DataValidator dataValidator;
	
	public EmployeeCreateService(EmployeeRespository employeeRepository, DataValidator dataValidator) {
		this.employeeRepository = employeeRepository;
        this.dataValidator = dataValidator;
    }
	
	public void insertEmployees(List<Employee> employees) throws DataSaveException {
		
		try {
			if(dataValidator.isValidEmployees(employees)) {
				employeeRepository.saveAllAndFlush(employees);
			} else {
				throw new DataSaveException("유효하지 않은 데이터로 저장할 수 없습니다.");
			}

		} catch(Exception e) {
			throw new DataSaveException(e.getMessage());
		}
	}

}
