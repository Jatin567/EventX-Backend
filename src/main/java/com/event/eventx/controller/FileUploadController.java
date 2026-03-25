package com.event.eventx.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            if (file.isEmpty()) {
                response.put("error", "No file provided.");
                return ResponseEntity.badRequest().body(response);
            }

            // Build absolute upload directory path
            String userDir = System.getProperty("user.dir");
            Path uploadPath = Paths.get(userDir, "uploads");
            Files.createDirectories(uploadPath);

            // Generate a unique file name
            String originalFilename = file.getOriginalFilename();
            String extension = ".jpg";
            if (originalFilename != null && originalFilename.lastIndexOf('.') >= 0) {
                extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            }
            String uniqueFilename = UUID.randomUUID().toString() + extension;
            Path targetPath = uploadPath.resolve(uniqueFilename);

            // Read bytes first (avoids temp file issues with Tomcat)
            byte[] bytes = file.getBytes();
            Files.write(targetPath, bytes);

            response.put("imageUrl", "/uploads/" + uniqueFilename);
            response.put("filename", uniqueFilename);
            response.put("saved_to", targetPath.toString());
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("error", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
