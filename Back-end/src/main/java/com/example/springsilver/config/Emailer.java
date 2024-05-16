package com.example.springsilver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Emailer {

    private final JavaMailSender javaMailSender;

    @Autowired
    public Emailer(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        try{
            javaMailSender.send(message);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}