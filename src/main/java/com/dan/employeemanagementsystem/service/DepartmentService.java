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
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Department not found"));
    }

    // Get Department DTO by ID
    public DepartmentDTO getDepartmentByIdAsDTO(int departmentId) {
        Department department = getDepartmentById(departmentId);
        return DepartmentMapper.convertToDTO(department);
    }

    // Get Department by Name
    public Department getDepartmentByName(String departmentName) {
        return departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Department not found"));
    }

    // Get Department DTO by Name
    public DepartmentDTO getDepartmentByNameAsDTO(String departmentName) {
        Department department = getDepartmentByName(departmentName);
        return DepartmentMapper.convertToDTO(department);
    }

    // Create Department
    @Transactional
    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        Department department = DepartmentMapper.convertToEntity(dto);
        return DepartmentMapper.convertToDTO(departmentRepository.save(department)); // save to DB then convert to DTO and return
    }

    // Update Department
    @Transactional
    public DepartmentDTO updateDepartment(int departmentId, DepartmentDTO dto) {
        Department department = getDepartmentById(departmentId);
        DepartmentMapper.updateEntityFromDTO(department, dto);
        return DepartmentMapper.convertToDTO(departmentRepository.save(department));
    }

    // Get all Departments
    public List<DepartmentDTO> getDepartments() {

        List<Department> departments = departmentRepository.findAll();  // Fetch all Department from DB

        return departments.stream()
                .map(DepartmentMapper::convertToDTO)
                .toList();
    }

    // Delete Department
    public void deleteDepartment(int departmentId) {

        Department department = getDepartmentById(departmentId);
        boolean hasEmployees = employeeRepository.existsByDepartmentId(departmentId);
        if (hasEmployees) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete department: employees still belong to it");
        }
        departmentRepository.delete(department);
    }
}
