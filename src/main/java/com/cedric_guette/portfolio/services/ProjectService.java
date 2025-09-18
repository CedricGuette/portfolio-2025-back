package com.cedric_guette.portfolio.services;

import com.cedric_guette.portfolio.entities.Project;
import com.cedric_guette.portfolio.exceptions.ItemNotFoundException;
import com.cedric_guette.portfolio.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ImageFileService imageFileService;

    public Project createProject(Project project) {
        project.setImageOne("");
        project.setImageTwo("");
        project.setImageThree("");

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);

        if(project.isPresent()) {
            return project.get();
        }

        throw new ItemNotFoundException("Le projet avec l'id " + id + " n'a pas été trouvé.");
    }

    public Project createPhoto(Long id, int idPhoto, MultipartFile imageFile) throws IOException {
        Optional<Project> optionalProjectInDatabase = projectRepository.findById(id);
        if(optionalProjectInDatabase.isPresent()){
            Project projectInDatabase = optionalProjectInDatabase.get();

            String newImage = imageFileService.uploadImage(imageFile);

            if(idPhoto == 1){
                projectInDatabase.setImageOne(newImage);
            } else if (idPhoto == 2) {
                projectInDatabase.setImageTwo(newImage);
            } else {
                projectInDatabase.setImageThree(newImage);
            }

            return projectRepository.save(projectInDatabase);
        }
        throw new ItemNotFoundException("Le projet avec l'id " + id + "n'a pas été trouvé.");
    }

    public Project deletePhoto(Long id, int idPhoto) throws IOException {
        Optional<Project> optionalProjectInDatabase = projectRepository.findById(id);
        if(optionalProjectInDatabase.isPresent()){
            Project projectInDatabase = optionalProjectInDatabase.get();
            String photoToDelete = "";

            if(idPhoto == 1){
                photoToDelete = projectInDatabase.getImageOne();
                projectInDatabase.setImageOne("");
            } else if (idPhoto == 2) {
                photoToDelete = projectInDatabase.getImageTwo();
                projectInDatabase.setImageTwo("");
            } else if (idPhoto == 3){
                photoToDelete = projectInDatabase.getImageThree();
                projectInDatabase.setImageThree("");
            }

            imageFileService.deleteImage(photoToDelete);

            return projectRepository.save(projectInDatabase);
        }
        throw new ItemNotFoundException("Le projet avec l'id " + id + " n'a pas été trouvé.");
    }

    public Project updatePhoto(Long id, int idPhoto, MultipartFile imageFile) throws IOException {
        deletePhoto(id, idPhoto);
        return createPhoto(id, idPhoto, imageFile);
    }

    public Project updateProject(Long id, Project project) {
        Optional<Project> optionalProjectInDatabase = projectRepository.findById(id);

        if(optionalProjectInDatabase.isPresent()) {
            Project projectInDatabe = optionalProjectInDatabase.get();

            projectInDatabe.setProjectNameFrench(project.getProjectNameFrench());
            projectInDatabe.setProjectNameEnglish(project.getProjectNameEnglish());
            projectInDatabe.setProjectNamePortuguese(project.getProjectNamePortuguese());

            projectInDatabe.setProjectDescriptionFrench(project.getProjectDescriptionFrench());
            projectInDatabe.setProjectDescriptionEnglish(project.getProjectDescriptionEnglish());
            projectInDatabe.setProjectDescriptionPortuguese(project.getProjectDescriptionPortuguese());

            projectInDatabe.setProjectTechnologies(project.getProjectTechnologies());
            projectInDatabe.setProjectGitHubLink(project.getProjectGitHubLink());
            projectInDatabe.setProjectLiveLink(project.getProjectLiveLink());

            Project updatedProject = projectRepository.save(projectInDatabe);

            return updatedProject;
        }

        throw new ItemNotFoundException("Le projet avec l'id " + id + " n'a pas été trouvé.");
    }

    public void deleteProject(Long id) throws IOException {
        Optional<Project> project = projectRepository.findById(id);

        if(project.isPresent()) {
            if(!Objects.equals(project.get().getImageOne(), "")){
                imageFileService.deleteImage(project.get().getImageOne());
            }
            if(!Objects.equals(project.get().getImageTwo(), "")){
                imageFileService.deleteImage(project.get().getImageTwo());
            }
            if(!Objects.equals(project.get().getImageThree(), "")){
                imageFileService.deleteImage(project.get().getImageThree());
            }
            projectRepository.delete(project.get());
            return;
        }

        throw new ItemNotFoundException("Le projet avec l'id " + id + " n'a pas été trouvé.");
    }
}
