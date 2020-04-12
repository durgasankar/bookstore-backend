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
    private HttpStatus httpStatus;
    private Object object;
    private List list;
    private int statusCode;

    public Response( String message ) {
        this.message = message;
    }

    public Response( String message, int statusCode ) {
        this(message);
        this.statusCode = statusCode;
    }

    public Response( String message, HttpStatus httpStatus ) {
        this( message );
        this.httpStatus = httpStatus;
    }

    public Response( String message, HttpStatus httpStatus, Object object ) {
        this( message, httpStatus);
        this.object = object;
    }

    public Response( String message, HttpStatus httpStatus, List list ) {
        this( message, httpStatus);
        this.list = list;
    }

    public Response( String message, HttpStatus httpStatus, Object object, List list ) {
        this( message, httpStatus, object );
        this.list = list;
    }


    public String getMessage() { return message; }

    public HttpStatus getHttpStatus() { return httpStatus; }

    public Object getObject() { return object; }

    public List getList() { return list; }

    public int getStatusCode() { return statusCode; }
}
