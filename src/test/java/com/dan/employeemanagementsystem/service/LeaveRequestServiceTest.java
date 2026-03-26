package com.dan.employeemanagementsystem.service;

import com.dan.employeemanagementsystem.dto.LeaveRequestRequestDTO;
import com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO;
import com.dan.employeemanagementsystem.entity.Employee;
import com.dan.employeemanagementsystem.entity.LeaveRequest;
import com.dan.employeemanagementsystem.enums.LeaveStatus;
import com.dan.employeemanagementsystem.repository.LeaveRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
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
    void getLeaveRequests_withResults_returnsList() {
        List<LeaveRequestResponseDTO> list = List.of(new LeaveRequestResponseDTO());

        when(leaveRequestRepository.findByOptionalTypeAndStatus(null, null)).thenReturn(list);

        List<LeaveRequestResponseDTO> result = leaveRequestService.getLeaveRequests(null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(leaveRequestRepository).findByOptionalTypeAndStatus(null, null);
    }

    @Test
    void getLeaveRequests_noResults_throwsException() {
        when(leaveRequestRepository.findByOptionalTypeAndStatus(null, null)).thenReturn(List.of());

        assertThrows(ResponseStatusException.class, () -> leaveRequestService.getLeaveRequests(null, null));
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

        when(employeeService.getEmployeeById(1)).thenReturn(employee);
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(saved);

        LeaveRequestResponseDTO result = leaveRequestService.createLeaveRequest(dto);

        assertNotNull(result);
        verify(employeeService).getEmployeeById(1);
        verify(leaveRequestRepository).save(any(LeaveRequest.class));
    }

    // ---------------------- UPDATE ----------------------
    @Test
    void updateLeaveRequest_success() {
        LeaveRequest existing = new LeaveRequest();
        existing.setLeaveStatus(LeaveStatus.PENDING);

        Employee employee = new Employee();
        employee.setId(1);
        existing.setEmployee(employee);

        LeaveRequestRequestDTO dto = new LeaveRequestRequestDTO();

        when(leaveRequestRepository.findById(1)).thenReturn(Optional.of(existing));
        when(leaveRequestRepository.save(existing)).thenReturn(existing);

        LeaveRequestResponseDTO result = leaveRequestService.updateLeaveRequest(1, dto);

        assertNotNull(result);
        verify(leaveRequestRepository).findById(1);
        verify(leaveRequestRepository).save(existing);
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

