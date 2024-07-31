package com.example.demo.service;

import com.example.demo.component.DataValidator;
import com.example.demo.entity.Employee;
import com.example.demo.exception.DataSaveException;
import com.example.demo.exception.NotFoundEmployeeException;
import com.example.demo.repository.EmployeeRespository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class EmployeeUpdateService {

	private final EmployeeRespository employeeRepository;
	private final DataValidator dataValidator;

	public EmployeeUpdateService(EmployeeRespository employeeRepository, DataValidator dataValidator) {
		this.employeeRepository = employeeRepository;
        this.dataValidator = dataValidator;
    }

	@Transactional(rollbackFor = {NotFoundEmployeeException.class, DataSaveException.class})
	public void updateEmployeeByTel(String tel, Employee employee) throws NotFoundEmployeeException, DataSaveException {

		if (dataValidator.isValidEmployee(employee)) {

			if (tel.equals(employee.getTel())) {
				Optional<Employee> data = employeeRepository.fetchByTel(tel);

				if (data.isPresent()) {
					try {
						employeeRepository.saveAndFlush(employee);
					} catch (Exception e) {
						throw new DataSaveException(e.getMessage());
					}
				} else {
					throw new NotFoundEmployeeException(String.format("요청한 전화번호(%s)의 직원정보가 없습니다.", tel));
				}
			} else {
				throw new NotFoundEmployeeException(String.format("요청한 전화번호(%s)와 변경될 데이터의 전화번호(%s)가 다릅니다.", tel, employee.getTel()));
			}
		}else{
			throw new DataSaveException("유효하지 않은 데이터로 저장할 수 없습니다.");
		}
	}

}
