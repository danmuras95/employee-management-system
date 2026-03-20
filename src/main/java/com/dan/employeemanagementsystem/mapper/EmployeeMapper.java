package com.dan.employeemanagementsystem.mapper;

import com.dan.employeemanagementsystem.dto.EmployeeRequestDTO;
import com.dan.employeemanagementsystem.dto.EmployeeResponseDTO;
import com.dan.employeemanagementsystem.entity.Department;
import com.dan.employeemanagementsystem.entity.Employee;

public class EmployeeMapper {

    public static Employee convertToEntity(EmployeeRequestDTO dto, Department department, Employee manager) {
        return new Employee(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getRole(),
                department,
                manager,
                dto.getSalary()
        );
    }

    public static EmployeeResponseDTO convertToDTO(Employee employee) {

        Employee manager = employee.getManager();

        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getFirstName() + " " + employee.getLastName(),
                employee.getEmail(),
                employee.getRole().name(),
                employee.getDepartment().getId(),
                employee.getDepartment().getName(),
                manager != null ? manager.getId() : null,
                manager != null ? manager.getFirstName() + " " + manager.getLastName() : null,
                employee.getSalary(), // optional: if the app is for internal users
                employee.getCreatedAt()
        );
    }

    public static void updateEntityFromDTO(Employee employee, EmployeeRequestDTO dto, Department department, Employee manager) {
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setRole(dto.getRole());
        employee.setDepartment(department);
        employee.setManager(manager);
        employee.setSalary(dto.getSalary());
    }
}
