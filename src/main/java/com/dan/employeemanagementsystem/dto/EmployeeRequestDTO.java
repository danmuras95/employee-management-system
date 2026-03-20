package com.dan.employeemanagementsystem.dto;

import com.dan.employeemanagementsystem.enums.Role;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class EmployeeRequestDTO {

    @NotBlank(message = "First name must not be blank")
    private String firstName;
    @NotBlank(message = "Last name must not be blank")
    private String lastName;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must have an Email format")
    private String email;
    @NotNull(message = "Role must not be null")
    private Role role;
    @NotNull(message = "Department Id must not be null")
    private Integer departmentId;
    private Integer managerId; // optional
    @NotNull(message = "Salary must not be null")
    @Positive(message = "Salary must be positive")
    private BigDecimal salary;

    public EmployeeRequestDTO() {
    }

    public EmployeeRequestDTO(String firstName, String lastName, String email, Role role, Integer departmentId, Integer managerId, BigDecimal salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.departmentId = departmentId;
        this.managerId = managerId;
        this.salary = salary;
    }

    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public Role getRole() {return role;}
    public Integer getDepartmentId() {return departmentId;}
    public Integer getManagerId() {return managerId;}
    public BigDecimal getSalary() {return salary;}

    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setEmail(String email) {this.email = email;}
    public void setRole(Role role) {this.role = role;}
    public void setDepartmentId(Integer departmentId) {this.departmentId = departmentId;}
    public void setManagerId(Integer managerId) {this.managerId = managerId;}
    public void setSalary(BigDecimal salary) {this.salary = salary;}
}
