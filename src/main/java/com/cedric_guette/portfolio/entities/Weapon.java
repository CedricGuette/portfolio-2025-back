package com.cedric_guette.portfolio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String weaponLogo;
    private boolean weaponIsObtained;

    private String weaponDescriptionFrench;
    private String weaponDescriptionEnglish;
    private String weaponDescriptionPortuguese;
    private List<Long> devstatUpgraded;
}
