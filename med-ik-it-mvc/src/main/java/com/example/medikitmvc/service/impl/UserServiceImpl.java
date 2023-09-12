package com.example.medikitmvc.service.impl;

import com.example.medikitcommon.entity.User;
import com.example.medikitcommon.repository.UserRepository;
import com.example.medikitcommon.util.ImageDownloader;
import com.example.medikitmvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageDownloader imageDownloader;

    @Override
    public void registerUser(User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isEmpty()) {
            String password = user.getPassword();
            String encode = passwordEncoder.encode(password);
            user.setPassword(encode);
            user.setRegisDate(new Date());
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUserById(int id) throws IOException {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            imageDownloader.deleteProfilePicture(byId.get().getPicName());
        }
        userRepository.deleteById(id);
    }
}
