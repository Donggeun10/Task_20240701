package com.example.demo.service;

import com.example.demo.cqrs.query.EmployeeByNameQuery;
import com.example.demo.cqrs.query.EmployeeByPagingQuery;
import com.example.demo.cqrs.query.EmployeeByTelQuery;
import com.example.demo.entity.Employee;
import com.example.demo.exception.BadRequestNoContentPageException;
import com.example.demo.exception.NotFoundEmployeeException;
import com.example.demo.repository.EmployeeRespository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class EmployeeReadService {
	
	private final EmployeeRespository employeeRepository;
	private final QueryGateway queryGateway;

    public EmployeeReadService(EmployeeRespository employeeRepository, QueryGateway queryGateway) {
        this.employeeRepository = employeeRepository;
        this.queryGateway = queryGateway;
    }

    public Employee selectEmployeeByName(String name) throws NotFoundEmployeeException {

		return queryGateway.query(new EmployeeByNameQuery(name), Employee.class).join();

	}

	public List<Employee> selectEmployeeByTel(String tel) throws NotFoundEmployeeException {

		return queryGateway.query(new EmployeeByTelQuery(tel), ResponseTypes.multipleInstancesOf(Employee.class)).join();

	}

	public List<Employee> selectEmployeeListByPage(int page, int pageSize) throws BadRequestNoContentPageException {
		
		return queryGateway.query(new EmployeeByPagingQuery(page, pageSize), ResponseTypes.multipleInstancesOf(Employee.class)).join();

	}

	public List<Employee> selectAllEmployees() {

		return employeeRepository.findAll();

	}

}
