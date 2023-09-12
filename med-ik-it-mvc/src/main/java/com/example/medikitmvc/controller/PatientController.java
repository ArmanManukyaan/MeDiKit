package com.example.medikitmvc.controller;

import com.example.medikitcommon.entity.Patient;
import com.example.medikitmvc.security.CurrentUser;
import com.example.medikitmvc.service.PatientService;
import com.example.medikitmvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final UserService userService;

    @GetMapping
    public String getPatient(@RequestParam("size") Optional<Integer> size,
                             @RequestParam("page") Optional<Integer> page,
                             ModelMap modelMap) {
        Page<Patient> result = patientService.getPatientPageData(size, page);
        List<Integer> pageNumbers = patientService.getPageNumbers(result.getTotalPages());
        modelMap.addAttribute("pageNumbers", pageNumbers);
        modelMap.addAttribute("patients", result);
        return "patients";
    }

    @GetMapping("/open-singlePage")
    public String singlePage(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        modelMap.addAttribute("patients", currentUser.getUser());
        return "patientSinglePage";
    }

    @GetMapping("/open-page-register")
    public String registerPage() {
        return "register";
    }


    @PostMapping("/register")
    public String registerPatient(@ModelAttribute Patient patient,
                                  @RequestParam("image") MultipartFile multipartFile) throws IOException {
        patientService.registerPatient(patient, multipartFile);
        return "redirect:/";
    }

    @GetMapping("/open-page-update")
    public String updatePatientPage(ModelMap modelMap,
                                    @ModelAttribute("patient") Patient patient) {
        Optional<Patient> patientById = patientService.findPatientById(patient.getId());
        patientById.ifPresent(patientFromDb -> modelMap.addAttribute("patient", patientFromDb));
        return "updatePatient";
    }

    @PostMapping("/update")
    public String updatePatient(@ModelAttribute Patient patient,
                                @RequestParam("image") MultipartFile multipartFile) throws IOException {
        patientService.updatePatient(patient, multipartFile);
        return "redirect:/patients";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam("id") int id) throws IOException {
        userService.deleteUserById(id);
        return "redirect:/patients";
    }
}
