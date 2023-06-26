package com.fdmgroup.employee.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fdmgroup.employee.model.Employee;
import com.fdmgroup.employee.service.EmployeeService;

import static org.mockito.ArgumentMatchers.anyString;




import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

	@Autowired
	EmployeeController employeeController;
	@Autowired
	MockMvc mockMvc;

	@MockBean
	EmployeeService mockEmployeeService;
	@MockBean
	Employee mockEmployee;

	// Tests if correct service method is called for each request
	@Test
	void test_GETrequestToShowEmployees_callsListAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/show-employees"));
		verify(mockEmployeeService).listAll();
	}

	@Test
	void test_POSTrequestToSubmitNewEmployee_callsCreateEmployee() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/submit-new-employee").flashAttr("employee", mockEmployee));
		verify(mockEmployeeService).createEmployee(mockEmployee);
	}

	// Tests if correct page is displayed
	@Test
	void test_GETrequestToShowEmployees_displaysShowEmployeesPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/show-employees"))
				.andExpect(MockMvcResultMatchers.view().name("showEmployees"));
	}

	@Test
	void test_POSTrequestHandleSubmitNewEmployeeRedirects_toShowEmployees_whenEmployeeSavedSuccessfully() throws Exception {
		when(mockEmployeeService.createEmployee(mockEmployee)).thenReturn(mockEmployee);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/submit-new-employee").flashAttr("employee", mockEmployee))
			.andExpect(MockMvcResultMatchers
					.view()
					.name("redirect:/show-employees"));
	}

	@Test
	void test_POSTrequestHandleSubmitNewEmployeeRedirects_toCreateEmployee_whenEmployeeNotPersisted() throws Exception {
		when(mockEmployeeService.createEmployee(mockEmployee)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/submit-new-employee").flashAttr("game", mockEmployee))
			.andExpect(MockMvcResultMatchers
					.view()
					.name("redirect:/create-employee"));
		
	}

	@Test
	void test_GETrequestToDisplayCreateEmployee_displaysCreateEmployeePage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/create-employee"))
				.andExpect(MockMvcResultMatchers.view().name("createEmployee"));
	}

	@Test
	void test_POSTrequestHandleSearchByAddress_callsListByAddress() throws Exception {
		String address = "123 Main St";

		mockMvc.perform(MockMvcRequestBuilders.post("/search-by-address").param("address", address))
				.andExpect(MockMvcResultMatchers.model().attributeExists("employees"));

		verify(mockEmployeeService).listByAddress(address);
	}

	@Test
	void test_POSTrequestHandleSearchByAddress_displaysShowEmployeesPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/search-by-address").param("address", "Street"))
				.andExpect(MockMvcResultMatchers.view().name("showEmployees"));
	}

	//anyString();
	@Test
	void test_POSTrequestHandleSearchByAddress_noEmployeesFound_displaysErrorPage() throws Exception {
	    when(mockEmployeeService.listByAddress(anyString())).thenReturn(Collections.emptyList());

	    mockMvc.perform(MockMvcRequestBuilders.post("/search-by-address")
	            .param("address", anyString()))
	            .andExpect(MockMvcResultMatchers.view().name("errorPage"));
	}

	@Test
	void test_POSTrequestHandleSearchByFirstName_callsFindByFirstName() throws Exception {
		String firstName = "John";

		mockMvc.perform(MockMvcRequestBuilders.post("/search-by-name").param("firstName", firstName))
				.andExpect(MockMvcResultMatchers.model().attributeExists("employees"));

		verify(mockEmployeeService).findByFirstName(firstName);
	}

	@Test
	void test_POSTrequestHandleSearchByFirstName_displaysShowEmployeesPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/search-by-name").param("firstName", anyString()))
				.andExpect(MockMvcResultMatchers.view().name("showEmployees"));
	}

	@Test
	void test_POSTrequestHandleSearchByFirstName_noEmployeesFound_displaysErrorPage() throws Exception {
	    when(mockEmployeeService.findByFirstName(anyString())).thenReturn(Collections.emptyList());

	    mockMvc.perform(MockMvcRequestBuilders.post("/search-by-name")
	            .param("firstName", anyString()))
	            .andExpect(MockMvcResultMatchers.view().name("errorPage"));
	}

	@Test
	void test_POSTrequestHandleSearchByLastName_callsFindByLastName() throws Exception {
		String lastName = "Doe";

		mockMvc.perform(MockMvcRequestBuilders.post("/search-by-last-name").param("lastName", lastName))
				.andExpect(MockMvcResultMatchers.model().attributeExists("employees"));

		verify(mockEmployeeService).findByLastName(lastName);
	}
	
//	@Test
//	void test_POSTrequestHandleSearchByLastName_displaysShowEmployeesPage() throws Exception {
//	    mockMvc.perform(MockMvcRequestBuilders.post("/search-by-last-name")
//	            .param("lastName", Matchers.anyString()))
//	            .andExpect(MockMvcResultMatchers.view().name("showEmployees"));
//	}
	

	@Test
	void test_POSTrequestHandleSearchByLastName_noEmployeesFound_displaysErrorPage() throws Exception {
	    when(mockEmployeeService.findByLastName(anyString())).thenReturn(Collections.emptyList());

	    mockMvc.perform(MockMvcRequestBuilders.post("/search-by-last-name")
	            .param("lastName", anyString()))
	            .andExpect(MockMvcResultMatchers.view().name("errorPage"));
	}

	@Test
	void test_POSTrequestHandleSearchByFullName_callsFindByFullName() throws Exception {
		String fullName = "John Doe";

		mockMvc.perform(MockMvcRequestBuilders.post("/search-by-full-name").param("fullName", fullName))
				.andExpect(MockMvcResultMatchers.model().attributeExists("employees"));

		verify(mockEmployeeService).findByFullName("John", "Doe");
	}

//	@Test
//	void test_POSTrequestHandleSearchByFullName_displaysShowEmployeesPage() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.post("/search-by-full-name").param("fullName", anyString()))
//				.andExpect(MockMvcResultMatchers.view().name("showEmployees"));
//	}

//	@Test
//	void test_POSTrequestHandleSearchByFullName_noEmployeesFound_displaysErrorPage() throws Exception {
//	    when(mockEmployeeService.findByFullName(anyString(), anyString())).thenReturn(Collections.emptyList());
//
//	    mockMvc.perform(MockMvcRequestBuilders.post("/search-by-full-name")
//	            .param("fullName", anyString()))
//	            .andExpect(MockMvcResultMatchers.view().name("errorPage"));
//	}

	@Test
	void test_POSTrequestHandleSearchEmployees_callsSearchEmployees() throws Exception {
		String searchInput = "John Doe";

		mockMvc.perform(MockMvcRequestBuilders.post("/search-employees").param("searchInput", searchInput));
				

		verify(mockEmployeeService).searchEmployees(searchInput);
	}
	

	@Test
	void test_POSTrequestHandleSearchEmployees_displaysShowEmployeesPage() throws Exception {
		String searchInput = "alex";
		mockMvc.perform(MockMvcRequestBuilders.post("/search-employees").param("searchInput", searchInput))
				.andExpect(MockMvcResultMatchers.view().name("showEmployees"));
	}

	@Test
	void test_POSTrequestHandleSearchEmployees_noEmployeesFound_displaysErrorPage() throws Exception {
	    when(mockEmployeeService.searchEmployees(anyString())).thenReturn(Collections.emptyList());

	    mockMvc.perform(MockMvcRequestBuilders.post("/search-employees")
	            .param("searchInput", anyString()))
	            .andExpect(MockMvcResultMatchers.view().name("errorPage"));
	}

}
