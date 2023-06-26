package com.fdmgroup.employee.model;

import java.time.LocalDate;
import java.util.Objects;
//import javax.validation.constraints.NotBlank;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Employee {
	@Id
	@SequenceGenerator(name = "EMPLOYEE_ID_GEN", sequenceName = "EMPLOYEE_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEE_ID_GEN")
	private int id;

	// @NotBlank(message = "First Name is required")
	private String firstName;
	private String lastName;
	private LocalDate hireDate;
	private String address;
	

	// Empty constructor
	public Employee() {

	}

	// Constructor using all fields
	public Employee(String firstName, String lastName, LocalDate hireDate, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.hireDate = hireDate;
		this.address = address;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

	public String getFullname() {
		return firstName + " " + lastName;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(address, firstName, hireDate, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(address, other.address) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(hireDate, other.hireDate) && id == other.id
				&& Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", hireDate=" + hireDate
				+ ", address=" + address + "]";
	}

}
