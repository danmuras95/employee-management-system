package com.dan.employeemanagementsystem.service;

import com.dan.employeemanagementsystem.dto.LeaveRequestRequestDTO;
import com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO;
import com.dan.employeemanagementsystem.dto.PaginatedResponseDTO;
import com.dan.employeemanagementsystem.entity.Employee;
import com.dan.employeemanagementsystem.entity.LeaveRequest;
import com.dan.employeemanagementsystem.enums.LeaveStatus;
import com.dan.employeemanagementsystem.repository.LeaveRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveRequestServiceTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private LeaveRequestService leaveRequestService;

    // ---------------------- GET BY ID ----------------------
    @Test
    void getLeaveRequestById_existing_returnsLeaveRequest() {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setId(1);

        when(leaveRequestRepository.findById(1)).thenReturn(Optional.of(leaveRequest));

        LeaveRequest result = leaveRequestService.getLeaveRequestById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(leaveRequestRepository).findById(1);
    }

    @Test
    void getLeaveRequestById_nonExisting_throwsException() {
        when(leaveRequestRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> leaveRequestService.getLeaveRequestById(1));
    }

    // ---------------------- GET WITH FILTERS ----------------------
    @Test
    void getLeaveRequests_withResults_returnsPaginatedResponse() {
        List<LeaveRequestResponseDTO> list = List.of(new LeaveRequestResponseDTO());

        Page<LeaveRequestResponseDTO> page = new PageImpl<>(list);

        when(leaveRequestRepository.findByOptionalTypeAndStatus(any(), any(), any(Pageable.class))).thenReturn(page);

        PaginatedResponseDTO<LeaveRequestResponseDTO> result = leaveRequestService.getLeaveRequests(
                null, null, 0, 10, "id", "asc");

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(leaveRequestRepository).findByOptionalTypeAndStatus(any(), any(), any(Pageable.class));
    }

    @Test
    void getLeaveRequests_noResults_returnsEmptyPage() {
        Page<LeaveRequestResponseDTO> emptyPage = Page.empty();

        when(leaveRequestRepository.findByOptionalTypeAndStatus(any(), any(), any(Pageable.class))).thenReturn(emptyPage);

        PaginatedResponseDTO<LeaveRequestResponseDTO> result = leaveRequestService.getLeaveRequests(
                null, null, 0, 10, "id", "asc");

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
    }

    // ---------------------- CREATE ----------------------
    @Test
    void createLeaveRequest_success() {
        Employee employee = new Employee();
        employee.setId(1);

        LeaveRequestRequestDTO dto = new LeaveRequestRequestDTO();
        dto.setEmployeeId(1);

        LeaveRequest saved = new LeaveRequest();
        saved.setEmployee(employee);

        when(leaveRequestRepository.existsOverlappingLeave(anyInt(), any(), any())).thenReturn(false);
        when(employeeService.getEmployeeById(1)).thenReturn(employee);
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(saved);

        LeaveRequestResponseDTO result = leaveRequestService.createLeaveRequest(dto);

        assertNotNull(result);
        verify(employeeService).getEmployeeById(1);
        verify(leaveRequestRepository).save(any(LeaveRequest.class));
    }

    @Test
    void createLeaveRequest_shouldThrowException_whenOverlapExists() {
        Employee employee = new Employee();
        employee.setId(1);

        LeaveRequestRequestDTO dto = new LeaveRequestRequestDTO();
        dto.setEmployeeId(1);

        when(employeeService.getEmployeeById(1)).thenReturn(employee);

        when(leaveRequestRepository.existsOverlappingLeave(anyInt(), any(), any())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                leaveRequestService.createLeaveRequest(dto)
        );

        verify(leaveRequestRepository, never()).save(any());
    }

    // ---------------------- UPDATE ----------------------
    @Test
    void updateLeaveRequest_success() {
        LeaveRequest existing = new LeaveRequest();
        existing.setId(1);
        existing.setLeaveStatus(LeaveStatus.PENDING);

        Employee employee = new Employee();
        employee.setId(1);
        existing.setEmployee(employee);

        LeaveRequestRequestDTO dto = new LeaveRequestRequestDTO();
        dto.setEmployeeId(1);
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(5));

        when(leaveRequestRepository.findById(1)).thenReturn(Optional.of(existing));
        when(leaveRequestRepository.existsOverlappingLeaveForUpdate(anyInt(), anyInt(), any(), any())).thenReturn(false);
        when(leaveRequestRepository.save(existing)).thenReturn(existing);

        LeaveRequestResponseDTO result = leaveRequestService.updateLeaveRequest(1, dto);

        assertNotNull(result);
        verify(leaveRequestRepository).findById(1);
        verify(leaveRequestRepository).save(existing);
    }

    @Test
    void updateLeaveRequest_shouldThrowException_whenOverlapExists() {
        LeaveRequest existing = new LeaveRequest();
        existing.setId(1);
        existing.setLeaveStatus(LeaveStatus.PENDING);

        Employee employee = new Employee();
        employee.setId(1);
        existing.setEmployee(employee);

        LeaveRequestRequestDTO dto = new LeaveRequestRequestDTO();
        dto.setEmployeeId(1);
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(5));

        when(leaveRequestRepository.findById(1)).thenReturn(Optional.of(existing));
        when(leaveRequestRepository.existsOverlappingLeaveForUpdate(anyInt(), anyInt(), any(), any())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                leaveRequestService.updateLeaveRequest(1, dto)
        );

        verify(leaveRequestRepository, never()).save(any());
    }

    // ---------------------- CANCEL ----------------------
    @Test
    void cancelLeaveRequest_success() {
        LeaveRequest existing = new LeaveRequest();
        existing.setLeaveStatus(LeaveStatus.PENDING);

        Employee employee = new Employee();
        employee.setId(1);
        existing.setEmployee(employee);

        when(leaveRequestRepository.findById(1)).thenReturn(Optional.of(existing));
        when(leaveRequestRepository.save(existing)).thenReturn(existing);

        LeaveRequestResponseDTO result = leaveRequestService.cancelLeaveRequest(1);

        assertNotNull(result);
        assertEquals(LeaveStatus.CANCELLED, existing.getLeaveStatus());
        verify(leaveRequestRepository).save(existing);
    }

    @Test
    void cancelLeaveRequest_alreadyCancelled_throwsException() {
        LeaveRequest existing = new LeaveRequest();
        existing.setLeaveStatus(LeaveStatus.CANCELLED);

        when(leaveRequestRepository.findById(1)).thenReturn(Optional.of(existing));

        assertThrows(ResponseStatusException.class, () -> leaveRequestService.cancelLeaveRequest(1));

        verify(leaveRequestRepository, never()).save(any());
    }
}

