package com.nagarro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nagarro.entities.Employee;
import com.nagarro.service.EmployeeService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("/index")
	public String dashboard(@ModelAttribute Employee e, Model m)
	{
		List<Employee> emp = employeeService.getEmployeeList();
		m.addAttribute("emp", emp);
		return "details_page";
	}

}
