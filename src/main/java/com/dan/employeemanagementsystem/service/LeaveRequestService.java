package com.dan.employeemanagementsystem.service;

import com.dan.employeemanagementsystem.dto.LeaveRequestRequestDTO;
import com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO;
import com.dan.employeemanagementsystem.entity.Employee;
import com.dan.employeemanagementsystem.entity.LeaveRequest;
import com.dan.employeemanagementsystem.enums.LeaveStatus;
import com.dan.employeemanagementsystem.enums.LeaveType;
import com.dan.employeemanagementsystem.mapper.LeaveRequestMapper;
import com.dan.employeemanagementsystem.repository.LeaveRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeService employeeService;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, EmployeeService employeeService) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeService = employeeService;
    }

    // Get Leave Request by ID
    public LeaveRequest getLeaveRequestById(int leaveRequestId) {
        return leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Leave Request not found"));
    }

    // Get Leave Request DTO by ID
    public LeaveRequestResponseDTO getLeaveRequestByIdAsDTO(int leaveRequestId) {
        LeaveRequest leaveRequest = getLeaveRequestById(leaveRequestId);
        return LeaveRequestMapper.convertToDTO(leaveRequest);
    }

    // Get Leave Requests by Employee ID
    public List<LeaveRequestResponseDTO> getLeaveRequestsByEmployeeId(int employeeId) {
        List<LeaveRequestResponseDTO> leaveRequests = leaveRequestRepository.findByEmployeeId(employeeId);
        if (leaveRequests.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Leave Requests with the Employee ID: " + employeeId + ", were not found");
        }
        return leaveRequests;
    }

    // Get all Leave Requests by Leave Type or Leave Status, both or all
    public List<LeaveRequestResponseDTO> getLeaveRequests(LeaveType type, LeaveStatus status) {

        List<LeaveRequestResponseDTO> leaveRequests = leaveRequestRepository.findByOptionalTypeAndStatus(type, status);

        if (leaveRequests.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No leave requests found for the given filters");
        }
        return leaveRequests;
    }

    // Create Leave Request
    @Transactional
    public LeaveRequestResponseDTO createLeaveRequest(LeaveRequestRequestDTO dto) {
        Employee employee = employeeService.getEmployeeById(dto.getEmployeeId());
        LeaveRequest leaveRequest = LeaveRequestMapper.convertToEntity(dto, employee);
        return LeaveRequestMapper.convertToDTO(leaveRequestRepository.save(leaveRequest));
    }

    // Update Leave Request
    @Transactional
    public LeaveRequestResponseDTO updateLeaveRequest(int leaveRequestId, LeaveRequestRequestDTO dto) {
        LeaveRequest leaveRequest = getLeaveRequestById(leaveRequestId);
        LeaveRequestMapper.updateEntityFromDTO(leaveRequest, dto);
        return LeaveRequestMapper.convertToDTO(leaveRequestRepository.save(leaveRequest));
    }

    // Cancel Leave Request
    @Transactional
    public LeaveRequestResponseDTO cancelLeaveRequest(int leaveRequestId) {

        LeaveRequest leaveRequest = getLeaveRequestById(leaveRequestId);
        // Check if already canceled
        if (leaveRequest.getLeaveStatus() == LeaveStatus.CANCELLED) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Leave Request is already canceled"
            );
        }

        leaveRequest.setLeaveStatus(LeaveStatus.CANCELLED);
        return LeaveRequestMapper.convertToDTO(leaveRequestRepository.save(leaveRequest));
    }
}
