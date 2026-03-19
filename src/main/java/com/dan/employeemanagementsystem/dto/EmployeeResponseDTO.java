package com.dan.employeemanagementsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EmployeeResponseDTO {

    private Integer id;
    private String fullName;
    private String email;
    private String role;
    private String departmentName;
    private String managerFullName;
    private BigDecimal salary;
    private LocalDateTime createdAt;

    public EmployeeResponseDTO() {
    }

    public EmployeeResponseDTO(Integer id, String fullName, String email, String role, String departmentName, String managerFullName, BigDecimal salary, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.departmentName = departmentName;
        this.managerFullName = managerFullName;
        this.salary = salary;
        this.createdAt = createdAt;
    }

    public Integer getId() {return id;}
    public String getFullName() {return fullName;}
    public String getEmail() {return email;}
    public String getRole() {return role;}
    public String getDepartmentName() {return departmentName;}
    public String getManagerFullName() {return managerFullName;}
    public BigDecimal getSalary() {return salary;}
    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setId(Integer id) {this.id = id;}
    public void setFullName(String fullName) {this.fullName = fullName;}
    public void setEmail(String email) {this.email = email;}
    public void setRole(String role) {this.role = role;}
    public void setDepartmentName(String departmentName) {this.departmentName = departmentName;}
    public void setManagerFullName(String managerFullName) {this.managerFullName = managerFullName;}
    public void setSalary(BigDecimal salary) {this.salary = salary;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
