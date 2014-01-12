/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.communication.utils;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Daud
 */
public class EmailSender {
//    private 

    public static boolean postMail(String recipients[], String subject, String message, String sender) throws AddressException, MessagingException {
        boolean debug = false;
//        String from = "ebokobbi@gmail.com";
        String password = "pear8ones";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.password", password);
        //set the host smtp address 

        //create some properties and get the defaulf session
        Session session = Session.getDefaultInstance(properties, null);
        session.setDebug(debug);

        //Create a message
        Message msg = new MimeMessage(session);
        //set the from and to address
        InternetAddress senderAddress = new InternetAddress(sender);
        msg.setFrom(senderAddress);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);
        msg.addHeader("MyHeader", "myHeaderValue");
        msg.setSentDate(new Date());

        //setting the subject and content type
        msg.setSubject(subject);
        msg.setContent(msg, "text/plain");
        Transport.send(msg);
        return false;

    }

    public static boolean sendGmail() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        return false;


    }

    public static boolean sendYahoomail() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "plus.smtp.mail.yahoo.com");
        return false;
    }
}
