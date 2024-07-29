package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.exception.NotFoundEmployeeException;
import com.example.demo.repository.EmployeeRespository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class EmployeeDeleteService {

	private final EmployeeRespository employeeRepository;

	public EmployeeDeleteService(EmployeeRespository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Transactional
	public void deleteEmployeeByTel(String tel) throws NotFoundEmployeeException {

		Optional<Employee> data = employeeRepository.fetchByTel(tel);
		if(data.isPresent()){
			employeeRepository.delete(data.get());
		}else{
			throw new NotFoundEmployeeException(String.format("요청한 전화번호(%s)의 직원정보가 없습니다.", tel));
		}
	}

}
