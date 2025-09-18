package com.cedric_guette.portfolio.controllers;

import com.cedric_guette.portfolio.configuration.HttpHeadersCORS;
import com.cedric_guette.portfolio.exceptions.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class ResourcesOnServerController {

    private final HttpHeadersCORS headersCORS = new HttpHeadersCORS();

    /*
     * Requête pour récupérer les images
     */
    @GetMapping("/uploads/{filename}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {

        try {
            Path filePath = Paths.get("src/main/resources/static/uploads/").resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {

                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(String.valueOf(headersCORS.headers()))
                        .body(resource);
            } else {
                throw new FileNotFoundException("L'image " + filename + " n'a pas été trouvée.");
            }
        } catch (MalformedURLException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header(String.valueOf(headersCORS.headers()))
                    .build();
        }
    }
}
