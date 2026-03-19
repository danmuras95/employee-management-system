package com.dan.employeemanagementsystem.service;

import com.dan.employeemanagementsystem.dto.EmployeeRequestDTO;
import com.dan.employeemanagementsystem.dto.EmployeeResponseDTO;
import com.dan.employeemanagementsystem.entity.Department;
import com.dan.employeemanagementsystem.entity.Employee;
import com.dan.employeemanagementsystem.mapper.EmployeeMapper;
import com.dan.employeemanagementsystem.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
    }

    // Get Employee by ID
    public Employee getEmployeeById(Integer employeeid) {
        return employeeRepository.findById(employeeid)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // Get Employee DTO by ID
    public EmployeeResponseDTO getEmployeeDTOById(int employeeid) {
        Employee employee = getEmployeeById(employeeid);
        return EmployeeMapper.convertToDTO(employee);
    }

    // Create Employee
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        Department department = departmentService.getDepartmentById(dto.getDepartmentId());
        Employee manager = dto.getManagerId() != null ? getEmployeeById(dto.getManagerId()) : null;
        Employee employee = EmployeeMapper.convertToEntity(dto,department,manager);
        return EmployeeMapper.convertToDTO(employeeRepository.save(employee)); // save to DB then convert to DTO and return
    }

    // Update Employee
    @Transactional
    public EmployeeResponseDTO updateEmployee(int employeeId, EmployeeRequestDTO dto) {
        Employee employee = getEmployeeById(employeeId);
        Department department = departmentService.getDepartmentById(dto.getDepartmentId());
        Employee manager = dto.getManagerId() != null ? getEmployeeById(dto.getManagerId()) : null;
        EmployeeMapper.updateEntityFromDTO(employee, dto, department, manager);
        return EmployeeMapper.convertToDTO(employeeRepository.save(employee));
    }

    // Get all Employees
    public List<EmployeeResponseDTO> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(EmployeeMapper::convertToDTO)
                .toList();
    }

    // Delete Employee
    public void deleteEmployee(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
