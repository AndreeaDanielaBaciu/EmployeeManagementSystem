package com.fdmgroup.employee.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fdmgroup.employee.model.Employee;
import com.fdmgroup.employee.repository.EmployeeRepository;
import com.fdmgroup.employee.service.*;

@Controller
public class EmployeeController {
	private final static Logger log = LoggerFactory.getLogger(Controller.class);
	private EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * Displays the "create new employee" functionality on the index page.
	 * 
	 * @param model    - The model object to add attributes.
	 * @param employee - An instance of the Employee class.
	 * @return - The view name for the createEmployee page.
	 */
	@GetMapping("/create-employee")
	public String displayCreateEmployee(Model model, @ModelAttribute Employee employee) {
		log.info("Entering displayCreateEmployee");
		log.info("Creating a new employee");
		model.addAttribute("employee", new Employee());
		if (employee != null) {
			model.addAttribute("employee", employee);
		}
		log.info("Exiting displayCreateEmployee");
		return "createEmployee";
	}

	/**
	 * Handles the submission of a new employee form.
	 * 
	 * @param employee           - An instance of the Employee class containing the
	 *                           employee details.
	 * @param redirectAttributes - The RedirectAttributes object to add flash
	 *                           attributes.
	 * @return - The redirect URL based on the outcome of the submission.
	 */
	@PostMapping("/submit-new-employee")
	public String handleSubmitNewEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
		log.info("Entering handleSubmitNewEmployee");
		log.info("Creating employee: " + employee);
		if (employeeService.createEmployee(employee) == null) {
			redirectAttributes.addFlashAttribute("errorMessage",
					"Unable to add Employee, please check that all fields are filled in.");
			redirectAttributes.addFlashAttribute("employee", employee);
			log.info("Exiting handleSubmitNewEmployee");
			return "redirect:/create-employee";
		}
		log.info("Exiting handleSubmitNewEmployee");
		return "redirect:/show-employees";
	}

	/**
	 * Displays the list of all employees.
	 * 
	 * @param model - The model object to add attributes.
	 * @return - The view name for the showEmployees page.
	 */
	@GetMapping("/show-employees")
	public String displayShowEmployees(Model model) {
		log.info("Entering displayShowEmployee");
		log.info("Showing employees");
		model.addAttribute("employees", employeeService.listAll());
		// log.info("employees deployed: " + employeeService.listAll());
		log.info("Exiting displayShowEmployee");
		return "showEmployees";
	}

	/**
	 * Handles the search by address functionality.
	 * 
	 * @param model   - The model object to add attributes.
	 * @param address - The address to search for.
	 * @return - The view name for the showEmployees page.
	 */
	@PostMapping("search-by-address")
	public String handleSearchByAdress(Model model, @RequestParam("address") String address) {
		log.info("Entering handleSearchByAdress");
		if (!employeeService.listByAddress(address).isEmpty()) {
			model.addAttribute("employees", employeeService.listByAddress(address));
			log.info("Exiting handleSearchByAdress");
			return "showEmployees";
		}
		log.info("Exiting handleSearchByAdress");
		return "errorPage";
	}

	/**
	 * Handles the search by first name functionality.
	 * 
	 * @param model     - The model object to add attributes.
	 * @param firstName - The first name to search for.
	 * @return - The view name for the showEmployees page.
	 */
	@PostMapping("search-by-name")
	public String handleSearchByFirstName(Model model, @RequestParam("firstName") String firstName) {
		log.info("Entering handleSearchByFirstName");
		if (!employeeService.findByFirstName(firstName).isEmpty()) {
			model.addAttribute("employees", employeeService.findByFirstName(firstName));
			log.info("Exiting handleSearchByFirstName");
			return "showEmployees";
		}
		log.info("Exiting handleSearchByFirstName");
		return "errorPage";
	}

	/**
	 * Handles the search by last name functionality.
	 * 
	 * @param model    - The model object to add attributes.
	 * @param lastName - The last name to search for.
	 * @return - The view name for the showEmployees page.
	 */
	@PostMapping("search-by-last-name")
	public String handleSearchByLastName(Model model, @RequestParam("lastName") String lastName) {
		log.info("Entering handleSearchByLastName");
		if (!employeeService.findByLastName(lastName).isEmpty()) {
			model.addAttribute("employees", employeeService.findByLastName(lastName));
			log.info("Exiting handleSearchByLastName1");
			return "showEmployees";
		}
		log.info("Exiting handleSearchByLastName2");
		return "errorPage";
	}

	/**
	 * Handles the search by full name functionality.
	 * 
	 * @param model    - The model object to add attributes.
	 * @param fullName - The full name to search for.
	 * @return - The view name for the showEmployees page.
	 */
	@PostMapping("search-by-full-name")
	public String handleSearchByFullName(Model model, @RequestParam("fullName") String fullName) {
		log.info("Entering handleSearchByFullName");

		// Split the full name into first name and last name
		String[] nameParts = fullName.split(" ");
		String firstName = nameParts[0];
		String lastName = nameParts[1];

		// Perform the search using both first name and last name
		List<Employee> employees = employeeService.findByFullName(firstName, lastName);

		if (!employees.isEmpty()) {
			model.addAttribute("employees", employees);
			log.info("Exiting handleSearchByFullName");
			return "showEmployees";
		}

		log.info("Exiting handleSearchByFullName");
		return "errorPage";
	}

	/**
	 * Handles the search employees functionality.
	 * Search either by first name or last name or both. 
	 * 
	 * @param model       - The model object to add attributes.
	 * @param searchInput - The search input to perform the search.
	 * @return - The view name for the showEmployees page.
	 */
	@PostMapping("/search-employees")
	public String handleSearchEmployees(Model model, @RequestParam("searchInput") String searchInput) {
		log.info("Entering handleSearchEmployees");

		// Perform the search using the searchInput
		List<Employee> employees = employeeService.searchEmployees(searchInput);

		if (!employees.isEmpty()) {
			model.addAttribute("employees", employees);
			log.info("Exiting handleSearchEmployees");
			return "showEmployees";
		}

		log.info("Exiting handleSearchEmployees");
		return "errorPage";
	}

//	@PostMapping("/submit-new-employee")
//	public String handleSubmitNewEmployee(Model model, @Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {
//	    if (bindingResult.hasErrors()) {
//	        // Handle validation errors
//	        return "createEmployee";
//	    }
//
//	    // Save the employee
//	    employeeService.createEmployee(employee);
//
//	    // Redirect to a success page or perform additional actions
//
//	    return "redirect:/employees";
//	}

}
