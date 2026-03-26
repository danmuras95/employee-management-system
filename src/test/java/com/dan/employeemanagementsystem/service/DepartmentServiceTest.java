package com.dan.employeemanagementsystem.service;

import com.dan.employeemanagementsystem.dto.DepartmentDTO;
import com.dan.employeemanagementsystem.entity.Department;
import com.dan.employeemanagementsystem.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import com.dan.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DepartmentService departmentService;

    // ---------------------- GET BY ID ----------------------
    @Test
    void getDepartmentById_existingDepartment_returnsDepartment() {
        Department department = new Department("HR", "Floor 7");
        department.setId(1);
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartmentById(1);

        assertNotNull(result);
        assertEquals("HR", result.getName());
        verify(departmentRepository).findById(1);
    }

    @Test
    void getDepartmentById_nonExistingDepartment_throwsException() {
        when(departmentRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> departmentService.getDepartmentById(1)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Department not found", exception.getReason());
        verify(departmentRepository).findById(1);
    }

    // ---------------------- GET BY NAME ----------------------
    @Test
    void getDepartmentByName_existingDepartment_returnsDepartment() {
        Department department = new Department("IT", "Floor 1");
        department.setId(1);
        when(departmentRepository.findByName("IT")).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartmentByName("IT");

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("IT", result.getName());
        verify(departmentRepository).findByName("IT");
    }

    @Test
    void getDepartmentByName_nonExistingDepartment_throwsException() {
        when(departmentRepository.findByName("Finance")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> departmentService.getDepartmentByName("Finance")
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Department not found", exception.getReason());
        verify(departmentRepository).findByName("Finance");
    }

    // ---------------------- CREATE DEPARTMENT ----------------------
    @Test
    void createDepartment_success() {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName("Finance");
        dto.setLocation("Floor 6");

        Department savedDepartment = new Department("Finance", "Floor 6");
        savedDepartment.setId(1);

        when(departmentRepository.save(any(Department.class))).thenReturn(savedDepartment);

        DepartmentDTO result = departmentService.createDepartment(dto);

        assertNotNull(result);
        assertEquals("Finance", result.getName());
        assertEquals("Floor 6", result.getLocation());
        verify(departmentRepository).save(any(Department.class));
    }

    // ---------------------- UPDATE DEPARTMENT ----------------------
    @Test
    void updateDepartment_existingDepartment_success() {
        Department existing = new Department("HR", "Floor 7");
        existing.setId(1);

        DepartmentDTO dto = new DepartmentDTO();
        dto.setName("HR Updated");
        dto.setLocation("Floor 8");

        when(departmentRepository.findById(1)).thenReturn(Optional.of(existing));
        when(departmentRepository.save(existing)).thenReturn(existing);

        DepartmentDTO result = departmentService.updateDepartment(1, dto);

        assertNotNull(result);
        assertEquals("HR Updated", result.getName());
        verify(departmentRepository).findById(1);
        verify(departmentRepository).save(existing);
    }

    // ---------------------- DELETE DEPARTMENT ----------------------
    @Test
    void deleteDepartment_existingDepartmentWithoutEmployees_deletesSuccessfully() {
        Department department = new Department("IT", "Floor 1");
        department.setId(1);
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
        when(employeeRepository.existsByDepartmentId(1)).thenReturn(false);

        departmentService.deleteDepartment(1);

        verify(departmentRepository).delete(department);
        verify(employeeRepository).existsByDepartmentId(1);
    }

    @Test
    void deleteDepartment_withEmployees_throwsException() {
        Department department = new Department("IT", "Floor 1");
        department.setId(1);
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
        when(employeeRepository.existsByDepartmentId(1)).thenReturn(true);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> departmentService.deleteDepartment(1)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Cannot delete department: employees still belong to it", exception.getReason());
        verify(departmentRepository, never()).delete(any());
    }

    @Test
    void getDepartments_returnsListOfDepartments() {
        Department dep1 = new Department("HR", "Floor 7");
        Department dep2 = new Department("IT", "Floor 1");
        when(departmentRepository.findAll()).thenReturn(List.of(dep1, dep2));

        List<DepartmentDTO> result = departmentService.getDepartments();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("HR", result.get(0).getName());
        assertEquals("IT", result.get(1).getName());
        verify(departmentRepository).findAll();
    }
}

