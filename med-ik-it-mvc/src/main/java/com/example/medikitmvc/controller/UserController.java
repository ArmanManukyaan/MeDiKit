package com.example.medikitmvc.controller;

import com.example.medikitcommon.entity.User;
import com.example.medikitmvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/verify-account")
    public String verifyUser(@RequestParam("email") String email,
                             @RequestParam("token") String token) {
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isEmpty()) {
            return "redirect:/";
        }

        if (byEmail.get().isEnabled()) {
            return "redirect:/";
        }
        userService.verifyAccount(email, token);
        return "redirect:/customLogin";
    }

    @GetMapping("/email-confirmation-page")
    public String confirmationPage() {
        return "confirmEmail";
    }

    @GetMapping("/confirm-email")
    public String confirmationEmail(@RequestParam("email") String email) {
        userService.confirmationMessage(email);
        return "redirect:/customLogin";
    }

    @GetMapping("/password-change-page")
    public String changePasswordPage(ModelMap modelMap, @RequestParam("email") String email,
                                     @RequestParam("token") String token) {
        userService.passwordChangData(email, token);
        modelMap.addAttribute("email", email);
        modelMap.addAttribute("token", token);
        return "passwordChangePage";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email,
                                 @RequestParam("token") String token,
                                 @RequestParam("password") String password,
                                 @RequestParam("passwordRepeat") String passwordRepeat) {
        userService.forgotPassword(email, token, password, passwordRepeat);
        return "redirect:/customLogin";
    }

    @GetMapping("/activate-deactivate-page")
    public String userActivateDeactivatePage(ModelMap modelMap) {
        List<User> userList = userService.findAll();
        modelMap.addAttribute("users", userList);
        return "activateDeactivateUser";
    }

    @GetMapping("/activate-deactivate")
    public String userActivateDeactivate(@ModelAttribute  User user) {
      userService.activateDeactivateUser(user);
        return "redirect:/users/activate-deactivate-page";

    }
}

