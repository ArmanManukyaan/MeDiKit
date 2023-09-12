package com.example.medikitcommon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
public class Doctor extends User{
    private String speciality;
    private String phoneNumber;
    private int zoomId;
    private String zoomPassword;
    @ManyToOne
    private Department department;
}
