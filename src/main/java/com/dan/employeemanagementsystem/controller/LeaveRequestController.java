package com.dan.employeemanagementsystem.controller;

import com.dan.employeemanagementsystem.dto.LeaveRequestRequestDTO;
import com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO;
import com.dan.employeemanagementsystem.dto.PaginatedResponseDTO;
import com.dan.employeemanagementsystem.enums.LeaveStatus;
import com.dan.employeemanagementsystem.enums.LeaveType;
import com.dan.employeemanagementsystem.service.LeaveRequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaveRequests")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping
    public PaginatedResponseDTO<LeaveRequestResponseDTO> getLeaveRequests( //leaveRequests?leaveType=VACATION
            @RequestParam(required = false) LeaveType leaveType,          //leaveRequests?leaveStatus=APPROVED
            @RequestParam(required = false) LeaveStatus leaveStatus,     //leaveRequests?leaveType=VACATION&&leaveStatus=APPROVED
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction){
        return leaveRequestService.getLeaveRequests(leaveType, leaveStatus, page, size, sortBy, direction);
    }

    @GetMapping("/{leaveRequestId}")
    public LeaveRequestResponseDTO getLeaveRequestById(@PathVariable int leaveRequestId) {
        return leaveRequestService.getLeaveRequestByIdAsDTO(leaveRequestId);
    }

    @PostMapping
    public LeaveRequestResponseDTO createLeaveRequest(@Valid @RequestBody LeaveRequestRequestDTO leaveRequestDTO) {
        return leaveRequestService.createLeaveRequest(leaveRequestDTO);
    }

    @PutMapping("/{leaveRequestId}")
    public LeaveRequestResponseDTO updateLeaveRequest(@PathVariable int leaveRequestId,
                                                    @Valid @RequestBody LeaveRequestRequestDTO leaveRequestDTO) {
        return leaveRequestService.updateLeaveRequest(leaveRequestId, leaveRequestDTO);
    }

    @PutMapping("/{leaveRequestId}/cancel")
    public LeaveRequestResponseDTO cancelLeaveRequest(@PathVariable int leaveRequestId) {
        return leaveRequestService.cancelLeaveRequest(leaveRequestId);
    }
}
