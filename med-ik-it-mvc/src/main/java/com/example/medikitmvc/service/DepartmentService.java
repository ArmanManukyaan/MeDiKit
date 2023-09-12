package com.example.medikitmvc.service;

import com.example.medikitcommon.entity.Department;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    void save(Department department);

    Page<Department> getDepartmentPageData(Optional<Integer> size, Optional<Integer> page);

    List<Integer> getPageNumbers(int totalPages);


    void updateDepartment(Department department);

    void deleteDepartmentById(int id);

    Optional<Department> findDepartmentById(int id);
}
