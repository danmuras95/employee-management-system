package com.dan.employeemanagementsystem.dto;

import com.dan.employeemanagementsystem.enums.LeaveType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LeaveRequestRequestDTO {

    @NotNull(message = "Employee Id must not be null")
    private Integer employeeId;
    @NotNull(message = "Start Date must not be null")
    @FutureOrPresent(message = "Start Date must be from the future or present")
    private LocalDate startDate;
    @NotNull(message = "End Date must not be null")
    @Future(message = "End Date must be from the future")
    private LocalDate endDate;
    @NotNull(message = "Leave Type must not be null")
    private LeaveType leaveType;

    public LeaveRequestRequestDTO() {
    }

    public LeaveRequestRequestDTO(Integer employeeId, LocalDate startDate, LocalDate endDate, LeaveType leaveType) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
    }

    public Integer getEmployeeId() {return employeeId;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public LeaveType getLeaveType() {return leaveType;}

    public void setEmployeeId(Integer employeeId) {this.employeeId = employeeId;}
    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}
    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}
    public void setLeaveType(LeaveType leaveType) {this.leaveType = leaveType;}
}
