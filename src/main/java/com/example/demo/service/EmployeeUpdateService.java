package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.exception.NotFoundEmployeeException;
import com.example.demo.repository.EmployeeRespository;
import com.example.demo.util.DataUtil;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class EmployeeUpdateService {

	private final EmployeeRespository employeeRepository;

	public EmployeeUpdateService(EmployeeRespository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Transactional
	public void updateEmployeeByTel(String tel, Employee employee) throws NotFoundEmployeeException {
		
		log.debug("{}", DataUtil.objectToString(employee));
		if(tel.equals(employee.getTel())){
			Optional<Employee> data = employeeRepository.fetchByTel(tel);
			if(data.isPresent()){
				employeeRepository.save(employee);
			}else{
				throw new NotFoundEmployeeException(String.format("요청한 전화번호(%s)의 직원정보가 없습니다.", tel));
			}
		}else{
			throw new NotFoundEmployeeException(String.format("요청한 전화번호(%s)와 변경될 데이터의 전화번호(%s)가 다릅니다.", tel, employee.getTel()));
		}
	}

}
