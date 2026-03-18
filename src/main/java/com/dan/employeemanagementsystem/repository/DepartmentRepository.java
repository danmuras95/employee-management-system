package com.dan.employeemanagementsystem.repository;

import com.dan.employeemanagementsystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    // Find Department by Name
    Optional<Department> findByName(String name);
    // Find Departments by Location
    List<Department> findByLocation(String location);
    // Check if Department exists
    boolean existsByName(String name);
}
