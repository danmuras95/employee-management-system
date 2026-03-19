package com.dan.employeemanagementsystem.dto;

import com.dan.employeemanagementsystem.enums.LeaveStatus;
import com.dan.employeemanagementsystem.enums.LeaveType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LeaveRequestResponseDTO {

    private Integer id;
    private Integer employeeId;
    private String employeeFullName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveType leaveType;
    private LeaveStatus leaveStatus;
    private LocalDateTime createdAt;

    public LeaveRequestResponseDTO() {
    }

    public LeaveRequestResponseDTO(Integer id, Integer employeeId, String employeeFullName, LocalDate startDate, LocalDate endDate, LeaveType leaveType, LeaveStatus leaveStatus, LocalDateTime createdAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.employeeFullName = employeeFullName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.leaveStatus = leaveStatus;
        this.createdAt = createdAt;
    }

    public Integer getId() {return id;}
    public Integer getEmployeeId() {return employeeId;}
    public String getEmployeeFullName() {return employeeFullName;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public LeaveType getLeaveType() {return leaveType;}
    public LeaveStatus getLeaveStatus() {return leaveStatus;}
    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setId(Integer id) {this.id = id;}
    public void setEmployeeId(Integer employeeId) {this.employeeId = employeeId;}
    public void setEmployeeFullName(String employeeFullName) {this.employeeFullName = employeeFullName;}
    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}
    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}
    public void setLeaveType(LeaveType leaveType) {this.leaveType = leaveType;}
    public void setLeaveStatus(LeaveStatus leaveStatus) {this.leaveStatus = leaveStatus;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
