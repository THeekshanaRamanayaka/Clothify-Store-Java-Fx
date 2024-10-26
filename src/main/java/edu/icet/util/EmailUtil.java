package edu.icet.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailUtil {
    public static boolean sendOTPEmail(String email, String generateOTP) {
        Properties properties = new Properties();

        try (InputStream input = EmailUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return false;
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final String form = properties.getProperty("email");
        final String password = properties.getProperty("app.password");
        String smtpHost = properties.getProperty("smtp.host");
        String smtpPort = properties.getProperty("smtp.port");

        if (form == null || password == null || smtpHost == null || smtpPort == null) {
            System.out.println("Required configuration not found in config.properties.");
            return false;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", properties.getProperty("smtp.auth"));
        props.put("mail.smtp.starttls.enable", properties.getProperty("smtp.starttls.enable"));

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(form, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(form));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            message.setSubject("Your OTP Code");

            message.setText("Your OTP code is: " + generateOTP);

            Transport.send(message);
            System.out.println("OTP sent successfully");
            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
