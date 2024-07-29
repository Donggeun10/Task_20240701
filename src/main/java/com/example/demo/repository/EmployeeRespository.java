package com.example.demo.repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRespository extends JpaRepository<Employee, String> { 
	
	Optional<Employee> findByName(String name);
	List<Employee> findByTelStartingWith(String tel);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT c FROM Employee c WHERE c.tel = ?1")
	Optional<Employee> fetchByTel(String tel);

}
