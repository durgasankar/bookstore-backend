package com.bridgelabz.bookstore.utility;

import com.bridgelabz.bookstore.exceptions.MailSendingException;
import com.bridgelabz.bookstore.responses.MailObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ sender class configuration allows us to send a mail by using
 * exchange routing key and mail information.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-02-09
 */
@Component
public class RabbitMQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("rmq.rube.exchange")
    private String exchange;

    @Value("rube.key")
    private String routingKey;

    public boolean send( MailObject mailObject ) throws MailSendingException {
        try {
            rabbitTemplate.convertAndSend (exchange, routingKey, mailObject);
            return true;
        } catch (Exception e) {
            throw new MailSendingException ("Oops... Error sending mail to RabbitMQ server" + e.getStackTrace (), HttpStatus.BAD_GATEWAY);
        }
    }

}