package com.dan.employeemanagementsystem.entity;

import com.dan.employeemanagementsystem.enums.LeaveStatus;
import com.dan.employeemanagementsystem.enums.LeaveType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @Column(nullable = false)
    @NotNull
    @Future
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus leaveStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public LeaveRequest() {} // JPA

    public LeaveRequest(Employee employee, LocalDate startDate, LocalDate endDate, LeaveType leaveType) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.leaveStatus = LeaveStatus.PENDING;
    }

    public Integer getId() {return id;}
    public Employee getEmployee() {return employee;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public LeaveType getLeaveType() {return leaveType;}
    public LeaveStatus getLeaveStatus() {return leaveStatus;}
    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setId(Integer id) {this.id = id;}
    public void setEmployee(Employee employee) {this.employee = employee;}
    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}
    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}
    public void setLeaveType(LeaveType leaveType) {this.leaveType = leaveType;}
    public void setLeaveStatus(LeaveStatus leaveStatus) {this.leaveStatus = leaveStatus;}
}
