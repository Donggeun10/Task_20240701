package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.exception.BadRequestNoContentPageException;
import com.example.demo.exception.NotFoundEmployeeException;
import com.example.demo.repository.EmployeeRespository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeReadService {
	
	private final EmployeeRespository employeeRepository;

    public EmployeeReadService(EmployeeRespository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee selectEmployeeByName(String name) throws NotFoundEmployeeException {

		Optional<Employee> employeeOpt = employeeRepository.findByName(name);
		
		if(employeeOpt.isPresent()) {
			return employeeOpt.get();
		} else {
			throw new NotFoundEmployeeException(String.format("요청한 이름(%s)의 직원정보가 없습니다.", name));
		}
		
	}

	public List<Employee> selectEmployeeByTel(String tel) throws NotFoundEmployeeException {

		List<Employee> employees = employeeRepository.findByTelStartingWith(tel);

		if(employees.isEmpty()) {
			throw new NotFoundEmployeeException(String.format("요청한 전화번호(%s)로 시작하는 직원 정보가 없습니다.", tel));
		} else {
			return employees;
		}

	}

	public List<Employee> selectEmployeeListByPage(int page, int pageSize) throws BadRequestNoContentPageException {
		
		PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "name")); 
		Page<Employee> employeeList = employeeRepository.findAll(pageRequest);
		if(employeeList.hasContent()) {
			return employeeList.toList();
		} else {
			throw new BadRequestNoContentPageException(String.format("요청한 페이지(%d, pageSize:%d)의 데이터는 존재하지 않습니다. ", page, pageSize));
		}

	}


	public List<Employee> selectAllEmployees() {

		return employeeRepository.findAll();

	}

}
