package com.cedric_guette.portfolio.controllers;

import com.cedric_guette.portfolio.configuration.HttpHeadersCORS;
import com.cedric_guette.portfolio.configuration.JwtUtils;
import com.cedric_guette.portfolio.entities.User;
import com.cedric_guette.portfolio.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "${app.url-front}")
public class AdminController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final HttpHeadersCORS httpHeadersCORS = new HttpHeadersCORS();
    private final String headers = String.valueOf(httpHeadersCORS.headers());

    @GetMapping("/exists/{username}")
    public ResponseEntity<Map<String, Boolean>> isAdminUsername(@PathVariable String username){
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAdminUsername", userService.isAdminUsername(username));

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }

    @PutMapping("/password/initiate")
    public ResponseEntity<Map<String, String>> initiatePassword(@RequestBody Map<String, String> request){
        userService.setFirstTimePassword(request.get("username"), request.get("temporaryPassword"), request.get("password"));

        Map<String, String> response = new HashMap<>();
        response.put("updated", "Le mot de passe a bien été mis à jour.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }

    @PutMapping("/password/change")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody HashMap<String, String> request, @RequestHeader(name="Authorization") String token){
        String username = jwtUtils.extractUsername(token.substring(7));
        userService.setNewPassword(username, request.get("password"), request.get("newPassword"));

        Map<String, String> response = new HashMap<>();
        response.put("updated", "Le mot de passe a bien été mis à jour.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }
}
