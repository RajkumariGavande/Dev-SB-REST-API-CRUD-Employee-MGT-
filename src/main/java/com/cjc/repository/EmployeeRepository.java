package com.cjc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cjc.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer>{

	
	
}
