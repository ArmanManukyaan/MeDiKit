package com.example.medikitmvc.controller;

import com.example.medikitcommon.entity.UserType;
import com.example.medikitmvc.security.CurrentUser;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class MainController {

    @Value("${hospital.upload.image.path}")
    private String imageUploadPath;


    @GetMapping("/customLogin")
    public String indexPage() {
        return "customLogin";
    }

    @GetMapping("/customSuccessLogIn")
    public String customSuccessLogInPage(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            if (currentUser.getUser().getUserType() == UserType.PATIENT) {
                return "redirect:/patients/open-singlePage";
            }
        }
        return null;
    }
    @GetMapping(value = "/getImage",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("imageName") String imageName) throws IOException {
        File file = new File(imageUploadPath + imageName);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                return IOUtils.toByteArray(fis);
            }
        }
        return null;
    }
}
