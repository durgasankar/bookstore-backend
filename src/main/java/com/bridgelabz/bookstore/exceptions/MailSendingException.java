package com.bridgelabz.bookstore.exceptions;

import org.springframework.http.HttpStatus;

/**
 * This class extends {@link BookStoreException} and creates a custom exception
 * which would be thrown during the user's abnormal activity while performing
 * mail sending operations.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-12
 */
public class MailSendingException extends BookStoreException {

    /**
     * Constructor takes message and Status code as input parameter and fetch
     * message from its superclass.
     *
     * @param message    as String input parameter
     * @param httpStatus as Integer input parameter
     */
    public MailSendingException( String message, HttpStatus httpStatus ) {
        super (message, httpStatus);
    }
}
