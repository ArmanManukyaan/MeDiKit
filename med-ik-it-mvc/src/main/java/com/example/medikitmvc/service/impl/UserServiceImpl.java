package com.example.medikitmvc.service.impl;

import com.example.medikitcommon.component.EmailSenderService;
import com.example.medikitcommon.entity.User;
import com.example.medikitcommon.repository.UserRepository;
import com.example.medikitcommon.util.ImageDownloader;
import com.example.medikitmvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageDownloader imageDownloader;
    private final EmailSenderService emailSenderService;

    @Value("${site.url}")
    private String siteUrl;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void registerUser(User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isEmpty()) {
            String password = user.getPassword();
            String encode = passwordEncoder.encode(password);
            user.setPassword(encode);
            user.setRegisDate(new Date());
            UUID token = UUID.randomUUID();
            user.setToken(token.toString());
            user.setEnabled(false);
            userRepository.save(user);
            verifyAccountWithEmail(user.getId());
        }
    }

    @Override
    public void verifyAccount(String email, String token) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent() && byEmail.get().getToken().equals(token)
                && !byEmail.get().isEnabled()) {
            User user = byEmail.get();
            user.setToken(null);
            user.setEnabled(true);
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

    public void verifyAccountWithEmail(int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            emailSenderService.sendSimpleEmail(user.getEmail(),
                    "Welcome", "Hi" + user.getName() +
                            "\n" + "Please verify your email by clicking on this url " +
                            siteUrl + "/users/verify-account?email=" + user.getEmail() + "&token=" + user.getToken());
        }
    }

    @Override
    public void confirmationMessage(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            UUID token = UUID.randomUUID();
            user.setToken(token.toString());
            userRepository.save(user);
            sendEmailVerificationMessage(user.getId());
        }
    }

    @Async
    public void sendEmailVerificationMessage(int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            emailSenderService.sendSimpleEmail(user.getEmail(),
                    "Welcome", "Hi" + user.getName() +
                            "\n" + "Confirm to rest password " +
                            siteUrl + "/users/password-change-page?email=" + user.getEmail() + "&token=" + user.getToken());
        }
    }

    @Override
    public void passwordChangData(String email, String token) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent() && byEmail.get().getToken().equals(token)) {
            User user = byEmail.get();
            user.setToken(null);
            userRepository.save(user);
        }
    }

    @Override
    public void forgotPassword(String email, String token, String password, String passwordRepeat) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent() && byEmail.get().isEnabled()
                && password.equals(passwordRepeat) && byEmail.get().getToken() == null) {
            User user = byEmail.get();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean activateDeactivateUser(User user) {
        Optional<User> byId = userRepository.findById(user.getId());
        boolean isProcessDone = false;
        if (byId.isPresent()) {
            User userFromDb = byId.get();
            updateUserStatus(userFromDb);
            isProcessDone = true;
            userRepository.save(userFromDb);
        }
        return isProcessDone;
    }

    @Override
    public void updateUserStatus(User user) {
        if (user.isEnabled()) {
            user.setEnabled(false);
            sendBlockMessage(user);
        } else {
            user.setEnabled(true);
            sendActivationMessage(user);
        }
    }

    private void sendActivationMessage(User user) {
        emailSenderService.sendSimpleEmail(user.getEmail(),
                "You are unblocked", "Hi" + user.getName() +
                        "\n" + "You are active again");
    }

    private void sendBlockMessage(User user) {
        emailSenderService.sendSimpleEmail(user.getEmail(),
                "You are blocked ", "Hi" + user.getName() +
                        "\n" + "You are deactivated by Admin");
    }
}