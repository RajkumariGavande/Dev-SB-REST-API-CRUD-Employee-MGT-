package com.cjc.service;

import java.util.List;

import com.cjc.model.Employee;

public interface EmployeeService {

	Employee saveAll(Employee employee);

	Employee getByID(int id);

	List<Employee> getAllEmployees();

	String deleteEmployeeByID(int id);

	Employee updateEmployee(int id, Employee employee);

	Employee editEmployee(int id, Employee employee);

	List<Employee> getEmployeesBypagination(int pageNo, int pageSize);
	

}
