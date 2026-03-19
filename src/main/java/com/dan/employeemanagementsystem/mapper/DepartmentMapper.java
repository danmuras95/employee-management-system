package com.dan.employeemanagementsystem.mapper;

import com.dan.employeemanagementsystem.dto.DepartmentDTO;
import com.dan.employeemanagementsystem.entity.Department;

public class DepartmentMapper {

    public static Department convertToEntity(DepartmentDTO dto) {
        return new Department(
                dto.getName(),
                dto.getLocation()
        );
    }

    public static DepartmentDTO convertToDTO(Department department) {
        return new DepartmentDTO(
                department.getId(),
                department.getName(),
                department.getLocation()
        );
    }

    public static void updateEntityFromDTO(Department department, DepartmentDTO dto) {
        department.setName(dto.getName());
        department.setLocation(dto.getLocation());
    }
}
