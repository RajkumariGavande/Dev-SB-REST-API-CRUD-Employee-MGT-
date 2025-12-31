package com.cjc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cjc.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findByName(String name);

	List<Employee> findByDeptOrSalaryBetween(String dept, Double minSalary, Double maxSalary);

	List<Employee> findByDeptAndSalaryBetween(String dept, Double minSalary, Double maxSalary);

}
