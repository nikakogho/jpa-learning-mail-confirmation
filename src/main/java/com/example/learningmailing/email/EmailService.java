package com.example.learningmailing.email;

import org.slf4j.*;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;

@Service
public class EmailService implements EmailSender {
    private final JavaMailSender sender;
    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    @Async
    @Override
    public String send(String to, String content) {
        String from = "totally-legit-company@no-reply.com";
        String subject = "Email verification link";

        try {
            var msg = sender.createMimeMessage();
            var helper = new MimeMessageHelper(msg, "utf-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            sender.send(msg);
        } catch (MessagingException e) {
            logger.error("Failed to send email: " + e.toString());
            return "Message could not be sent";
        }

        return "Message sent";
    }
}
