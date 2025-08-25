package com.example.demo.utils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.Properties;

public class EmailSender {

    private final String username;
    private final String password;
    private final String host;
    private final int port;
    private final boolean enableTls;
    
    /**
     * Constructor for EmailSender.
     * 
     * @param host SMTP server host
     * @param port SMTP server port
     * @param username SMTP username
     * @param password SMTP password
     * @param enableTls Whether to enable TLS
     */
    public EmailSender(String host, int port, String username, String password, boolean enableTls) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.enableTls = enableTls;
    }
    
    /**
     * Send an email with the HTML test report attached.
     * 
     * @param from From email address
     * @param to Recipients (comma-separated)
     * @param subject Email subject
     * @param body Email body
     * @param reportFilePath Path to the HTML report file
     * @throws MessagingException If an error occurs while sending the email
     */
    public void sendReportEmail(String from, String to, String subject, String body, String reportFilePath) 
            throws MessagingException {
        
        // Set properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        
        if (enableTls) {
            props.put("mail.smtp.starttls.enable", "true");
        }
        
        // Create session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        // Create message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setSentDate(new Date());
        
        // Create the message body part
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(body, "text/html");
        
        // Create the attachment part
        File reportFile = new File(reportFilePath);
        if (reportFile.exists()) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(reportFilePath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(reportFile.getName());
            
            // Create multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            
            // Set the multipart content
            message.setContent(multipart);
            
            // Send the message
            Transport.send(message);
            
            System.out.println("Email sent successfully with report attachment.");
        } else {
            System.err.println("Report file not found: " + reportFilePath);
            
            // Send email without attachment
            message.setContent(body + "<p>Error: Report file not found.</p>", "text/html");
            Transport.send(message);
        }
    }
}
