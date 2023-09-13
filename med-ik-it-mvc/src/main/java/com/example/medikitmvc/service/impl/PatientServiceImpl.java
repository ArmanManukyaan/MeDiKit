package com.example.medikitmvc.service.impl;

import com.example.medikitcommon.entity.Patient;
import com.example.medikitcommon.entity.UserType;
import com.example.medikitcommon.repository.PatientRepository;
import com.example.medikitcommon.util.ImageDownloader;
import com.example.medikitmvc.service.PatientService;
import com.example.medikitmvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final UserService userService;
    private final ImageDownloader imageDownloader;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerPatient(Patient patient, MultipartFile multipartFile) throws IOException {
        patient.setUserType(UserType.PATIENT);
        imageDownloader.saveProfilePicture(multipartFile, patient);
        userService.registerUser(patient);
    }

    @Override
    public Page<Patient> getPatientPageData(Optional<Integer> size, Optional<Integer> page) {
        int countPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(countPage - 1, pageSize);
        return patientRepository.findAll(pageable);
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
    public Optional<Patient> findPatientById(int id) {
        return patientRepository.findById(id);
    }

    @Override
    public void updatePatient(Patient patient, MultipartFile multipartFile) throws IOException {
        Optional<Patient> byId = patientRepository.findById(patient.getId());
        if (byId.isPresent()) {
            Patient patientFromDbData = byId.get();
            if(patientRepository.findByEmail(patient.getEmail()).isEmpty() ||
                    patient.getEmail().equals(patientFromDbData.getEmail()))   {
                patientFromDbData.setName(patient.getName());
                patientFromDbData.setSurname(patient.getSurname());
                patientFromDbData.setEmail(patient.getEmail());
                patientFromDbData.setPassword(passwordEncoder.encode(patient.getPassword()));
                patientFromDbData.setBirthDate(patient.getBirthDate());
                patientFromDbData.setPicName(patient.getPicName());
                patientFromDbData.setRegion(patient.getRegion());
                patientFromDbData.setNation(patient.getNation());
                patientFromDbData.setAddress(patient.getAddress());
                patient.setRegisDate(new Date());
                imageDownloader.saveProfilePicture(multipartFile, patient);
                patientRepository.save(patientFromDbData);
            }
        }
    }
}
