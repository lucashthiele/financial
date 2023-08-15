package com.lucashthiele.financial.services;

import com.lucashthiele.financial.models.kafka.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    KafkaTemplate<String, Email> emailSenderKafkaTemplate;
    @Value("${spring.kafka.email-sender-topic}")
    private String emailSenderTopicName;


    public void produceEmailSenderMessage(Integer recoveryToken, String emailAddress){
        var emailSubject = "Financial password recovery";

        var emailContent = "<h1>Hello, you are receiving your recovery code</h1>" +
                "<p>Use this code on the application to create a new password.</p>" +
                "<h2>" + recoveryToken + "</h2>" +
                "<br>" +
                "<p>The code has a validation time of 2 hours.</p>";

        var email = new Email(
                "sac@financial.com",
                "Financial Support",
                emailAddress,
                emailSubject,
                emailContent);

        emailSenderKafkaTemplate.send(emailSenderTopicName, email);
    }

}
