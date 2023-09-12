package com.example.medikitcommon.repository;

import com.example.medikitcommon.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
