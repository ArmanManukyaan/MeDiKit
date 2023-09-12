package com.example.medikitmvc.service.impl;

import com.example.medikitcommon.entity.Department;
import com.example.medikitcommon.repository.DepartmentRepository;
import com.example.medikitmvc.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;


    @Override
    public void save(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public Page<Department> getDepartmentPageData(Optional<Integer> size, Optional<Integer> page) {
        int countPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(countPage - 1, pageSize);
        return departmentRepository.findAll(pageable);
    }

    @Override
    public List<Integer> getPageNumbers(int totalPages) {
        if (totalPages > 0) {
            return IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void updateDepartment(Department department) {
        Optional<Department> byId = departmentRepository.findById(department.getId());
        if (byId.isPresent()) {
            Department departmentFromDbData = byId.get();
            departmentFromDbData.setName(department.getName());
            departmentRepository.save(departmentFromDbData);

        }

    }

    @Override
    public void deleteDepartmentById(int id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Optional<Department> findDepartmentById(int id) {
        return departmentRepository.findById(id);
    }
}

