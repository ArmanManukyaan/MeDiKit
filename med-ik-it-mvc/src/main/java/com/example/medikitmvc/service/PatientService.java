package com.example.medikitmvc.service;

import com.example.medikitcommon.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PatientService {
    void registerPatient(Patient patient, MultipartFile multipartFile) throws IOException;

    Page<Patient> getPatientPageData(Optional<Integer> size, Optional<Integer> page);

    List<Integer> getPageNumbers(int totalPages);

    Optional<Patient> findPatientById(int id);

    void updatePatient(Patient patient, MultipartFile multipartFile) throws IOException;

}
