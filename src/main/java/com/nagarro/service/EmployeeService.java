package com.nagarro.service;

import java.util.List;
import com.nagarro.entities.Employee;

/**
 * Interface for basic employee data modification methods'declaration
 * 
 * @author palakkharbanda
 *
 */
public interface EmployeeService {

	/**
	 * To get the list of all employees
	 * 
	 * @return list of employees
	 */
	List<Employee> getEmployeeList();

	/**
	 * To update a particular employee's data
	 * 
	 * @param employee
	 * @return
	 */
	Employee updateEmployee(Employee employee);

	/**
	 * To add a new employee
	 * 
	 * @param employees
	 */
	void uploadEmployee(Employee employees);

	/**
	 * To delete a particular employee
	 * 
	 * @param employeeCode
	 * @return
	 */
	boolean deleteEmployee(Long employeeCode);

	/**
	 * To fetch the details of a particular employee by id
	 * 
	 * @param employeeCode
	 * @return
	 */
	Employee getEmployeeById(Long employeeCode);
}
