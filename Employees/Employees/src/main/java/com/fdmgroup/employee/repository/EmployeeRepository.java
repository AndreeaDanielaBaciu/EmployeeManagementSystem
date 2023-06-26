package com.fdmgroup.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdmgroup.employee.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	
	//
	List<Employee> findByAddressContainsIgnoreCase(String address);
	//
	List<Employee> findByFirstNameContainsIgnoreCase(String firstName);
	//
	List<Employee> findByLastNameContainsIgnoreCase(String lastName);
	//
	//
	List<Employee> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);
	//
	//
	@Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE %:searchTerm% OR LOWER(e.lastName) LIKE %:searchTerm%")
	List<Employee> findByFullNameContainsIgnoreCase(@Param("searchTerm") String searchTerm);

}
