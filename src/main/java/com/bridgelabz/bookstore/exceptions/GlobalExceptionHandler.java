package com.bridgelabz.bookstore.exceptions;

import com.bridgelabz.bookstore.responses.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global Exception Handler which handles all runtime exceptions like
 * {@link AuthenticationException}, {@link MailSendingException},
 * layer itself.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-01-29
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Response> handelAllAuthenticationExceptions( AuthenticationException e ) {
        return ResponseEntity.status (HttpStatus.UNAUTHORIZED).body (new Response (e.getMessage (), e.getHttpStatus ()));
    }

    @ExceptionHandler(MailSendingException.class)
    public ResponseEntity<Response> handelAllMailSendingExceptions( MailSendingException e ) {
        return ResponseEntity.status (HttpStatus.BAD_GATEWAY).body (new Response (e.getMessage (), e.getHttpStatus ()));
    }

}
