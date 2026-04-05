package com.dan.employeemanagementsystem.repository;

import com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO;
import com.dan.employeemanagementsystem.entity.LeaveRequest;
import com.dan.employeemanagementsystem.enums.LeaveStatus;
import com.dan.employeemanagementsystem.enums.LeaveType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    // Find Leave Requests by Leave Type or Leave Status, both or all
    @Query("SELECT new com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO(" +
            "l.id, l.employee.id, CONCAT(l.employee.firstName, ' ', l.employee.lastName), " +
            "l.startDate, l.endDate, l.leaveType, l.leaveStatus, l.createdAt) " +
            "FROM LeaveRequest l " +
            "WHERE (:type IS NULL OR l.leaveType = :type) " +
            "AND (:status IS NULL OR l.leaveStatus = :status)")
    Page<LeaveRequestResponseDTO> findByOptionalTypeAndStatus(@Param("type") LeaveType type, @Param("status") LeaveStatus status, Pageable pageable);
    // Find Leave Requests by Employee ID
    @Query("SELECT new com.dan.employeemanagementsystem.dto.LeaveRequestResponseDTO(" +
            "l.id, l.employee.id, CONCAT(l.employee.firstName, ' ', l.employee.lastName), " +
            "l.startDate, l.endDate, l.leaveType, l.leaveStatus, l.createdAt) " +
            "FROM LeaveRequest l WHERE l.employee.id = :employeeId")
    List<LeaveRequestResponseDTO> findByEmployeeId(@Param("employeeId") Integer employeeId);
    // Find Leave Requests between Start Date and End Date
    List<LeaveRequest> findByStartDateBetween(LocalDate start, LocalDate end);
    // Check if Leave Requests overlap
    @Query("SELECT COUNT(l) > 0 FROM LeaveRequest l " +
            "WHERE l.employee.id = :employeeId " +
            "AND l.leaveStatus = 'APPROVED' " +
            "AND l.startDate <= :endDate " +
            "AND l.endDate >= :startDate")
    boolean existsOverlappingLeave(@Param("employeeId") Integer employeeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    // Check if Leave Requests overlap on update
    @Query("SELECT COUNT(l) > 0 FROM LeaveRequest l " +
            "WHERE l.employee.id = :employeeId " +
            "AND l.leaveStatus = 'APPROVED' " +
            "AND l.id <> :leaveId " +
            "AND l.startDate <= :endDate " +
            "AND l.endDate >= :startDate")
    boolean existsOverlappingLeaveForUpdate(@Param("leaveId") Integer leaveId, @Param("employeeId") Integer employeeId,
                                            @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}