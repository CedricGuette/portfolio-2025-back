package com.cedric_guette.portfolio.services;

import com.cedric_guette.portfolio.entities.User;
import com.cedric_guette.portfolio.exceptions.UsernameAndPasswordDoNotMatchException;
import com.cedric_guette.portfolio.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MailingService mailingService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${app.url-front}")
    private String urlFront;

    @Value("${app.mail.admin}")
    private String mailAdmin;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void createAdmin(){
        if(userRepository.count() == 0){
            User admin = new User();
            admin.setCreatedDate(LocalDateTime.now());
            admin.setRole(String.valueOf(User.Role.ROLE_ADMIN));
            UUID username = UUID.randomUUID();
            UUID password = UUID.randomUUID();
            admin.setFirstConnexion(true);
            admin.setUsername(String.valueOf(username));
            admin.setTemporaryPassword(String.valueOf(password));

            mailingService.sendMailText(mailAdmin, "Prochaine étape pour la création d'un administrateur", adminMailTemplate(admin));

            userRepository.save(admin);
        }
    }

    public boolean isAdminUsername(String username){
        List<User> allUsers = userRepository.findAll();
        User admin = allUsers.getFirst();
        if(admin.getUsername().equals(username) && admin.isFirstConnexion()){
            return true;
        } else{
            return false;
        }
    }

    public User setFirstTimePassword(String username, String temporaryPassword, String password){
        User admin = userRepository.findByUsername(username);
        if(admin.isFirstConnexion() && admin.getTemporaryPassword().equals(temporaryPassword)){
            admin.setPassword(passwordEncoder.encode(password));
            admin.setFirstConnexion(false);
            admin.setTemporaryPassword("");

            return userRepository.save(admin);
        } else {
            throw new UsernameAndPasswordDoNotMatchException("L'identifiant et le mot de passe ne correspondent pas.");
        }
    }

    public User setNewPassword(String username, String password, String newPassword){
        User admin = userRepository.findByUsername(username);
        if(passwordEncoder.matches(password, admin.getPassword())){
            admin.setPassword(passwordEncoder.encode(newPassword));

            return userRepository.save(admin);
        } else {
            throw new UsernameAndPasswordDoNotMatchException("L'identifiant et le mot de passe ne correspondent pas.");
        }
    }

    public String adminMailTemplate(User user){
        return "Dans ce mail se trouvent des informations importantes pour créer votre administration.\n" +
                "Voici votre identifiant, le mot de passe est temporaire et il vous sera demandé de le changer lors de votre première connexion.\n\n" +
                "Identifiant: " + user.getUsername() + "\n" +
                "Mot de passe: " + user.getTemporaryPassword() + "\n\n" +
                "Pour passer à la prochaine étape je vous demanderais de bien vouloir suivre le lien ci-dessous:\n" +
                urlFront + "/admin-generator/" + user.getUsername() + "\n\n" +
                "Cordialement, l'équipe du site.";
    }


    @EventListener(ApplicationReadyEvent.class)
    public void initiateAdmin() {
        createAdmin();
    }
}

// passwordEncoder.encode(user.getPassword())
