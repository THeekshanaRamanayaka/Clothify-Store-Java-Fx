package edu.icet.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {

    private static final String from = "Theekshanasankalpa2004rcc@gmail.com";
    private static final String password = "xwly qwst kblh sdgd";
    public static boolean sendOTPEmail(String email, String generateOTP) {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

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
