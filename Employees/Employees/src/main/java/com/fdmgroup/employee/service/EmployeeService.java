package com.fdmgroup.employee.service;

import java.util.*;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.employee.model.Employee;
import com.fdmgroup.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {
	private final static Logger log = LoggerFactory.getLogger(EmployeeService.class);
	private EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	/**
	 * Creates a new employee using all the fields, such as address, first name,
	 * last name, and hire date.
	 * 
	 * @param employee - An instance of the Employee class containing the employee
	 *                 details.
	 * @return - The saved Employee instance if all fields are completed; otherwise,
	 *         it returns null.
	 */

	public Employee createEmployee(Employee employee) {
		log.info("Entering createEmployee");
		log.info("Creating a new employee");
		if (employee.getAddress() == null || employee.getFirstName() == null || employee.getLastName() == null
				|| employee.getHireDate() == null || employeeRepository.existsById(employee.getId())) {
			log.info("Error: not all fileds filled");
			log.info("Exiting createEmployee");
			return null;
		}
		log.info("Exiting createEmployee");
		return employeeRepository.save(employee);
	}

	/**
	 * Retrieves an employee by their ID.
	 * 
	 * @param id - The ID of the employee to retrieve.
	 * @return - The Employee instance if the ID is found in the database;
	 *         otherwise, it returns null.
	 */
	public Employee getEmployee(int id) {
		log.info("Entering getEmployee");
		log.info("Retrieving an employee by id");
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if (optionalEmployee.isPresent()) {
			log.info("Exiting getEmployee");
			return optionalEmployee.get();
		}
		log.info("Exiting getEmployee");
		return null;
	}

	/**
	 * Lists all employees.
	 * 
	 * @return - A list of all employees in the database.
	 */
	public List<Employee> listAll() {
		log.info("Entering listAll");
		log.info("Exiting listAll");
		return employeeRepository.findAll();
	}

	/**
	 * Lists employees based on the provided address.
	 * 
	 * @param address - The address to search for.
	 * @return - A list of employees with addresses containing the provided address.
	 */
	public List<Employee> listByAddress(String address) {
		log.info("Entering listByAddress");
		log.info("Exiting listByAddress");
		return employeeRepository.findByAddressContainsIgnoreCase(address);
	}

	/**
	 * Searches employees by first name.
	 * 
	 * @param firstName - The first name to search for.
	 * @return - A list of employees with first names containing the provided first
	 *         name.
	 */
	public List<Employee> findByFirstName(String firstName) {
		log.info("Entering findByFirstName");
		log.info("Exiting findByFirstName");
		return employeeRepository.findByFirstNameContainsIgnoreCase(firstName);
	}

	/**
	 * Searches employees by last name.
	 * 
	 * @param lastName - The last name to search for.
	 * @return - A list of employees with last names containing the provided last
	 *         name.
	 */
	public List<Employee> findByLastName(String lastName) {
		log.info("Entering findByLastName");
		log.info("Exiting findByLastName");
		return employeeRepository.findByLastNameContainsIgnoreCase(lastName);
	}

	/**
	 * Searches employees by both first name and last name.
	 * 
	 * @param firstName - The first name to search for.
	 * @param lastName  - The last name to search for.
	 * @return - A list of employees with matching first name and last name.
	 */
	public List<Employee> findByFullName(String firstName, String lastName) {
		log.info("Entering findByFullName");
		log.info("Exiting findByFullName");
		return employeeRepository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName);
	}

	/**
	 * Search employees based on the search input (first name, last name, or both)
	 *
	 * @param searchInput The search input entered by the user
	 * @return A list of employees matching the search criteria
	 */
	public List<Employee> searchEmployees(String searchInput) {
		log.info("Entering searchEmployees");

		// Perform the search based on searchInput
		List<Employee> employees = new ArrayList<>();

		// Check if the searchInput is empty
		if (searchInput.isEmpty()) {
			// Return all employees if searchInput is empty
			employees = employeeRepository.findAll();
		} else {
			// Perform the search using first name, last name, or both
			employees = employeeRepository.findByFullNameContainsIgnoreCase(searchInput);

			// Split the searchInput into first name and last name
			String[] nameParts = searchInput.split(" ");

			// If both first name and last name are provided, we perform an additional
			// search
			if (nameParts.length > 1) {
				String firstName = nameParts[0];
				String lastName = nameParts[1];

				employees.addAll(employeeRepository.findByFirstNameContainsIgnoreCase(firstName));
				employees.addAll(employeeRepository.findByLastNameContainsIgnoreCase(lastName));
			}
		}

		// Remove duplicates from the result
		employees = new ArrayList<>(new HashSet<>(employees));

		log.info("Exiting searchEmployees");
		return employees;
	}

}
