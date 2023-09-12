package com.example.medikitcommon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;


    private String email;


    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regisDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private String picName;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    private boolean enabled;
    private String token;
}
