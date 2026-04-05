package com.dan.employeemanagementsystem.service;

import com.dan.employeemanagementsystem.dto.LeaveRequestRequestDTO;
import com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO;
import com.dan.employeemanagementsystem.dto.PaginatedResponseDTO;
import com.dan.employeemanagementsystem.entity.Employee;
import com.dan.employeemanagementsystem.entity.LeaveRequest;
import com.dan.employeemanagementsystem.enums.LeaveStatus;
import com.dan.employeemanagementsystem.enums.LeaveType;
import com.dan.employeemanagementsystem.mapper.LeaveRequestMapper;
import com.dan.employeemanagementsystem.repository.LeaveRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
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
        log.debug("Fetching leave request with ID: {}", leaveRequestId);
        return leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> {
                    log.error("Leave request not found with ID: {}", leaveRequestId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"Leave Request not found");
                });
    }

    // Get Leave Request DTO by ID
    public LeaveRequestResponseDTO getLeaveRequestByIdAsDTO(int leaveRequestId) {
        log.debug("Fetching leave request DTO with ID: {}", leaveRequestId);
        LeaveRequest leaveRequest = getLeaveRequestById(leaveRequestId);
        return LeaveRequestMapper.convertToDTO(leaveRequest);
    }

    // Get all Leave Requests by Leave Type or Leave Status, both or all
    public PaginatedResponseDTO<LeaveRequestResponseDTO> getLeaveRequests(LeaveType type, LeaveStatus status,
                                                                          int page, int size, String sortBy, String direction) {
        log.debug("Fetching leave requests with filter - Type: {}, Status: {}", type, status);
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<LeaveRequestResponseDTO> leaveRequestPage = leaveRequestRepository.findByOptionalTypeAndStatus(type, status, pageable);

        if (leaveRequestPage.isEmpty()) {
            log.warn("No leave requests found for type: {} and status: {}", type, status);
        }
        log.info("Found {} leave requests for type: {} and status: {}", leaveRequestPage.getTotalElements(), type, status);
        return new PaginatedResponseDTO<>(leaveRequestPage.getContent(), leaveRequestPage.getNumber(),
                leaveRequestPage.getSize(), leaveRequestPage.getTotalElements(), leaveRequestPage.getTotalPages()
        );
    }

    // Create Leave Request
    @Transactional
    public LeaveRequestResponseDTO createLeaveRequest(LeaveRequestRequestDTO dto) {
        log.info("Creating leave request for employee ID: {}", dto.getEmployeeId());
        Employee employee = employeeService.getEmployeeById(dto.getEmployeeId());
        boolean overlap = leaveRequestRepository.existsOverlappingLeave(employee.getId(), dto.getStartDate(), dto.getEndDate());

        if (overlap) {
            throw new IllegalArgumentException(
                    "Cannot create leave request: overlaps with an existing approved leave"
            );
        }
        LeaveRequest leaveRequest = LeaveRequestMapper.convertToEntity(dto, employee);
        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        log.info("Leave request created with ID: {}", savedLeaveRequest.getId());
        return LeaveRequestMapper.convertToDTO(savedLeaveRequest);
    }

    // Update Leave Request
    @Transactional
    public LeaveRequestResponseDTO updateLeaveRequest(int leaveRequestId, LeaveRequestRequestDTO dto) {
        log.info("Updating leave request with ID: {}", leaveRequestId);
        LeaveRequest leaveRequest = getLeaveRequestById(leaveRequestId);

        boolean overlap = leaveRequestRepository.existsOverlappingLeaveForUpdate(leaveRequest.getId(),
                dto.getEmployeeId(), dto.getStartDate(), dto.getEndDate());

        if (overlap) {
            throw new IllegalArgumentException(
                    "Cannot update leave request: overlaps with an existing approved leave"
            );
        }

        LeaveRequestMapper.updateEntityFromDTO(leaveRequest, dto);
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        log.info("Leave request updated with ID: {}", updatedLeaveRequest.getId());
        return LeaveRequestMapper.convertToDTO(updatedLeaveRequest);
    }

    // Cancel Leave Request
    @Transactional
    public LeaveRequestResponseDTO cancelLeaveRequest(int leaveRequestId) {
        log.info("Attempting to cancel leave request with ID: {}", leaveRequestId);
        LeaveRequest leaveRequest = getLeaveRequestById(leaveRequestId);
        // Check if already canceled
        if (leaveRequest.getLeaveStatus() == LeaveStatus.CANCELLED) {
            log.warn("Leave request with ID: {} is already canceled", leaveRequestId);
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Leave Request is already canceled"
            );
        }

        leaveRequest.setLeaveStatus(LeaveStatus.CANCELLED);
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        log.info("Leave request with ID: {} canceled successfully", leaveRequestId);
        return LeaveRequestMapper.convertToDTO(updatedLeaveRequest);
    }
}
