package com.dan.employeemanagementsystem.entity;

import com.dan.employeemanagementsystem.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    @Email
    @NotBlank
    private String email;

    @NotNull(message = "Role must be provided")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Column(nullable = false)
    @Min(10_000)
    @Max(100_000)
    private BigDecimal salary;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Employee() {} // JPA

    public Employee(String firstName, String lastName, String email, Role role, Department department, Employee manager, BigDecimal salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.department = department;
        this.manager = manager;
        this.salary = salary;
    }

    public Integer getId() {return id;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public Role getRole() {return role;}
    public Department getDepartment() {return department;}
    public Employee getManager() {return manager;}
    public BigDecimal getSalary() {return salary;}
    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setId(Integer id) {this.id = id;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setEmail(String email) {this.email = email;}
    public void setRole(Role role) {this.role = role;}
    public void setDepartment(Department department) {this.department = department;}
    public void setManager(Employee manager) {this.manager = manager;}
    public void setSalary(BigDecimal salary) {this.salary = salary;}
}
