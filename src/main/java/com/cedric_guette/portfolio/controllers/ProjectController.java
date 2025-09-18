package com.cedric_guette.portfolio.controllers;

import com.cedric_guette.portfolio.configuration.HttpHeadersCORS;
import com.cedric_guette.portfolio.entities.Project;
import com.cedric_guette.portfolio.services.ProjectService;
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
@RequestMapping("/api/project")
@CrossOrigin(origins = "${app.url-front}")
public class ProjectController {

    private final ProjectService projectService;
    private final HttpHeadersCORS httpHeadersCORS = new HttpHeadersCORS();
    private final String headers = String.valueOf(httpHeadersCORS.headers());

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createProject(@RequestBody Project project) {
        projectService.createProject(project);

        Map<String, String> response = new HashMap<>();
        response.put("created", "Le projet a bien été créé.");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(headers)
                .body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(projectService.getProjectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateProject(@PathVariable Long id, @RequestBody Project project) {
        projectService.updateProject(id, project);

        Map<String, String > response = new HashMap<>();
        response.put("updated", "Le projet avec l'id " + id + " a bien été mis à jour.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }

    @PutMapping(path="/image/{id}/create/{idPhoto}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> createPhoto(@PathVariable Long id, @PathVariable int idPhoto, @RequestPart("image") MultipartFile imageFile) throws IOException {
        Project updatedProject = projectService.createPhoto(id, idPhoto, imageFile);

        Map<String, String> response = new HashMap<>();
        response.put("updated", "La photo " + idPhoto + " du projet avec l'id " + id + " a bien été créée.");
        if(idPhoto == 1){
            response.put("url", updatedProject.getImageOne());
        } else if(idPhoto == 2){
            response.put("url", updatedProject.getImageTwo());
        } else{
            response.put("url", updatedProject.getImageThree());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(headers)
                .body(response);
    }

    @PutMapping(path="/image/{id}/update/{idPhoto}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> updatePhoto(@PathVariable Long id, @PathVariable int idPhoto, @RequestPart("image") MultipartFile imageFile) throws IOException {
        Project updatedProject = projectService.updatePhoto(id, idPhoto, imageFile);

        Map<String, String> response = new HashMap<>();
        response.put("updated", "La photo " + idPhoto + " du projet avec l'id " + id + " a bien été mise à jour.");
        if(idPhoto == 1){
            response.put("url", updatedProject.getImageOne());
        } else if(idPhoto == 2){
            response.put("url", updatedProject.getImageTwo());
        } else{
            response.put("url", updatedProject.getImageThree());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(headers)
                .body(response);
    }

    @PutMapping(path="/image/{id}/delete/{idPhoto}")
    public ResponseEntity<Map<String, String>> deletePhoto(@PathVariable Long id, @PathVariable int idPhoto) throws IOException {
        Project updatedProject = projectService.deletePhoto(id, idPhoto);

        Map<String, String> response = new HashMap<>();
        response.put("updated", "La photo " + idPhoto + " du projet avec l'id " + id + " a bien été suprimée.");
        if(idPhoto == 1){
            response.put("url", updatedProject.getImageOne());
        } else if(idPhoto == 2){
            response.put("url", updatedProject.getImageTwo());
        } else{
            response.put("url", updatedProject.getImageThree());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(headers)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProject(@PathVariable Long id) throws IOException {
        projectService.deleteProject(id);

        Map<String, String> response = new HashMap<>();
        response.put("deleted", "Le projet avec l'id " + id + " a bien été supprimé.");
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(headers)
                .body(response);
    }
}
