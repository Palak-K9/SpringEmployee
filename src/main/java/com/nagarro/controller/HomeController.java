package com.nagarro.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lowagie.text.DocumentException;
import com.nagarro.entities.Employee;
import com.nagarro.entities.Employees;
import com.nagarro.entities.UserPDFExporter;
import com.nagarro.repository.UserRepository;
import com.nagarro.service.EmployeeService;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/")
	public String signUp(Model model) {

		model.addAttribute("title", "SignUp page");
		model.addAttribute("employees", new Employees());
		return "signup";
	}

	/**
	 * For registration of new user
	 * 
	 * @param employees
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("employees") Employees employees, Model model, HttpSession session) {

		try {

			employees.setPassword(passwordEncoder.encode(employees.getPassword()));
			employees.setRole("ROLE_USER");
			this.userRepository.save(employees);
			model.addAttribute("employees", new Employees());
			return "details_page";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("employees", employees);
			return "signup";
		}

	}

	/**
	 * Handler for custom login
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/signin")
	private String customLogin(Model model) {
		model.addAttribute("title", "Login Page");
		return "login";
	}

	/**
	 * Handler for adding new employee
	 * 
	 * @return
	 */
	@GetMapping("/addemp")
	public String addEmp() {
		return "add";
	}

	@PostMapping("/register")
	public String empRegister(@ModelAttribute Employee e, Model m) {
		employeeService.uploadEmployee(e);
		List<Employee> emp = employeeService.getEmployeeList();
		m.addAttribute("emp", emp);
		return "details_page";
	}

	/**
	 * This method works when the user clicks the "Edit" Button to edit a particular
	 * employee's details and redirects the user to the page where he can fill new
	 * details of that employee
	 * 
	 * @param e
	 * @param employeeCode
	 * @param m
	 * @return
	 */
	@GetMapping("/edit/{employeeCode}")
	public String edit(@ModelAttribute Employee e, @PathVariable Long employeeCode, Model m) {
		Employee e1 = employeeService.getEmployeeById(employeeCode);
		employeeService.updateEmployee(e1);
		m.addAttribute("emp", e1);
		return "edit";

	}

	/**
	 * To update a particular employee's data and reload the existing page with
	 * modification(update) as done by the user
	 * 
	 * @param e
	 * @return
	 */
	@PostMapping("/update")
	public String updateEmp(@ModelAttribute Employee e) {
		employeeService.uploadEmployee(e);
		return "redirect:/user/index";
	}

	/**
	 * To reload the same page(Employee Details Page) after he clicks "Delete"
	 * Button and deletes a particular employee's data
	 * 
	 * @param employeeCode
	 * @return
	 */
	@GetMapping("/delete/{employeeCode}")
	public String deleteEmp(@PathVariable Long employeeCode) {
		boolean deleteEmployee = employeeService.deleteEmployee(employeeCode);
		if (deleteEmployee) {
			return "redirect:/user/index";
		}
		return "details_page";

	}

	/**
	 * To create a pdf of the data of all employees
	 * 
	 * @param response
	 * @throws DocumentException
	 * @throws IOException
	 */
	@GetMapping("/export")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";

		response.setHeader(headerKey, headerValue);

		List<Employee> listUsers = employeeService.getEmployeeList();

		UserPDFExporter exporter = new UserPDFExporter(listUsers);
		exporter.export(response);
	}

}
