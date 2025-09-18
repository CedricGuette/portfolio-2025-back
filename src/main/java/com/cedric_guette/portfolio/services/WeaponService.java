package com.cedric_guette.portfolio.services;

import com.cedric_guette.portfolio.entities.Weapon;
import com.cedric_guette.portfolio.exceptions.ItemNotFoundException;
import com.cedric_guette.portfolio.repositories.WeaponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeaponService {

    private final WeaponRepository weaponRepository;
    private final ImageFileService imageFileService;

    public Weapon createWeapon(Weapon weapon, MultipartFile imageFile) throws IOException {

        if(!imageFile.isEmpty()) {
            weapon.setWeaponLogo(imageFileService.uploadImage(imageFile));
        }

        return weaponRepository.save(weapon);
    }

    public List<Weapon> getAllWeapons() {
        return weaponRepository.findAll();
    }

    public Weapon getWeaponById(Long id) {
        Optional<Weapon> weapon = weaponRepository.findById(id);
        if(weapon.isPresent()){
            return weapon.get();
        }

        throw new ItemNotFoundException("L'arme avec l'id " + id + " n'a pas été trouvée.");
    }

    public Weapon updateWeapon(Long id, Weapon weapon) {
        Optional<Weapon> optionalWeaponInDatabase = weaponRepository.findById(id);

        if(optionalWeaponInDatabase.isPresent()) {
            Weapon weaponInDatabase = optionalWeaponInDatabase.get();

            weaponInDatabase.setWeaponIsObtained(weapon.isWeaponIsObtained());
            weaponInDatabase.setWeaponDescriptionFrench(weapon.getWeaponDescriptionFrench());
            weaponInDatabase.setWeaponDescriptionEnglish(weapon.getWeaponDescriptionEnglish());
            weaponInDatabase.setWeaponDescriptionPortuguese(weapon.getWeaponDescriptionPortuguese());
            weaponInDatabase.setDevstatUpgraded(weapon.getDevstatUpgraded());

            Weapon updatedWeapon = weaponRepository.save(weaponInDatabase);

            return updatedWeapon;
        }

        throw new ItemNotFoundException("L'arme avec l'id " + id + "n'a pas été trouvée.");
    }

    public Weapon updateWeaponAndImage(Long id, Weapon weapon, MultipartFile imageFile) throws IOException {
        Optional<Weapon> optionalWeaponInDatabase = weaponRepository.findById(id);

        if(optionalWeaponInDatabase.isPresent()) {
            Weapon weaponInDatabase = optionalWeaponInDatabase.get();

            imageFileService.deleteImage(weaponInDatabase.getWeaponLogo());
            weaponInDatabase.setWeaponLogo(imageFileService.uploadImage(imageFile));

            weaponInDatabase.setWeaponIsObtained(weapon.isWeaponIsObtained());
            weaponInDatabase.setWeaponDescriptionFrench(weapon.getWeaponDescriptionFrench());
            weaponInDatabase.setWeaponDescriptionEnglish(weapon.getWeaponDescriptionEnglish());
            weaponInDatabase.setWeaponDescriptionPortuguese(weapon.getWeaponDescriptionPortuguese());

            Weapon updatedWeapon = weaponRepository.save(weaponInDatabase);

            return updatedWeapon;
        }

        throw new ItemNotFoundException("L'arme avec l'id " + id + "n'a pas été trouvée.");
    }

    public void deleteWeapon(Long id) throws IOException {
        Optional<Weapon> weapon = weaponRepository.findById(id);

        if(weapon.isPresent()) {
            imageFileService.deleteImage(weapon.get().getWeaponLogo());
            weaponRepository.delete(weapon.get());

            return;
        }

        throw new ItemNotFoundException("L'arme avec l'id " + id + " n'a pas été trouvée.");
    }
}
