package com.dan.employeemanagementsystem.controller;

import com.dan.employeemanagementsystem.dto.EmployeeRequestDTO;
import com.dan.employeemanagementsystem.dto.EmployeeResponseDTO;
import com.dan.employeemanagementsystem.dto.PaginatedResponseDTO;
import com.dan.employeemanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public PaginatedResponseDTO<EmployeeResponseDTO> getEmployees(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                                  @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
                                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                                  @RequestParam(defaultValue = "asc") String direction) {
        return employeeService.getEmployees(page, size, sortBy, direction);
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponseDTO getEmployeeById(@PathVariable int employeeId) {
        return employeeService.getEmployeeByIdAsDTO(employeeId);
    }

    @PostMapping
    public EmployeeResponseDTO createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponseDTO updateEmployee(@PathVariable int employeeId,
                                              @Valid @RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService.updateEmployee(employeeId, employeeDTO);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

}
