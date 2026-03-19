package com.dan.employeemanagementsystem.service;

import com.dan.employeemanagementsystem.dto.LeaveRequestRequestDTO;
import com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO;
import com.dan.employeemanagementsystem.entity.Employee;
import com.dan.employeemanagementsystem.entity.LeaveRequest;
import com.dan.employeemanagementsystem.enums.LeaveStatus;
import com.dan.employeemanagementsystem.mapper.LeaveRequestMapper;
import com.dan.employeemanagementsystem.repository.LeaveRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RuntimeException("Leave Request not found"));
    }

    // Get Leave Request DTO by ID
    public LeaveRequestResponseDTO getLeaveRequestDTOById(int leaveRequestId) {
        LeaveRequest leaveRequest = getLeaveRequestById(leaveRequestId);
        return LeaveRequestMapper.convertToDTO(leaveRequest);
    }

    // Get Leave Requests by Employee ID
    public List<LeaveRequestResponseDTO> getLeaveRequestsByEmployeeId(int employeeId) {
        List<LeaveRequestResponseDTO> leaveRequests = leaveRequestRepository.findByEmployeeId(employeeId);
        if (leaveRequests.isEmpty()) {
            throw new RuntimeException("Leave Requests with the Employee ID: " + employeeId + ", were not found");
        }
        return leaveRequests;
    }

    // Get Leave Requests by Employee ID
    public List<LeaveRequestResponseDTO> getLeaveRequestsByStatus(LeaveStatus leaveStatus) {
        List<LeaveRequestResponseDTO> leaveRequests = leaveRequestRepository.findByLeaveStatus(leaveStatus);
        if (leaveRequests.isEmpty()) {
            throw new RuntimeException("Leave Requests with the Leave Status " + leaveStatus + ", were not found");
        }
        return leaveRequests;
    }

    // Create Leave Request
    @Transactional
    public LeaveRequestResponseDTO createLeaveRequest(LeaveRequestRequestDTO dto) {
        Employee employee = employeeService.getEmployeeById(dto.getEmployeeId());
        LeaveRequest leaveRequest = LeaveRequestMapper.convertToEntity(dto, employee);
        return LeaveRequestMapper.convertToDTO(leaveRequestRepository.save(leaveRequest)); // save to DB then convert to DTO and return
    }

    // Update Leave Request
    @Transactional
    public LeaveRequestResponseDTO updateLeaveRequest(int leaveRequestId, LeaveRequestRequestDTO dto) {
        LeaveRequest leaveRequest = getLeaveRequestById(leaveRequestId);
        LeaveRequestMapper.updateEntityFromDTO(leaveRequest, dto);
        return LeaveRequestMapper.convertToDTO(leaveRequestRepository.save(leaveRequest));
    }

    // Get all Leave Requests
    public List<LeaveRequestResponseDTO> getAllLeaveRequests() {

        List<LeaveRequest> leaveRequest = leaveRequestRepository.findAll();

        return leaveRequest.stream()
                .map(LeaveRequestMapper::convertToDTO)
                .toList();
    }
}
