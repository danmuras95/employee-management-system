package com.dan.employeemanagementsystem.dto;

import com.dan.employeemanagementsystem.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class EmployeeRequestDTO {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank @Email
    private String email;
    @NotBlank
    private Role role;
    @NotNull
    private Integer departmentId;
    private Integer managerId; // optional
    @NotNull @PositiveOrZero
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
