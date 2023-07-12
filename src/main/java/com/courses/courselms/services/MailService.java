package com.courses.courselms.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String subject, String htmlText) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("rafatedesco@teste.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlText, true);
            mailSender.send(message);
        } catch (Exception ex) {
            System.out.println(ex.getCause());
            throw new RuntimeException(ex.getCause());
        }
    }
}
