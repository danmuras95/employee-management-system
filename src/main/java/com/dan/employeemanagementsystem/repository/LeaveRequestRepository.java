package com.dan.employeemanagementsystem.repository;

import com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO;
import com.dan.employeemanagementsystem.entity.LeaveRequest;
import com.dan.employeemanagementsystem.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    // Find Leave Requests by Leave Status

    @Query("SELECT new com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO(" +
            "l.id, l.employee.id, CONCAT(l.employee.firstName, ' ', l.employee.lastName), " +
            "l.startDate, l.endDate, l.leaveType, l.leaveStatus, l.createdAt) " +
            "FROM LeaveRequest l WHERE l.leaveStatus = :status")
    List<LeaveRequestResponseDTO> findByLeaveStatus(@Param("status") LeaveStatus status);
    // Find Leave Requests by Employee ID
    @Query("SELECT new com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO(" +
            "l.id, l.employee.id, CONCAT(l.employee.firstName, ' ', l.employee.lastName), " +
            "l.startDate, l.endDate, l.leaveType, l.leaveStatus, l.createdAt) " +
            "FROM LeaveRequest l WHERE l.employee.id = :employeeId")
    List<LeaveRequestResponseDTO> findByEmployeeId(@Param("employeeId") Integer employeeId);
    // Find Leave Requests by Employee ID and Leave Status
    List<LeaveRequest> findByEmployeeIdAndLeaveStatus(Integer employeeId, LeaveStatus status);
    // Find Leave Requests between Start Date and End Date
    List<LeaveRequest> findByStartDateBetween(LocalDate start, LocalDate end);
}
