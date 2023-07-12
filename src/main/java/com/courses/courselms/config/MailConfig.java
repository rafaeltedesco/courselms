package com.courses.courselms.config;

import com.courses.courselms.services.HtmlGenerator;
import com.courses.courselms.services.MailService;
import com.courses.courselms.services.WelcomeHtmlGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.user}")
    private String user;

    @Bean
    HtmlGenerator getHtmlGenerator() {
        return new WelcomeHtmlGenerator();
    }

    @Bean
    MailService getMailService() {
        return new MailService();
    }

    @Bean
    JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.mailHost);
        mailSender.setPort(this.mailPort);
        mailSender.setPassword(this.password);
        mailSender.setUsername(this.user);
        return mailSender;
    }
}
