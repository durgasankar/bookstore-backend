package com.bridgelabz.bookstore.utility;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bridgelabz.bookstore.exceptions.MailSendingException;
import com.bridgelabz.bookstore.responses.MailObject;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * This is the configuration mail class which uses SMTP authentication and runs
 * the service in the port 587 and gives the functionality of sending the mail
 * to user. RabbitMQ functionality added to the existing JMS mail service
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-12
 */
@Component
public class EmailServiceProvider {

    /**
     * This function takes following input parameter and configure the
     * authentication of SMTP and port 587 and authorization and send the mail to
     * the assigned user details.
     *
     * @param toEmailId   Sender's id
     * @param subject     subject of mail
     * @param bodyContain body contains of mail
     */
    private boolean sendMail( String toEmailId, String subject, String bodyContain ) {
        Authenticator authentication = new Authenticator () {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication (Util.SENDER_EMAIL_ID, Util.SENDER_PASSWORD);
            }
        };
        Session session = Session.getInstance (mailPropertiesSettings (), authentication);
        try {
            Transport.send (mimeMessageConfiguration (session, toEmailId, subject, bodyContain));
            return true;
        } catch (MessagingException e) {
            throw new MailSendingException ("Oops...Error Sending mail to Server!", HttpStatus.BAD_GATEWAY);
        }
    }

    /**
     * This function takes following information and sets all the header information
     * of the email.
     *
     * @param session as Current session information
     * @param toEmail as String receiver's mail id
     * @param subject as String input parameter
     * @param body    as String input parameter
     * @return MimeMessage
     */
    private MimeMessage mimeMessageConfiguration( Session session, String toEmail, String subject, String body ) {

        MimeMessage mimeMessage = new MimeMessage (session);
        // set message headers
        try {
            mimeMessage.addHeader ("Content-type", "text/HTML; charset=UTF-8");
            mimeMessage.addHeader ("format", "flowed");
            mimeMessage.addHeader ("Content-Transfer-Encoding", "8bit");
            mimeMessage.setFrom (new InternetAddress (Util.SENDER_EMAIL_ID, "Book Store Application"));
            mimeMessage.setReplyTo (InternetAddress.parse (Util.SENDER_EMAIL_ID, false));
            mimeMessage.setSubject (subject, "UTF-8");
            mimeMessage.setText (body, "UTF-8");
            mimeMessage.setSentDate (new Date ());
            mimeMessage.setRecipients (Message.RecipientType.TO, InternetAddress.parse (toEmail, false));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace ();
        }
        return mimeMessage;
    }

    /**
     * This class sets the properties configuration of the mail and return it.
     *
     * @return Properties class
     */
    private Properties mailPropertiesSettings() {
        Properties properties = new Properties ();
        properties.put ("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
        properties.put ("mail.smtp.port", "587"); // TLS Port
        properties.put ("mail.smtp.auth", "true"); // enable authentication
        properties.put ("mail.smtp.starttls.enable", "true"); // enable STARTTLS
        return properties;
    }

    /**
     * This function fetch the mail server based on key of the {@link Queue} it send
     * to the RabbitMQ Server.
     *
     * @param mailObject as {@link MailObject}
     * @throws MailSendingException custom exception
     */
    @RabbitListener(queues = "rmq.rube.queue")
    public void receivedMessage( MailObject mailObject ) throws MailSendingException {

        if (sendMail (mailObject.getEmail (), mailObject.getSubject (), mailObject.getMessage ()))
            return;
        throw new MailSendingException ("Oops...Error Receiving mail from RabbitMQ server", HttpStatus.BAD_GATEWAY);
    }
}
