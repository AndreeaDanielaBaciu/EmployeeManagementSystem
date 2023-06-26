package com.fdmgroup.employee.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.employee.model.Employee;
import com.fdmgroup.employee.repository.EmployeeRepository;

import java.util.*;

@SpringBootTest
public class EmployeeServiceTest {
	@Autowired
	EmployeeService employeeService;

	@MockBean
	EmployeeRepository mockEmployeeRepository;
	@MockBean
	Employee mockEmployee;
	@MockBean
	List<Employee> mockEmployeeList;

	@Test
	void test_listAll_callsFindAllFromEmployeeRepository_whenCalled() {
		employeeService.listAll();
		verify(mockEmployeeRepository).findAll();

	}

	@Test
	void test_createEmployee_AllFieldsFilled_ReturnsSavedEmployee() {
		// Create a mock employee
		Employee mockEmployee = new Employee();
		mockEmployee.setId(1);
		mockEmployee.setFirstName("John");
		mockEmployee.setLastName("Doe");
		mockEmployee.setAddress("123 Main St");
		mockEmployee.setHireDate(LocalDate.now());

		// Configure the behaviour of the mock repository
		when(mockEmployeeRepository.existsById(anyInt())).thenReturn(false);
		when(mockEmployeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

		// Call the method under test
		Employee savedEmployee = employeeService.createEmployee(mockEmployee);

		// Verify the interactions and assertions
		verify(mockEmployeeRepository).existsById(anyInt());
		verify(mockEmployeeRepository).save(any(Employee.class));
		assertEquals(mockEmployee, savedEmployee);
	}

	@Test
	void test_createEmployee_MissingField_ReturnsNull() {
		// Create an employee with a missing field 
		Employee employeeWithMissingField = new Employee();
		employeeWithMissingField.setLastName("Doe");
		employeeWithMissingField.setAddress("123 Main St");
		employeeWithMissingField.setHireDate(LocalDate.now());

		// Call the method under test
		Employee savedEmployee = employeeService.createEmployee(employeeWithMissingField);

		// Verify that the method returns null when a field is missing
		assertNull(savedEmployee);
	}

	@Test
	void test_createEmployee_DuplicateId_ReturnsNull() {
		// Create a mock employee
		Employee mockEmployee = new Employee();
		mockEmployee.setId(1);
		mockEmployee.setFirstName("John");
		mockEmployee.setLastName("Doe");
		mockEmployee.setAddress("123 Main St");
		mockEmployee.setHireDate(LocalDate.now());

		// Configure the behaviour of the mock repository
		when(mockEmployeeRepository.existsById(anyInt())).thenReturn(true);

		// Call the method under test
		Employee savedEmployee = employeeService.createEmployee(mockEmployee);

		// Verify that the method returns null when the ID already exists
		assertNull(savedEmployee);
	}

	@Test
	void testListByAddress() {
		// Mock data
		String address = "123 Main St";
		Employee employee1 = new Employee("John", "Doe", LocalDate.of(2022, 1, 15), address);
		Employee employee2 = new Employee("Jane", "Smith", LocalDate.of(2021, 12, 10), address);
		List<Employee> expectedEmployees = Arrays.asList(employee1, employee2);

		// Mock the behaviour of the repository
		when(mockEmployeeRepository.findByAddressContainsIgnoreCase(address)).thenReturn(expectedEmployees);

		// Call the method under test
		List<Employee> actualEmployees = employeeService.listByAddress(address);

		// Verify the result
		assertEquals(expectedEmployees, actualEmployees);
	}

	@Test
	void testFindByFirstName() {
		// Mock data
		String firstName = "John";
		String address = "123 Main St";
		Employee employee1 = new Employee(firstName, "Doe", LocalDate.of(2022, 1, 15), address);
		Employee employee2 = new Employee(firstName, "Smith", LocalDate.of(2022, 1, 15), address);
		List<Employee> expectedEmployees = Arrays.asList(employee1, employee2);

		// Mock the behaviour of the repository
		when(mockEmployeeRepository.findByFirstNameContainsIgnoreCase(firstName)).thenReturn(expectedEmployees);

		// Call the method under test
		List<Employee> actualEmployees = employeeService.findByFirstName(firstName);

		// Verify the result
		assertEquals(expectedEmployees, actualEmployees);
	}

	@Test
	void testFindByLastName() {
		// Mock data
		String lastName = "Doe";
		String address = "123 Main St";
		Employee employee1 = new Employee("John", lastName, LocalDate.of(2022, 1, 15), address);
		Employee employee2 = new Employee("Jane", lastName, LocalDate.of(2022, 1, 15), address);
		List<Employee> expectedEmployees = Arrays.asList(employee1, employee2);

		// Mock the behaviour of the repository
		when(mockEmployeeRepository.findByLastNameContainsIgnoreCase(lastName)).thenReturn(expectedEmployees);

		// Call the method under test
		List<Employee> actualEmployees = employeeService.findByLastName(lastName);

		// Verify the result
		assertEquals(expectedEmployees, actualEmployees);
	}

	@Test
	void testFindByFullName() {
		// Mock data
		String firstName = "John";
		String lastName = "Doe";
		String address = "123 Main St";
		Employee employee1 = new Employee(firstName, lastName, LocalDate.of(2022, 1, 15), address);
		Employee employee2 = new Employee(firstName, lastName, LocalDate.of(2022, 1, 15), address);
		List<Employee> expectedEmployees = Arrays.asList(employee1, employee2);

		// Mock the behaviour of the repository
		when(mockEmployeeRepository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName))
				.thenReturn(expectedEmployees);

		// Call the method under test
		List<Employee> actualEmployees = employeeService.findByFullName(firstName, lastName);

		// Verify the result
		assertEquals(expectedEmployees, actualEmployees);
	}

	@Test
	void testGetEmployee() {
		// Mock data
		int id = 1;
		String address = "123 Main St";
		Employee expectedEmployee = new Employee("John", "Doe", LocalDate.of(2022, 1, 15), address);

		// Mock the behaviour of the repository
		when(mockEmployeeRepository.findById(id)).thenReturn(Optional.of(expectedEmployee));

		// Call the method under test
		Employee actualEmployee = employeeService.getEmployee(id);

		// Verify the result
		assertEquals(expectedEmployee, actualEmployee);
	}
	

	  @Test
	    public void testSearchEmployees() {
	        // Arrange
	        String searchInput = "John Doe";
	        List<Employee> expectedEmployees = new ArrayList<>();
	        expectedEmployees.add(new Employee("John", "Doe", LocalDate.of(2022, 1, 15), "123 Main St"));
	        expectedEmployees.add(new Employee("Jane", "Doe", LocalDate.of(2022, 1, 15), "123 Main St"));
	        when(mockEmployeeRepository.findByFullNameContainsIgnoreCase(searchInput)).thenReturn(expectedEmployees);
	        when(mockEmployeeRepository.findByFirstNameContainsIgnoreCase("John")).thenReturn(List.of(expectedEmployees.get(0)));
	        when(mockEmployeeRepository.findByLastNameContainsIgnoreCase("Doe")).thenReturn(expectedEmployees);

	        // Act
	        List<Employee> actualEmployees = employeeService.searchEmployees(searchInput);

	        // Assert
	        Assertions.assertEquals(expectedEmployees.size(), actualEmployees.size());
	        Assertions.assertTrue(actualEmployees.containsAll(expectedEmployees));
	        Assertions.assertTrue(expectedEmployees.containsAll(actualEmployees));
	        verify(mockEmployeeRepository, times(1)).findByFullNameContainsIgnoreCase(searchInput);
	        verify(mockEmployeeRepository, times(1)).findByFirstNameContainsIgnoreCase("John");
	        verify(mockEmployeeRepository, times(1)).findByLastNameContainsIgnoreCase("Doe");
	        verifyNoMoreInteractions(mockEmployeeRepository);
	    }

}
