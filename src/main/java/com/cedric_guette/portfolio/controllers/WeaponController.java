package com.cedric_guette.portfolio.controllers;

import com.cedric_guette.portfolio.configuration.HttpHeadersCORS;
import com.cedric_guette.portfolio.entities.Weapon;
import com.cedric_guette.portfolio.services.WeaponService;
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
@RequestMapping("/api/weapon")
@RequiredArgsConstructor
@CrossOrigin(origins = "${app.url-front}")
public class WeaponController {

    private final WeaponService weaponService;
    private final HttpHeadersCORS headersCORS = new HttpHeadersCORS();
    private String headers = String.valueOf(headersCORS.headers());

    @PostMapping(path = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> createWeapon(@RequestPart Weapon weapon, @RequestPart("image") MultipartFile imageFile) throws IOException {
        weaponService.createWeapon(weapon, imageFile);
        Map<String, String> response = new HashMap<>();
        response.put("created", "L'arme a bien été créée.");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(headers)
                .body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<Weapon>> getAllWeapons() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(weaponService.getAllWeapons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Weapon> getWeaponById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(weaponService.getWeaponById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateWeapon(@PathVariable Long id, @RequestBody Weapon weapon) {
        weaponService.updateWeapon(id, weapon);

        Map<String, String> response = new HashMap<>();
        response.put("updated", "L'arme avec l'id " + id + " a bien été mise à jour.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }

    @PutMapping(path="/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> updateWeaponAndImage(@PathVariable Long id, @RequestPart Weapon weapon, @RequestPart("image") MultipartFile imageFile) throws IOException {
        weaponService.updateWeaponAndImage(id, weapon, imageFile);

        Map<String, String> response = new HashMap<>();
        response.put("updated", "L'arme avec l'id " + id + " a bien été mise à jour.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteWeapon(@PathVariable Long id) throws IOException {
        weaponService.deleteWeapon(id);

        Map<String, String> response = new HashMap<>();
        response.put("deleted", "L'arme avec l'id " + id + " a bien été supprimée.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }
}
