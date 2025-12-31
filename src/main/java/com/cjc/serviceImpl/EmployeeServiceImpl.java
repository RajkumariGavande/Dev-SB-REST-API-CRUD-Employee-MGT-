package com.cjc.serviceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.cjc.model.Employee;
import com.cjc.repository.EmployeeRepository;
import com.cjc.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee saveAll(Employee employee) {
		Employee dbEmployee = employeeRepository.save(employee);
		return dbEmployee;
	}

	@Override
	public Employee getByID(int id) {
		if (employeeRepository.existsById(id)) {
			Employee dbEmployee = employeeRepository.findById(id).get();
			return dbEmployee;
		}
		return null;

	}

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> list = employeeRepository.findAll();
		return list;
	}

	@Override
	public String deleteEmployeeByID(int id) {
		if (employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
			return "Record deleted Successfully having id:" + id;
		}
		return "Record is not available for given id:" + id;
	}

	@Override
	public Employee updateEmployee(int id, Employee employee) {
		if (employeeRepository.existsById(id)) {
			Employee dbEmployee = employeeRepository.findById(id).get();
			dbEmployee.setName(employee.getName());
			dbEmployee.setDesignation(employee.getDesignation());
			dbEmployee.setSalary(employee.getSalary());
			employeeRepository.save(dbEmployee);
			return dbEmployee;
		}
		return null;
	}

	@Override
	public Employee editEmployee(int id, Employee employee) {
		if (employeeRepository.existsById(id)) {
			Employee dbEmployee = employeeRepository.findById(id).get();
			if (employee.getName() != null) {
				dbEmployee.setName(employee.getName());
			}
			if (employee.getDesignation() != null) {
				dbEmployee.setDesignation(employee.getDesignation());
			}
			if (employee.getSalary() != 0.0) {// instead of 0.0=Integer(wrapper class)
				dbEmployee.setSalary(employee.getSalary());
			}
			Employee editedEmployee = employeeRepository.save(dbEmployee);
			return editedEmployee;
		}
		return null;
	}

	@Override
	public List<Employee> getEmployeesBypagination(int pageNo, int pageSize) {
		Pageable page = PageRequest.of(pageNo, pageSize);
		Page data = employeeRepository.findAll(page);
		if (data.hasContent()) {
			List<Employee> emplist = (List<Employee>) data.getContent();
			return emplist;
		}

		return Collections.emptyList();
	}

	@Override
	// direction=desc or asc
	public List<Employee> getEmployeeSortedByPrice(String direction) {

		Sort sort;
		if (direction != null && direction.equalsIgnoreCase("desc")) {
			sort = Sort.by(Direction.DESC, "salary");
		} else {
			sort = Sort.by(Direction.ASC, "salary");
		}
		return employeeRepository.findAll(sort);
	}

	@Override
	public List<Employee> getEmployeesByName(String name) {

		List<Employee> productList = employeeRepository.findByName(name);
		if (!productList.isEmpty()) {
			return productList;
		} else {
			return null;
		}

	}

	@Override
	public List<Employee> getEmployeeByFilter(Map<String, String> filters) {
		String dept = filters.get("dept");
		Double minSalary = filters.get("minSalary") != null ? Double.valueOf(filters.get("minSalary")) : null;
		Double maxSalary = filters.get("maxSalary") != null ? Double.valueOf(filters.get("maxSalary")) : null;

		if (dept != null && minSalary != null && maxSalary != null) {
			return employeeRepository.findByDeptAndSalaryBetween(dept, minSalary, maxSalary);
		} else {
			return employeeRepository.findByDeptOrSalaryBetween(dept, minSalary, maxSalary);
		}

	}

}
