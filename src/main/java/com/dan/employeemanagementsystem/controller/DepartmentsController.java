package com.dan.employeemanagementsystem.controller;

import com.dan.employeemanagementsystem.dto.DepartmentDTO;
import com.dan.employeemanagementsystem.service.DepartmentService;
import com.dan.employeemanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentsController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentsController(DepartmentService departmentService,EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<DepartmentDTO> getDepartments() {
        return departmentService.getDepartments();
    }

    @GetMapping("/{departmentId}")
    public DepartmentDTO getDepartmentById(@PathVariable int departmentId) {
        return departmentService.getDepartmentByIdAsDTO(departmentId);
    }

    @GetMapping("/name/{departmentName}")
    public DepartmentDTO getDepartmentByName(@PathVariable String departmentName) {
        return departmentService.getDepartmentByNameAsDTO(departmentName);
    }

    @GetMapping("/{departmentId}/employees/count")
    public long countEmployeesInDepartment(@PathVariable Integer departmentId) {
        return employeeService.countEmployeesInDepartment(departmentId);
    }

    @PostMapping
    public DepartmentDTO createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }

    @PutMapping("/{departmentId}")
    public DepartmentDTO updateDepartment(@PathVariable int departmentId,
                                          @Valid @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.updateDepartment(departmentId, departmentDTO);
    }

    @DeleteMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable int departmentId) {
        departmentService.deleteDepartment(departmentId);
    }
}
