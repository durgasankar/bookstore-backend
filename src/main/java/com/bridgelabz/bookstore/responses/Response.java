package com.bridgelabz.bookstore.responses;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * This class has the functionality of capturing response of user which would be
 * recorded when user does any type of operation.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-12
 */
public class Response {

    private final String message;
    private HttpStatus statusCode;
    private Object object;
    private List list;

    public Response( String message ) {
        this.message = message;
    }

    public Response( String message, HttpStatus statusCode ) {
        this( message );
        this.statusCode = statusCode;
    }

    public Response( String message, HttpStatus statusCode, Object object ) {
        this( message, statusCode );
        this.object = object;
    }

    public Response( String message, HttpStatus statusCode, List list ) {
        this( message, statusCode );
        this.list = list;
    }

    public Response( String message, HttpStatus statusCode, Object object, List list ) {
        this( message, statusCode, object );
        this.list = list;
    }

    public String getMessage() { return message; }

    public HttpStatus getStatusCode() { return statusCode; }

    public Object getObject() { return object; }

    public List getList() { return list; }
}
