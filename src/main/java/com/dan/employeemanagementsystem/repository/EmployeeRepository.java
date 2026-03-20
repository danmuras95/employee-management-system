package com.dan.employeemanagementsystem.repository;

import com.dan.employeemanagementsystem.entity.Employee;
import com.dan.employeemanagementsystem.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Find Employee by Email
    Optional<Employee> findByEmail(String email);
    // Find Employees by Department ID
    List<Employee> findByDepartmentId(Integer departmentId);
    // With pagination
    //Page<Employee> findByDepartmentId(Integer departmentId, Pageable pageable);
    // Find Employees by Role
    List<Employee> findByRole(Role role);
    // Find Employees by Manager ID
    List<Employee> findByManagerId(Integer managerId);
    // Find Employees by Last Name
    List<Employee> findByLastName(String lastName);
    // Find Employees by Department Name
    List<Employee> findByDepartmentName(String name);
    // Check if Employee exists by Email
    boolean existsByEmail(String email);
    // Check if Employee exists by Department ID
    boolean existsByDepartmentId(Integer departmentId);
    // COUNT EMPLOYEES IN A DEPARTMENT
    long countByDepartmentId(Integer departmentId);
}
