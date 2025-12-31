package com.cjc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cjc.model.Employee;
import com.cjc.model.Employees;
import com.cjc.service.EmployeeService;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	// @Autowired
	// private EmployeeService employeeService;

	// inject secondary dependency using constructor no need of @Autowired
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// to insert data into database
	@PostMapping(value = "/addEmployee", produces = { "application/xml", "application/json" }, consumes = {
			"application/xml", "application/json	" })
	public Employee addEmployee(@RequestBody Employee employee) {
		System.out.println("In Controller Layer:" + employee);
		Employee dbEmployee = employeeService.saveAll(employee);
		return dbEmployee;
	}

	// to get single record from db using id
	@GetMapping(value = "/getEmployee/{id}", produces = { "application/xml" })
	public Employee getEmployee(@PathVariable int id) {
		System.out.println("Fetching data for Employee Id:" + id);
		Employee dbEmployee = employeeService.getByID(id);
		return dbEmployee;
	}

	// to get all records from db
	@GetMapping(value = "/getEmployees")
	public List<Employee> getAllEmployee() {
		List<Employee> empList = employeeService.getAllEmployees();
		return empList;
	}

	// to fetch all record in xml format
	@GetMapping("/getEmployees-xml")
	public Employees getAllEmployeesXml() {
		List<Employee> empList = employeeService.getAllEmployees();
		Employees employees = new Employees();
		employees.setEmpList(empList);
		return employees;
	}

	// to delete record from db using id
	@DeleteMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable int id) {
		String msg = employeeService.deleteEmployeeByID(id);
		return msg;
	}

	// to update all record using id
	@PutMapping("/updateEmployee/{id}")
	public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
		Employee dbEmployee = employeeService.updateEmployee(id, employee);
		return dbEmployee;

	}

	// to update partial record
	@PatchMapping("/editEmployee/{id}")
	public Employee editEmployee(@PathVariable int id, @RequestBody Employee employee) {
		Employee editedEmployee = employeeService.editEmployee(id, employee);
		return editedEmployee;
	}

	// pagination manually
//	@GetMapping("/employees/page/{pageNo}/size/{pageSize}")
//	public List<Employee> getEmplpoyeesByPagination(@PathVariable int pageNo,@PathVariable int pageSize){
//		List<Employee> empList=employeeService.getEmployeesBypagination(pageNo,pageSize);
//		
//		return empList;
//	}

	// pagination for default page and size if we don't specify in url
	@GetMapping("/getEmployees/page")
	public List<Employee> getEmplpoyeesByPagination(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "5") int pageSize) {
		List<Employee> empList = employeeService.getEmployeesBypagination(pageNo, pageSize);

		return empList;
	}

	// sorting desc or asc
	@GetMapping(value = "/getEmployees/sortByPrice")
	public List<Employee> getEmployeesSortedByPrice(@RequestParam(defaultValue = "asc") String direction) {

		return employeeService.getEmployeeSortedByPrice(direction);

	}

	// searching by name
	@GetMapping("/getEmployees/searchByName/{name}")
	public List<Employee> getEmployeeByName(@PathVariable String name) {
		return employeeService.getEmployeesByName(name);

	}

	// filtering accrording to price,category
	@GetMapping("/getEmployees/filter")
	public List<Employee> getEmployeeByFilter(@RequestParam(required = false) Double minSalary,@RequestParam(required = false) Double maxSalary,
			@RequestParam(required = false) String dept) {

		Map<String, String> filters = new HashMap<String, String>();
		
		if (dept != null && !dept.isEmpty()) {
			filters.put("dept", dept);
		}
		if (minSalary != null) {
			filters.put("minSalary", String.valueOf(minSalary));
		}
		if (maxSalary != null) {
			filters.put("maxSalary", String.valueOf(maxSalary));
		}
		return employeeService.getEmployeeByFilter(filters);
	}

}
