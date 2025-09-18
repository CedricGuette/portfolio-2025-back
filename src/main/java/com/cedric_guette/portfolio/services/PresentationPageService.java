package com.cedric_guette.portfolio.services;

import com.cedric_guette.portfolio.entities.PresentationPage;
import com.cedric_guette.portfolio.exceptions.DatabaseAlreadyFilledException;
import com.cedric_guette.portfolio.exceptions.ItemNotFoundException;
import com.cedric_guette.portfolio.repositories.PresentationPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PresentationPageService {

    private final PresentationPageRepository presentationPageRepository;
    private final ContactFormService contactFormService;

    public PresentationPage createPresentationPage(PresentationPage presentationPage) {
        if(presentationPageRepository.count() >= 3) {
            throw new DatabaseAlreadyFilledException("La base de données ne peut pas contenir plus d'entrée pour les textes de présentation.");
        }

        return presentationPageRepository.save(presentationPage);
    }

    public List<PresentationPage> getAllPresentationPage() {
        return presentationPageRepository.findAll();
    }

    public PresentationPage getPresentationPageById(Long id){
        Optional<PresentationPage> presentationPage = presentationPageRepository.findById(id);
        if(presentationPage.isPresent()){
            return presentationPage.get();
        }
        throw new ItemNotFoundException("Les textes de présentation avec l'id " + id + " n'ont pas été trouvés.");
    }

    public PresentationPage updatePresentationPage(Long id, PresentationPage presentationPage){
        Optional<PresentationPage> optionalPresentationPageInDatabase = presentationPageRepository.findById(id);

        if(optionalPresentationPageInDatabase.isPresent()){
            PresentationPage presentationPageInDatabase = optionalPresentationPageInDatabase.get();

            presentationPageInDatabase.setFirstTitle(presentationPage.getFirstTitle());
            presentationPageInDatabase.setFirstText(presentationPage.getFirstText());
            presentationPageInDatabase.setSecondTitle(presentationPage.getSecondTitle());
            presentationPageInDatabase.setSecondText(presentationPage.getSecondText());
            presentationPageInDatabase.setThirdTitle(presentationPage.getThirdTitle());
            presentationPageInDatabase.setThirdText(presentationPage.getThirdText());

            PresentationPage updatedPresentationPage = presentationPageRepository.save(presentationPageInDatabase);

            return updatedPresentationPage;
        }

        throw new ItemNotFoundException("Les textes de présentation avec l'id " + id + " n'ont pas été trouvés.");
    }

    public List<PresentationPage> udpateAllPresentationPage(List<PresentationPage> presentationPages) {
        PresentationPage french = presentationPages.get(0);
        PresentationPage english = presentationPages.get(1);
        PresentationPage portuguese = presentationPages.get(2);

        List<PresentationPage> updatedPresentationPage = new ArrayList<>();

        updatedPresentationPage.add(updatePresentationPage(1L, french));
        updatedPresentationPage.add(updatePresentationPage(2L, english));
        updatedPresentationPage.add(updatePresentationPage(3L, portuguese));

        return updatedPresentationPage;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initiateDataBase() {
        PresentationPage presentationPage = new PresentationPage();
        presentationPage.setFirstTitle("Titre");
        presentationPage.setFirstText("Contenu");
        presentationPage.setSecondTitle("Titre");
        presentationPage.setSecondText("Contenu");
        presentationPage.setThirdTitle("Titre");
        presentationPage.setThirdText("Contenu");

        if(presentationPageRepository.count() < 3){
            createPresentationPage(presentationPage);
            initiateDataBase();
        }
    }
}
