package com.cedric_guette.portfolio.controllers;

import com.cedric_guette.portfolio.configuration.HttpHeadersCORS;
import com.cedric_guette.portfolio.entities.PresentationPage;
import com.cedric_guette.portfolio.services.PresentationPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "${app.url-front}")
public class TextsController {

    private final PresentationPageService presentationPageService;

    private final HttpHeadersCORS headersCORS = new HttpHeadersCORS();
    private final String headers = String.valueOf(headersCORS.headers());


    @GetMapping("/presentation")
    public ResponseEntity<List<PresentationPage>> getAllPresentationPage() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(presentationPageService.getAllPresentationPage());
    }

    @PutMapping("/presentation/update")
    public ResponseEntity<Map<String, String>> updatePresentationPage(@RequestBody List<PresentationPage> presentationPage) {
        presentationPageService.udpateAllPresentationPage(presentationPage);

        Map<String, String> response = new HashMap<>();
        response.put("updated", "Les textes de présentations ont bien été mis à jour.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }
}
