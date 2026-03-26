package com.dan.employeemanagementsystem.entity;

import jakarta.persistence.*;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    private String location;

    public Department() {}

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // Constructors, getters, setters

    public Integer getId() {return id;}
    public String getName() {return name;}
    public String getLocation() {return location;}

    public void setId(Integer id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setLocation(String location) {this.location = location;}
}