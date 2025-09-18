package com.cedric_guette.portfolio.controllers;

import com.cedric_guette.portfolio.configuration.HttpHeadersCORS;
import com.cedric_guette.portfolio.entities.DevStat;
import com.cedric_guette.portfolio.services.DevStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/devstat")
@CrossOrigin(origins = "${app.url-front}")
public class DevStatController {

    private final DevStatService devStatService;
    private final HttpHeadersCORS headersCORS = new HttpHeadersCORS();
    private final String headers = String.valueOf(headersCORS.headers());

    @PostMapping(path="/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> createDevStat(@RequestPart  DevStat devStat, @RequestPart("image") MultipartFile imageFile) throws IOException {
        devStatService.createDevStat(devStat, imageFile);

        Map<String, String> response = new HashMap<>();
        response.put("created", "La stat de dev a bien été créée.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<DevStat>> getAllDevStats() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(devStatService.getAllDevStats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevStat> getDevStatById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(devStatService.getDevStatById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateDevStat(@PathVariable Long id, @RequestBody DevStat devStat) {
        DevStat updatedDevStat = devStatService.updateDevStat(id, devStat);

        Map<String, String> response = new HashMap<>();
        response.put("updated", "La stat de dev avec le nom " + updatedDevStat.getAbilityName() + " a bien été mise à jour.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }

    @PutMapping(path="/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> updateDevStatAndImage(@PathVariable Long id, @RequestPart DevStat devStat, @RequestPart("image") MultipartFile imageFile) throws IOException {
        DevStat updatedDevStat = devStatService.updateDevStatAndImage(id, devStat, imageFile);

        Map<String, String> response = new HashMap<>();
        response.put("updated", "La stat de dev avec le nom " + updatedDevStat.getAbilityName() + " a bien été mise à jour.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }

    @PutMapping("/movetop/{id}")
    public ResponseEntity<Map<String, String>> moveDevStatToTop(@PathVariable Long id){
        DevStat devStat = devStatService.getDevStatById(id);
        devStatService.uploadIndexToTheTop(id);

        Map<String, String> response = new HashMap<>();
        response.put("updated", "L'élément " + devStat.getAbilityName() + " a bien été monté dans la liste.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }

    @PutMapping("/movebottom/{id}")
    public ResponseEntity<Map<String, String>> moveDevStatToBottom(@PathVariable Long id){
        DevStat devStat = devStatService.getDevStatById(id);
        devStatService.uploadIndexToTheBottom(id);

        Map<String, String> response = new HashMap<>();
        response.put("updated", "L'élément " + devStat.getAbilityName() + " a bien été déscendu dans la liste.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDevStat(@PathVariable Long id) throws IOException {
        DevStat devStat = devStatService.getDevStatById(id);
        devStatService.deleteDevStat(id);

        Map<String, String> response = new HashMap<>();
        response.put("deleted", "La stat de dev avec le nom " + devStat.getAbilityName() + " a bien été supprimée.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }
}
