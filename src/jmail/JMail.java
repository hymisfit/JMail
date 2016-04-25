/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmail;


import java.lang.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 *
 * @author henry
 */
public class JMail {
    public static void sendEmailWithAttachments(String host, String port, final String userName, final String password, String toAddress, String subject, String message, String[] attachFiles)
            throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
        
        String[] address = toAddress.split(":CC=");
        toAddress = address[0];
        InternetAddress[] ccAddress = new InternetAddress[address.length-1];
        if( address.length > 1 ) {
            for(int i=1; i<address.length; i++) {
                ccAddress[i-1] = new InternetAddress(address[i]);
            }
        }
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setRecipients(Message.RecipientType.CC, ccAddress);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    System.err.println("Can't attach file " + filePath);
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            }
        }
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        Transport.send(msg);
    }
    public static String correctPath(String filePath) {
        String currentDir = System.getProperty("user.dir");
        if( filePath.indexOf("~/") == 0 ) {
            filePath = System.getProperty("user.home") + filePath.substring(1);
        } else if( filePath.indexOf("./") == 0 )  {
            filePath = currentDir + filePath.substring(1);
        } else if( filePath.indexOf("../") == 0 )  {
            filePath = currentDir + filePath.substring(2);
        }
        return filePath;
    }
    
    /**
     * @param args the command line arguments
     * 0                        1       2           3           4       5               6
     * EmailAttachmentSender    subject from_mail   from_pass   to_mail message_file    attach_file
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.util.Map<String, String> env = System.getenv();
        // for (String envName : env.keySet()) {
        //    System.out.format("%s=%s\n", envName, env.get(envName));
        // }
        
        String currentDir = env.get("WORK_DIR");
        // System.out.println("PRO_DIR: " + (currentDir == null ? "NULL" : currentDir) );
        currentDir = (currentDir == null) ? System.getProperty("user.dir") : currentDir;
        System.out.println("Current working dir: " +currentDir);
        // System.exit(1);

        // System.out.println("Browse URL https://www.google.com/settings/security/lesssecureapps to turn on");
        if( args.length < 6 ) {
            System.err.println("Invalid input params");
            System.out.println("Syntax: java -jar JMail.jar subject from_mail from_pass to_mail [message_file or message_text] attach_file");
            System.out.println("Syntax: java -jar JMail.jar \"Test Subject\" \"from@gmail.com\" \"from_pass\" \"to@gmail.com\" \"Body: Hello World\" \"file.txt\"");
            System.exit(1);
        }
        // SMTP info
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = args[1];// "your-email-address";
        String password = args[2];// "your-email-password";
        
        // message info
        String mailTo = args[3];// "your-friend-email";
        String subject = args[0];// "New email with attachments";
        String message = args[4];
        String filePath = "";
        // attachments
        String[] attachFiles = new String[args.length - 5];
        int i;
        for(i=0; i<args.length - 5; i++) {
            filePath = correctPath(args[5+i]);
            attachFiles[i] = (new File(filePath)).getAbsolutePath();
        }
        try {
            message = correctPath(message);
            File file = new File(message);
            if( file.exists() && file.isFile() ) {
                FileReader fileMessage = new FileReader(file);
                char[] msg = new char[ (int)file.length() ];
                int length = fileMessage.read(msg, 0, (int)file.length() );
                message = new String(msg);
            }
        } catch(IOException io) {
            System.out.println("Could not send email. Because can't open file " + message);
            io.printStackTrace();
            System.exit(2);
        }
        

        try {
            sendEmailWithAttachments(host, port, mailFrom, password, mailTo, subject, message, attachFiles);
            System.out.println("Email sent.");
        } catch (AddressException ae) {
            System.err.println("Could not send email. AddressException " + ae.getMessage());
            ae.printStackTrace();
            System.exit(3);
        } catch (MessagingException me) {
            System.err.println("Could not send email. MessagingException " + me.getMessage());
            me.printStackTrace();
            System.exit(4);
        } catch (Exception ex) {
            System.err.println("Could not send email. Exception " + ex.getMessage());
            ex.printStackTrace();
            System.exit(5);
        }
    }    
}
