package com.cedric_guette.portfolio.controllers;

import com.cedric_guette.portfolio.configuration.HttpHeadersCORS;
import com.cedric_guette.portfolio.entities.ContactForm;
import com.cedric_guette.portfolio.services.ContactFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@CrossOrigin(origins = "${app.url-front}")
public class ContactFormController {

    private final ContactFormService contactFormService;
    private final HttpHeadersCORS httpHeadersCORS = new HttpHeadersCORS();
    private final String headers = String.valueOf(httpHeadersCORS.headers());

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendContactForm(@RequestBody ContactForm contactForm){
        contactFormService.sendContactForm(contactForm);

        Map<String,String> response = new HashMap<>();
        response.put("sended", "Le message que vous avez envoyé a bien été enregistré.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<ContactForm>> getAllMessages(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(contactFormService.getAllMessages());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteContactForm(@PathVariable Long id){
        contactFormService.deleteContactForm(id);

        Map<String ,String> response = new HashMap<>();
        response.put("deleted", "Le message avec l'id " + id + " a bien été supprimé.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }
}
