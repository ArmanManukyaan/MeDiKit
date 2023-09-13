package com.example.medikitmvc.service;

import com.example.medikitcommon.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    void registerUser(User user);

    void deleteUserById(int id) throws IOException;

    Optional<User> findByEmail(String email);

    void verifyAccount(String email, String token);

    void verifyAccountWithEmail(int id);

    void confirmationMessage(String email);

    void sendEmailVerificationMessage(int id);

    void passwordChangData(String email, String token);

    void forgotPassword(String email, String token, String password, String passwordRepeat);

    List <User> findAll();

    boolean activateDeactivateUser(User user);
    void updateUserStatus(User user);
}
