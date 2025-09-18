package com.cedric_guette.portfolio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresentationPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstTitle;
    @Lob
    private String firstText;
    private String secondTitle;
    @Lob
    private String secondText;
    private String thirdTitle;
    @Lob
    private String thirdText;
}
