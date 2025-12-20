package com.example.EcoBazaar_module2.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveBase64Image(String base64Image) throws IOException {
        String[] parts = base64Image.split(",");
        String imageString = parts.length > 1 ? parts[1] : parts[0];

        byte[] imageBytes = Base64.getDecoder().decode(imageString);

        String fileName = UUID.randomUUID() + ".jpg";
        Path path = Paths.get(uploadDir, fileName);

        Files.createDirectories(path.getParent());
        Files.write(path, imageBytes);

        return fileName; // return filename to store in DB
    }

    public String saveMultipartImage(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension == null || extension.isEmpty()) extension = "jpg";

        String fileName = UUID.randomUUID() + "." + extension;

        Path path = Paths.get(uploadDir, fileName);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path);

        return fileName; // return filename to store in DB
    }
}