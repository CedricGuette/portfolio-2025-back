package com.cedric_guette.portfolio.services;

import com.cedric_guette.portfolio.entities.DevStat;
import com.cedric_guette.portfolio.exceptions.CannotUpdateIndexException;
import com.cedric_guette.portfolio.exceptions.ItemNotFoundException;
import com.cedric_guette.portfolio.repositories.DevStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.matching;

@Service
@RequiredArgsConstructor
public class DevStatService {

    private final DevStatRepository devStatRepository;
    private final ImageFileService imageFileService;

    public DevStat createDevStat(DevStat devStat, MultipartFile imageFile) throws IOException {
        Long howManyDevstat = devStatRepository.count();

        if(!imageFile.isEmpty()){
            devStat.setAbilityLogo(imageFileService.uploadImage(imageFile));
        }

        devStat.setDevstatIndex(howManyDevstat);
        return devStatRepository.save(devStat);
    }

    public List<DevStat> getAllDevStats() {
        Sort sort = Sort.by(Sort.Direction.ASC, "devstatIndex");
        return devStatRepository.findAll(sort);
    }

    public DevStat getDevStatById(Long id) {
        Optional<DevStat> devStat = devStatRepository.findById(id);

        if(devStat.isPresent()) {
            return devStat.get();
        }

        throw new ItemNotFoundException("La stat de dev avec l'id " + id + " n'a pas été trouvée.");
    }

    public DevStat updateDevStat(Long id, DevStat devStat) {
        Optional<DevStat> optionalDevStatInDatabase = devStatRepository.findById(id);

        if(optionalDevStatInDatabase.isPresent()) {
            DevStat devStatInDatabase = optionalDevStatInDatabase.get();

            devStatInDatabase.setAbilityName(devStat.getAbilityName());
            devStatInDatabase.setAbilityScore(devStat.getAbilityScore());

            DevStat updatedDevStat = devStatRepository.save(devStatInDatabase);

            return updatedDevStat;
        }

        throw new ItemNotFoundException("La stat de dev avec l'id " + id + " n'a pas été trouvée.");
    }

    public DevStat updateDevStatAndImage(Long id, DevStat devStat, MultipartFile imageFile) throws IOException {

        Optional<DevStat> optionalDevStatInDatabase = devStatRepository.findById(id);
        if(optionalDevStatInDatabase.isPresent()){
            DevStat devStatInDatabase = optionalDevStatInDatabase.get();

            imageFileService.deleteImage(devStatInDatabase.getAbilityLogo());

            devStatInDatabase.setAbilityName(devStat.getAbilityName());
            devStatInDatabase.setAbilityScore(devStat.getAbilityScore());
            devStatInDatabase.setAbilityLogo(imageFileService.uploadImage(imageFile));

            DevStat updatedDevStat = devStatRepository.save(devStatInDatabase);

            return updatedDevStat;
        }
        throw new ItemNotFoundException("L'image n'a pas été trouvée dans la base de données.");
    }

    public void uploadIndexToTheTop(Long id){
        DevStat devStatToTop = getDevStatById(id);
        Long actualDevstatIndex = devStatToTop.getDevstatIndex();
        if(actualDevstatIndex > 0){
            List<DevStat> devStats = getAllDevStats();
            DevStat devStatToBottom = devStats.get(actualDevstatIndex.intValue() -1);
            devStatToBottom.setDevstatIndex(devStatToBottom.getDevstatIndex() + 1);
            devStatToTop.setDevstatIndex(devStatToTop.getDevstatIndex() - 1);
            devStatRepository.save(devStatToTop);
            devStatRepository.save(devStatToBottom);
            return;
        }
        throw new CannotUpdateIndexException("Impossible de faire monter l'entité plus haut dans la liste.");
    }

    public void uploadIndexToTheBottom(Long id){
        DevStat devStatToBottom = getDevStatById(id);
        Long actualDevstatIndex = devStatToBottom.getDevstatIndex();
        List<DevStat> devStats = getAllDevStats();
        if(actualDevstatIndex < devStats.size() - 1){
            DevStat devStatToTop = devStats.get(actualDevstatIndex.intValue() + 1);
            devStatToBottom.setDevstatIndex(devStatToBottom.getDevstatIndex() + 1);
            devStatToTop.setDevstatIndex(devStatToTop.getDevstatIndex() - 1);
            devStatRepository.save(devStatToTop);
            devStatRepository.save(devStatToBottom);
            return;
        }
        throw new CannotUpdateIndexException("Impossible de faire descendre l'entité plus bas dans la liste.");
    }

    public void deleteDevStat(Long id) throws IOException {
        Optional<DevStat> devStat = devStatRepository.findById(id);

        if(devStat.isPresent()) {
            imageFileService.deleteImage(devStat.get().getAbilityLogo());
            Long devstatIndex = devStat.get().getDevstatIndex();
            devStatRepository.delete(devStat.get());

            List<DevStat> devStats = devStatRepository.findAll();
            while (devstatIndex < devStats.size()) {
                DevStat devstatToUpload = devStats.get(devstatIndex.intValue());
                devstatToUpload.setDevstatIndex(devstatToUpload.getDevstatIndex() - 1);
                devStatRepository.save(devstatToUpload);
                devstatIndex = devstatIndex + 1;
            }

            return;
        }

        throw new ItemNotFoundException("La stat de dev avec l'id " + id + " n'a pas été trouvée.");
    }
}
