package com.dan.employeemanagementsystem.repository;

import com.dan.employeemanagementsystem.entity.LeaveRequest;
import com.dan.employeemanagementsystem.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    // Find Leave Request by Leave Status
    List<LeaveRequest> findByLeaveStatus(LeaveStatus leaveStatus);
    // Find Leave Requests by Employee ID
    List<LeaveRequest> findByEmployeeId(Integer employeeId);
    // Find Leave Requests by Employee ID and Leave Status
    List<LeaveRequest> findByEmployeeIdAndLeaveStatus(Integer employeeId, LeaveStatus status);
    // Find Leave Request between Start Date and End Date
    List<LeaveRequest> findByStartDateBetween(LocalDate start, LocalDate end);
}
