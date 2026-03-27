package com.dan.employeemanagementsystem.service;

import com.dan.employeemanagementsystem.dto.EmployeeRequestDTO;
import com.dan.employeemanagementsystem.dto.EmployeeResponseDTO;
import com.dan.employeemanagementsystem.entity.Department;
import com.dan.employeemanagementsystem.entity.Employee;
import com.dan.employeemanagementsystem.enums.Role;
import com.dan.employeemanagementsystem.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private EmployeeService employeeService;

    // ---------------------- GET BY ID ----------------------
    @Test
    void getEmployeeById_existingEmployee_returnsEmployee() {
        Department department = new Department("HR", "Floor 7");
        department.setId(1);

        Employee employee = new Employee("John", "Doe", "john.doe@example.com",
                Role.MANAGER, department, null, BigDecimal.valueOf(75000));
        employee.setId(1);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());

        verify(employeeRepository).findById(1);
    }

    @Test
    void getEmployeeById_nonExistingEmployee_throwsException() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class,
                () -> employeeService.getEmployeeByIdAsDTO(1));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Employee not found", thrown.getReason());

        verify(employeeRepository).findById(1);
    }

    // ---------------------- CREATE ----------------------
    @Test
    void createEmployee_success() {
        Department department = new Department("IT", "Floor 1");
        department.setId(1);

        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO(
                "John", "Doe", "john.doe@example.com",
                Role.MANAGER, 1, null, BigDecimal.valueOf(50000)
        );

        Employee employee = new Employee(
                "John", "Doe", "john.doe@example.com",
                Role.MANAGER, department, null, BigDecimal.valueOf(50000)
        );
        employee.setId(1);

        when(departmentService.getDepartmentById(1)).thenReturn(department);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponseDTO result = employeeService.createEmployee(requestDTO);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John Doe", result.getFullName());
        assertEquals("john.doe@example.com", result.getEmail());

        verify(departmentService).getDepartmentById(1);
        verify(employeeRepository).save(any(Employee.class));
    }

    // ---------------------- GET ALL ----------------------
    @Test
    void getEmployees_returnsListOfEmployeeDTOs() {
        Department department = new Department("IT", "Floor 1");
        department.setId(1);

        Employee employee1 = new Employee("John", "Doe", "john.doe@example.com", Role.MANAGER,
                department, null, BigDecimal.valueOf(50000));
        employee1.setId(1);

        Employee employee2 = new Employee("Jane", "Smith", "jane.smith@example.com", Role.MANAGER,
                department, null, BigDecimal.valueOf(60000));
        employee2.setId(2);

        when(employeeRepository.findAll()).thenReturn(List.of(employee1, employee2));

        List<EmployeeResponseDTO> result = employeeService.getEmployees();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
        assertEquals("Jane Smith", result.get(1).getFullName());
        assertEquals("john.doe@example.com", result.get(0).getEmail());
        assertEquals("jane.smith@example.com", result.get(1).getEmail());

        verify(employeeRepository).findAll();
    }

    @Test
    void getEmployees_noEmployees_returnsEmptyList() {
        when(employeeRepository.findAll()).thenReturn(List.of());

        List<EmployeeResponseDTO> result = employeeService.getEmployees();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(employeeRepository).findAll();
    }

    // ---------------------- DELETE ----------------------
    @Test
    void deleteEmployee_existingEmployee_deletesSuccessfully() {
        Department department = new Department("IT", "Floor 1");
        department.setId(1);

        Employee employee = new Employee("John", "Doe", "john.doe@example.com",
                Role.MANAGER, department, null, BigDecimal.valueOf(50000));
        employee.setId(1);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1);

        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void deleteEmployee_nonExistingEmployee_throwsException() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> employeeService.deleteEmployee(1));

        verify(employeeRepository, never()).delete(any());
    }

    // ---------------------- COUNT BY DEPARTMENT ----------------------
    @Test
    void countEmployeesInDepartment_existingDepartment_returnsCount() {
        Department department = new Department("HR", "Floor 7");
        department.setId(1);

        when(departmentService.getDepartmentById(1)).thenReturn(department);
        when(employeeRepository.countByDepartmentId(1)).thenReturn(2L);

        long employeeCount = employeeService.countEmployeesInDepartment(1);

        assertEquals(2L, employeeCount);

        verify(departmentService).getDepartmentById(1);
        verify(employeeRepository).countByDepartmentId(1);
    }

    @Test
    void countEmployeesInDepartment_nonExistingDepartment_throwsException() {
        when(departmentService.getDepartmentById(1))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));

        assertThrows(ResponseStatusException.class,
                () -> employeeService.countEmployeesInDepartment(1));

        verify(employeeRepository, never()).countByDepartmentId(anyInt());
    }

    // ---------------------- UPDATE ----------------------
    @Test
    void updateEmployee_success() {
        int employeeId = 1;

        Department department = new Department();
        Employee manager = new Employee();
        manager.setRole(Role.MANAGER);

        Employee existingEmployee = new Employee();
        existingEmployee.setRole(Role.EMPLOYEE);
        existingEmployee.setDepartment(department);
        existingEmployee.setEmail("old@email.com");

        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setRole(Role.EMPLOYEE);
        requestDTO.setEmail("new@email.com");
        requestDTO.setDepartmentId(2);
        requestDTO.setManagerId(3);

        when(employeeRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(departmentService.getDepartmentById(2)).thenReturn(department);
        when(employeeRepository.findById(3)).thenReturn(Optional.of(manager));
        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);

        EmployeeResponseDTO result = employeeService.updateEmployee(employeeId, requestDTO);

        assertNotNull(result);
        assertEquals("new@email.com", result.getEmail());

        verify(employeeRepository).findById(employeeId);
        verify(departmentService).getDepartmentById(2);
        verify(employeeRepository).save(existingEmployee);
    }

    @Test
    void updateEmployee_emailAlreadyExists_shouldThrowException() {
        int employeeId = 1;

        Employee existingEmployee = new Employee();
        existingEmployee.setId(employeeId);
        existingEmployee.setEmail("old@email.com");

        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setEmail("existing@email.com");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> employeeService.updateEmployee(employeeId, dto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Email is already taken. Please use a different email.", exception.getReason());

        verify(employeeRepository, never()).save(any());
    }
}