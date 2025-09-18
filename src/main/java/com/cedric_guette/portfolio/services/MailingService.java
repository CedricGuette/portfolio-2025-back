package com.cedric_guette.portfolio.services;

import com.cedric_guette.portfolio.exceptions.ErrorWhileSendingMailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailingService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendMailText(String recipient, String subject, String message) {
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(recipient);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);

            javaMailSender.send(simpleMailMessage);

        } catch (Exception e) {
            throw new ErrorWhileSendingMailException(e.getLocalizedMessage());
        }
    }
}
