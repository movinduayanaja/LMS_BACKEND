package com.lib1.backendlib.service.impl;

import com.lib1.backendlib.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendSignupConfirmation(String toEmail) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("Welcome to the Library!");
            helper.setText("""
                    <h3>Signup Successful</h3>
                    <p>Your account has been created successfully.</p>
                    """, true);
            mailSender.send(msg);
        } catch (MessagingException e) {
            // Log and continue (don't fail the signup if email fails)
            System.err.println("Failed to send signup email: " + e.getMessage());
        }
    }
}