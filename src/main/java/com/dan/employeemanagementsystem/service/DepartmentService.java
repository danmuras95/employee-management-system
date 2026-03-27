package com.dan.employeemanagementsystem.service;

import com.dan.employeemanagementsystem.dto.DepartmentDTO;
import com.dan.employeemanagementsystem.entity.Department;
import com.dan.employeemanagementsystem.mapper.DepartmentMapper;
import com.dan.employeemanagementsystem.repository.DepartmentRepository;
import com.dan.employeemanagementsystem.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    // Get Department by ID
    public Department getDepartmentById(int departmentId) {
        log.debug("Debug log: department id = {}", departmentId);
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> {
                    log.error("Department not found with id {}", departmentId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
                });
    }

    // Get Department DTO by ID
    public DepartmentDTO getDepartmentByIdAsDTO(int departmentId) {
        log.debug("Fetching department DTO with ID: {}", departmentId);
        Department department = getDepartmentById(departmentId);
        return DepartmentMapper.convertToDTO(department);
    }

    // Get Department by Name
    public Department getDepartmentByName(String departmentName) {
        log.debug("Fetching department with name: {}", departmentName);
        return departmentRepository.findByName(departmentName)
                .orElseThrow(() -> {
                    log.error("Department not found with name: {}", departmentName);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
                });
    }

    // Get Department DTO by Name
    public DepartmentDTO getDepartmentByNameAsDTO(String departmentName) {
        log.debug("Fetching department DTO with name: {}", departmentName);
        Department department = getDepartmentByName(departmentName);
        return DepartmentMapper.convertToDTO(department);
    }

    // Create Department
    @Transactional
    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        log.info("Creating department with name: {}", dto.getName());
        Department department = DepartmentMapper.convertToEntity(dto);
        Department savedDepartment = departmentRepository.save(department);
        log.info("Department created with ID: {}", savedDepartment.getId());
        return DepartmentMapper.convertToDTO(savedDepartment);
    }

    // Update Department
    @Transactional
    public DepartmentDTO updateDepartment(int departmentId, DepartmentDTO dto) {
        log.info("Updating department with ID: {}", departmentId);
        Department department = getDepartmentById(departmentId);
        DepartmentMapper.updateEntityFromDTO(department, dto);
        Department updatedDepartment = departmentRepository.save(department);
        log.info("Department updated with ID: {}", updatedDepartment.getId());
        return DepartmentMapper.convertToDTO(updatedDepartment);
    }

    // Get all Departments
    public List<DepartmentDTO> getDepartments() {
        log.debug("Fetching all departments");
        List<Department> departments = departmentRepository.findAll();
        log.debug("Fetched {} departments", departments.size());
        return departments.stream()
                .map(DepartmentMapper::convertToDTO)
                .toList();
    }

    // Delete Department
    public void deleteDepartment(int departmentId) {
        log.info("Attempting to delete department with ID: {}", departmentId);
        Department department = getDepartmentById(departmentId);
        boolean hasEmployees = employeeRepository.existsByDepartmentId(departmentId);
        if (hasEmployees) {
            log.warn("Department with ID: {} cannot be deleted, employees still belong to it", departmentId);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete department: employees still belong to it");
        }
        log.info("Department with ID: {} deleted successfully", departmentId);
        departmentRepository.delete(department);
    }
}
