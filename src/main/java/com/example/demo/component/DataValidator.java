package com.example.demo.component;

import com.example.demo.entity.Employee;
import java.util.List;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataValidator {

	private boolean isTel(String tel) {
		String reg = "^010-?(\\d{3}|\\d{4})-?\\d{4}";
		return Pattern.matches(reg, tel);
	}

	private boolean isEmail(String email) {
		String reg = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
		return Pattern.matches(reg, email);
	}

	public boolean isValidEmployees(List<Employee> employees) {

		for(Employee employee : employees) {
			if(!isValidEmployee(employee)) {
				return false;
			}
		}
		return true;
	}

	public boolean isValidEmployee(Employee employee) {

		if(!isTel(employee.getTel())) {
			log.error("Invalid tel: {}", employee.getTel());
			return false;
		} else if(!isEmail(employee.getEmail())) {
			log.error("Invalid email: {}", employee.getEmail());
			return false;
		}
		return true;

	}

}
