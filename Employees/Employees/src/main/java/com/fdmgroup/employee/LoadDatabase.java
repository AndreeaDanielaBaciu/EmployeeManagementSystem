package com.fdmgroup.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fdmgroup.employee.repository.*;
import com.fdmgroup.employee.model.*;
import com.fdmgroup.employee.model.Employee;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Configuration
@Profile("!test") 
public class LoadDatabase {


	@Bean
	CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {
		return args -> {
			Employee employee1 = new Employee("Daliah", "Wehbe", LocalDate.of(2019, 6, 17), "22 A Street London England");
			Employee employee2 = new Employee("Anya", "Wehbe",  LocalDate.of(2019, 6, 17), "23 B Street Manchester England");
			Employee employee3 = new Employee("Anastasia", "Alexandrovna",  LocalDate.of(2019, 6, 17), "24 C Street Moscow Russia");
			Employee employee4 = new Employee("Madalina", "Acasandrei",  LocalDate.of(2019, 6, 17), "25 D Street Brasov Romania");
			Employee employee5 = new Employee("Robert", "Daniel",  LocalDate.of(2019, 6, 17), "26 E Street Cluj-Napoca Romania");
		
			employeeRepository.save(employee1);
			employeeRepository.save(employee2);
			employeeRepository.save(employee3);
			employeeRepository.save(employee4);
			employeeRepository.save(employee5);
		};
	}
}
