package com.example.medikitmvc.controller;

import com.example.medikitcommon.entity.Department;
import com.example.medikitmvc.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/open-page")
    public String getDepartment(@RequestParam("size") Optional<Integer> size,
                                @RequestParam("page") Optional<Integer> page,
                                ModelMap modelMap) {
        Page<Department> result = departmentService.getDepartmentPageData(size, page);
        List<Integer> pageNumbers = departmentService.getPageNumbers(result.getTotalPages());
        modelMap.addAttribute("pageNumbers", pageNumbers);
        modelMap.addAttribute("department", result);
        return "department";
    }


    @GetMapping("/open-page-addDepartment")
    public String createDepartmentPage() {
        return "addDepartment";
    }

    @PostMapping("/add")
    public String createDepartment(@ModelAttribute Department department) {
        departmentService.save(department);
        return "redirect:/departments/open-page";
    }


    @GetMapping("/open-page-update")
    public String updatePatientPage(ModelMap modelMap,
                                    @ModelAttribute("department") Department department) {
        Optional<Department> departmentById = departmentService.findDepartmentById(department.getId());
        departmentById.ifPresent(departmentFromDb -> modelMap.addAttribute("department", departmentFromDb));
        return "updateDepartment";
    }

    @PostMapping("/update")
    public String updatePatient(@ModelAttribute Department department) {
        departmentService.updateDepartment(department);
        return "redirect:/departments/open-page";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam("id") int id) {
        departmentService.deleteDepartmentById(id);
        return "redirect:/departments/open-page";
    }
}
