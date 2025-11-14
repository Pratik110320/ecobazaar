package com.example.EcoBazaar_module2.service;

import com.example.EcoBazaar_module2.exceptions.StorageException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {

    // This service is no longer used for storing files in the ProductService,
    // as images are now saved directly to the database.
    // Leaving the class structure in case it's needed for other file types later.

    @Value("${file.upload-dir:#{null}}") // Make property optional
    private String uploadDir;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        if (uploadDir != null) {
            try {
                uploadPath = Paths.get(uploadDir);
                Files.createDirectories(uploadPath);
                log.info("Initialized upload directory at: {}", uploadPath.toAbsolutePath());
            } catch (IOException e) {
                log.warn("Could not initialize storage location. This is OK if file.upload-dir is not set.", e);
            }
        } else {
            log.info("file.upload-dir not set. File system storage service is disabled.");
        }
    }

    /**
     * This method is no longer used by ProductService.
     * @param file
     * @return
     */
    public String storeFile(MultipartFile file) {
        if (uploadPath == null) {
            throw new StorageException("Storage location is not configured.");
        }
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = StringUtils.getFilenameExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID().toString() + "." + extension;

        try {
            Path targetLocation = this.uploadPath.resolve(uniqueFilename);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }

            log.info("Stored file: {}", uniqueFilename);

            // This URL refers to the file system path, which is no longer the primary storage.
            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(uniqueFilename)
                    .toUriString();

        } catch (IOException e) {
            throw new StorageException("Failed to store file " + originalFilename, e);
        }
    }
}