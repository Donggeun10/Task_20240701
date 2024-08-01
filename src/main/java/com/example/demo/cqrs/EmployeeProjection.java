package com.example.demo.cqrs;

import com.example.demo.cqrs.query.EmployeeByNameQuery;
import com.example.demo.cqrs.query.EmployeeByPagingQuery;
import com.example.demo.cqrs.query.EmployeeByTelQuery;
import com.example.demo.entity.Employee;
import com.example.demo.exception.BadRequestNoContentPageException;
import com.example.demo.exception.NotFoundEmployeeException;
import com.example.demo.repository.EmployeeRespository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmployeeProjection {

    private final EmployeeRespository employeeRepository;

    public EmployeeProjection(EmployeeRespository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @QueryHandler
    public List<Employee> findByTel(EmployeeByTelQuery query) throws NotFoundEmployeeException {

        String tel = query.getTel();
        List<Employee> employees = employeeRepository.findByTelStartingWith(tel);

        if(employees.isEmpty()) {
            throw new NotFoundEmployeeException(String.format("요청한 전화번호(%s)로 시작하는 직원 정보가 없습니다.", tel));
        } else {
            return employees;
        }
    }

    @QueryHandler
    public Employee findByName(EmployeeByNameQuery query) throws NotFoundEmployeeException {

        String name = query.getName();
        Optional<Employee> employeeOpt = employeeRepository.findByName(name);
        return employeeOpt.orElseThrow(() -> new NotFoundEmployeeException(String.format("요청한 이름(%s)의 직원정보가 없습니다.", name)));

    }

    @QueryHandler
    public List<Employee> findByPaging(EmployeeByPagingQuery query) throws BadRequestNoContentPageException {

        int page = query.getPage();
        int pageSize = query.getPageSize();

        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "name"));
        Page<Employee> employeeList = employeeRepository.findAll(pageRequest);
        if(employeeList.hasContent()) {
            return employeeList.toList();
        } else {
            throw new BadRequestNoContentPageException(String.format("요청한 페이지(%d, pageSize:%d)의 데이터는 존재하지 않습니다. ", page, pageSize));
        }

    }
}