package com.cedric_guette.portfolio.services;

import com.cedric_guette.portfolio.exceptions.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageFileService {

    private static final String UPLOAD_PATH = "src/main/resources/static/";
    private static final String UPLOAD_DIRECTORY = "uploads/";

    public String uploadImage(MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {

            // On vérifie que le repertoire existe bien sinon on le crée
            File uploadDirectory = new File(UPLOAD_PATH + UPLOAD_DIRECTORY);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            // On génère un nom de fichier unique
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_PATH + UPLOAD_DIRECTORY + fileName);
            Files.write(filePath, imageFile.getBytes());
            String imageUrl = "/" + UPLOAD_DIRECTORY + fileName;

            return imageUrl;
        }
        throw new ImageNotFoundException("L'image cherchée n'a pas été trouvée.");
    }

    public void deleteImage (String imageName) throws IOException {

        Path photoToDeletePath = Paths.get(UPLOAD_PATH + imageName.substring(1));
        if(Files.exists(photoToDeletePath)) {
            Files.delete(photoToDeletePath);
            return;
        }
        throw new ImageNotFoundException("L'image cherchée n'a pas été trouvée.");
    }
}
