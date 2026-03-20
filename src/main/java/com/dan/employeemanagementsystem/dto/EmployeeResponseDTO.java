package com.dan.employeemanagementsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EmployeeResponseDTO {

    private Integer id;
    private String fullName;
    private String email;
    private String role;
    private Integer departmentId;
    private String departmentName;
    private Integer managerId;
    private String managerFullName;
    private BigDecimal salary;
    private LocalDateTime createdAt;

    public EmployeeResponseDTO() {
    }

    public EmployeeResponseDTO(Integer id, String fullName, String email, String role, Integer departmentId, String departmentName, Integer managerId, String managerFullName, BigDecimal salary, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.managerId = managerId;
        this.managerFullName = managerFullName;
        this.salary = salary;
        this.createdAt = createdAt;
    }

    public Integer getId() {return id;}
    public String getFullName() {return fullName;}
    public String getEmail() {return email;}
    public String getRole() {return role;}
    public Integer getDepartmentId() {return departmentId;}
    public String getDepartmentName() {return departmentName;}
    public Integer getManagerId() {return managerId;}
    public String getManagerFullName() {return managerFullName;}
    public BigDecimal getSalary() {return salary;}
    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setId(Integer id) {this.id = id;}
    public void setFullName(String fullName) {this.fullName = fullName;}
    public void setEmail(String email) {this.email = email;}
    public void setRole(String role) {this.role = role;}
    public void setDepartmentId(Integer departmentId) {this.departmentId = departmentId;}
    public void setDepartmentName(String departmentName) {this.departmentName = departmentName;}
    public void setManagerId(Integer managerId) {this.managerId = managerId;}
    public void setManagerFullName(String managerFullName) {this.managerFullName = managerFullName;}
    public void setSalary(BigDecimal salary) {this.salary = salary;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
