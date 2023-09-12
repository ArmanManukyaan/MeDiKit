package com.example.medikitmvc.service;

import com.example.medikitcommon.entity.User;

import java.io.IOException;

public interface UserService {
    void registerUser(User user);
    void deleteUserById(int id) throws IOException;
}
