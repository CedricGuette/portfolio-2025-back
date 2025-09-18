package com.cedric_guette.portfolio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectNameFrench;
    private String projectDescriptionFrench;

    private String projectNameEnglish;
    private String projectDescriptionEnglish;

    private String projectNamePortuguese;
    private String projectDescriptionPortuguese;

    private String projectTechnologies;
    private String projectGitHubLink;
    private String projectLiveLink;

    private String imageOne;
    private String imageTwo;
    private String imageThree;
}
