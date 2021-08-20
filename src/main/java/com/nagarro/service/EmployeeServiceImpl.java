package com.nagarro.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.nagarro.entities.Employee;

/**
 * Employee Service Implementation class
 * 
 * @author palakkharbanda
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Employee> getEmployeeList() {
		ResponseEntity<List<Employee>> response = restTemplate.exchange("http://localhost:8084/restapi/employees/",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
				});
		List<Employee> employee = response.getBody();
		return employee;
	}

	@Override
	public Employee updateEmployee(Employee employee) {

		restTemplate.put("http://localhost:8084/restapi/employees/" + employee.getEmployeeCode(), employee);
		return employee;

	}

	@Override
	public void uploadEmployee(Employee employees) {

		restTemplate.postForObject("http://localhost:8084/restapi/employees", employees, Employee.class);

	}

	@Override
	public boolean deleteEmployee(Long employeeCode) {

		ResponseEntity<Boolean> deletedEmp = restTemplate.exchange(
				"http://localhost:8084/restapi/employees/" + employeeCode, HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Boolean>() {
				});
		if (deletedEmp.getBody()) {
			return true;
		}
		return false;
	}

	@Override
	public Employee getEmployeeById(Long employeeCode) {

		ResponseEntity<Employee> emp = this.restTemplate.exchange(
				"http://localhost:8084/restapi/employees/" + employeeCode, HttpMethod.GET, null,
				new ParameterizedTypeReference<Employee>() {
				});

		Employee employee = emp.getBody();
		System.out.println(employee);

		return employee;
	}

}
