package com.dan.employeemanagementsystem.dto;

import com.dan.employeemanagementsystem.enums.LeaveType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LeaveRequestRequestDTO {

    @NotNull
    private Integer employeeId;
    @NotNull
    @FutureOrPresent
    private LocalDate startDate;
    @NotNull
    @Future
    private LocalDate endDate;
    @NotNull
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
