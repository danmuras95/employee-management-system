package com.dan.employeemanagementsystem.mapper;

import com.dan.employeemanagementsystem.dto.LeaveRequestRequestDTO;
import com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO;
import com.dan.employeemanagementsystem.entity.Employee;
import com.dan.employeemanagementsystem.entity.LeaveRequest;

public class LeaveRequestMapper {

    public static LeaveRequest convertToEntity(LeaveRequestRequestDTO dto, Employee employee) {
        return new LeaveRequest(
                employee,
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getLeaveType()
        );
    }

    public static LeaveRequestResponseDTO convertToDTO(LeaveRequest leaveRequest) {
        return new LeaveRequestResponseDTO(
                leaveRequest.getId(),
                leaveRequest.getEmployee().getId(),
                leaveRequest.getEmployee().getFirstName() + " " + leaveRequest.getEmployee().getLastName(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getLeaveType(),
                leaveRequest.getLeaveStatus(),
                leaveRequest.getCreatedAt()
        );
    }

    public static void updateEntityFromDTO(LeaveRequest leaveRequest, LeaveRequestRequestDTO dto) {
        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setLeaveType(dto.getLeaveType());
    }
}
