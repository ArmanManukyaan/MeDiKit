package com.example.medikitcommon.util;

import com.example.medikitcommon.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageDownloader {

    @Value("${hospital.upload.image.path}")
    private String imageUploadPath;

    public void saveProfilePicture(MultipartFile multipartFile, User user) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            user.setPicName(fileName);
        }
    }

    public void deleteProfilePicture(String fileName) throws IOException {
        if (fileName != null && !fileName.equalsIgnoreCase("null")) {
            Path path = Paths.get(imageUploadPath + fileName);
            Files.delete(path);
        }
    }
}
