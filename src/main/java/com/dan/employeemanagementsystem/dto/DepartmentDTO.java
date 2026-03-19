package com.dan.employeemanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartmentDTO {

    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String location;

    public DepartmentDTO() {
    }

    public DepartmentDTO(Integer id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Integer getId() {return id;}
    public String getName() {return name;}
    public String getLocation() {return location;}

    public void setId(Integer id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setLocation(String location) {this.location = location;}
}