package com.dan.employeemanagementsystem.service;

import com.dan.employeemanagementsystem.dto.EmployeeRequestDTO;
import com.dan.employeemanagementsystem.dto.EmployeeResponseDTO;
import com.dan.employeemanagementsystem.dto.PaginatedResponseDTO;
import com.dan.employeemanagementsystem.entity.Department;
import com.dan.employeemanagementsystem.entity.Employee;
import com.dan.employeemanagementsystem.mapper.EmployeeMapper;
import com.dan.employeemanagementsystem.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
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
        log.debug("Fetching employee with ID: {}", employeeid);
        return employeeRepository.findById(employeeid)
                .orElseThrow(() -> {
                    log.error("Employee with ID: {} not found", employeeid);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
                });
    }

    public Employee getManagerById(Integer managerId) {
        log.debug("Fetching manager with ID: {}", managerId);
        return managerId != null ? getEmployeeById(managerId) : null;
    }

    private void validateEmailForCreate(String email) {
        log.debug("Validating email for creation: {}", email);
        if (employeeRepository.existsByEmail(email)) {
            log.error("Email already taken: {}", email);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email is already taken. Please use a different email."
            );
        }
    }

    private void validateEmailUpdate(String currentEmail, String newEmail) {
        log.debug("Validating email update from {} to {}", currentEmail, newEmail);
        if (!newEmail.equals(currentEmail) && employeeRepository.existsByEmail(newEmail)) {
            log.error("Email update failed, new email already taken: {}", newEmail);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email is already taken. Please use a different email."
            );
        }
    }

    // Get Employee DTO by ID
    public EmployeeResponseDTO getEmployeeByIdAsDTO(int employeeId) {
        log.debug("Fetching employee DTO for ID: {}", employeeId);
        Employee employee = getEmployeeById(employeeId);
        return EmployeeMapper.convertToDTO(employee);
    }

    // Create Employee
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        log.info("Creating employee with email: {}", dto.getEmail());
        validateEmailForCreate(dto.getEmail());
        Department department = departmentService.getDepartmentById(dto.getDepartmentId());
        Employee manager = getManagerById(dto.getManagerId());
        Employee employee = EmployeeMapper.convertToEntity(dto,department,manager);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee created with ID: {}", savedEmployee.getId());
        return EmployeeMapper.convertToDTO(savedEmployee);
    }

    // Update Employee
    @Transactional
    public EmployeeResponseDTO updateEmployee(int employeeId, EmployeeRequestDTO dto) {
        log.info("Updating employee with ID: {}", employeeId);
        Employee employee = getEmployeeById(employeeId);
        validateEmailUpdate(employee.getEmail(), dto.getEmail());
        Department department = departmentService.getDepartmentById(dto.getDepartmentId());
        Employee manager = getManagerById(dto.getManagerId());
        EmployeeMapper.updateEntityFromDTO(employee, dto, department, manager);
        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Employee updated with ID: {}", updatedEmployee.getId());
        return EmployeeMapper.convertToDTO(updatedEmployee);
    }

    // Get all Employees
    public PaginatedResponseDTO<EmployeeResponseDTO> getEmployees(int page, int size, String sortBy, String direction) {
        log.info("Fetching employees for page: {} with size: {}", page, size);
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        log.debug("Fetched {} employees on page {}. Total employees: {}",
                employeePage.getNumberOfElements(), page, employeePage.getTotalElements());
        List<EmployeeResponseDTO> employeeDTOs =  employeePage.getContent().stream()
                .map(EmployeeMapper::convertToDTO)
                .toList();
        return new PaginatedResponseDTO<>(employeeDTOs, employeePage.getNumber(), employeePage.getSize(),
                employeePage.getTotalElements(), employeePage.getTotalPages());
    }

    // Delete Employee
    public void deleteEmployee(int employeeId) {
        log.info("Deleting employee with ID: {}", employeeId);
        Employee employee = getEmployeeById(employeeId);
        employeeRepository.delete(employee);
        log.info("Employee with ID: {} deleted successfully", employeeId);
    }

    // Count Employees by Department ID
    public long countEmployeesInDepartment(Integer departmentId) {
        log.debug("Counting employees in department with ID: {}", departmentId);
        Department department = departmentService.getDepartmentById(departmentId);
        long count = employeeRepository.countByDepartmentId(departmentId);
        log.debug("Found {} employees in department with ID: {}", count, departmentId);
        return count;
    }
}
