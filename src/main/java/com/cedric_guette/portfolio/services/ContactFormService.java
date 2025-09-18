package com.cedric_guette.portfolio.services;

import com.cedric_guette.portfolio.entities.ContactForm;
import com.cedric_guette.portfolio.exceptions.ItemNotFoundException;
import com.cedric_guette.portfolio.repositories.ContactFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactFormService {

    private final ContactFormRepository contactFormRepository;
    private final MailingService mailingService;

    @Value("${app.mail.admin}")
    private String mailAdmin;

    public List<ContactForm> getAllMessages(){
        return contactFormRepository.findAll();
    }

    public String mailTemplate(ContactForm contactForm){
        return  "Un nouveau message a été laissé sur votre portfolio par " + contactForm.getSenderName() + "\n Son adresse e-mail : " + contactForm.getSenderMail() +"\n Son message: \n " + contactForm.getMessage();
    }

    public ContactForm sendContactForm(ContactForm contactForm) {
        ContactForm savedContactForm = contactFormRepository.save(contactForm);
        mailingService.sendMailText(mailAdmin, "Un nouveau message a été envoyé depuis votre portfolio", mailTemplate(contactForm));

        return savedContactForm;
    }

    public void deleteContactForm(Long id){
        Optional<ContactForm> contactForm = contactFormRepository.findById(id);
        if(contactForm.isPresent()){
            contactFormRepository.delete(contactForm.get());
        }
        throw new ItemNotFoundException("Le message du formulaire de contact avec l'id " + id + " n'a pas été trouvé.");
    }
}
