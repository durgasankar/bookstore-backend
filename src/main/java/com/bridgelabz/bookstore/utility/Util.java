package com.bridgelabz.bookstore.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * This singleton class has the all the reusable methods => createLink
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-13
 */
public class Util {

    private Util() {

    }

    private static Random random = new Random();

    public static final String IP_ADDRESS = "http://localhost:";
    public static final String SENDER_EMAIL_ID = "jc100.r20jc134@gmail.com";
    public static final String SENDER_PASSWORD = "r20jc134";
    public static final String ANGULAR_PORT_NUMBER = "4200";
    public static final String SPRING_PORT_NUMBER = "8081";
    public static final String REGISTRATION_VERIFICATION_LINK = "/verification";
    public static final String REGISTRATION_EMAIL_SUBJECT = "Registration Verification Link";
    public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "Oops...User not found!";
    public static final String BOOK_NOT_FOUND_EXCEPTION_MESSAGE = "Oops...Book not found!";
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";


    /**
     * this function just concatenate two parameter.
     *
     * @param url   as String input parameter
     * @param token as String input parameter
     * @return String
     */
    public static String createLink( String url, String token ) {
        return url + "/" + token;
    }

    public static String currentDateTime() {
        return LocalDateTime.now ().format (DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * This function generate random id.
     *
     * @return String data
     */
    public static String idGenerator() {
        return UUID.randomUUID ().toString ();
    }

    /**
     * This function generate order id
     *
     * @return String data
     */
    public static String generateOrderNumber(){
        String inputId = "#Du";
        inputId += random.nextInt (10000000);
        return inputId;
    }

    public static byte[] compressBytes( byte[] bytes ) {
        Deflater deflater = new Deflater ();
        deflater.setInput (bytes);
        deflater.finish ();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream (bytes.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished ()) {
            int count = deflater.deflate (buffer);
            outputStream.write (buffer, 0, count);
        }
        try {
            outputStream.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        System.out.println ("compressed size : " + outputStream.toByteArray ().length);
        return outputStream.toByteArray ();
    }

    public static byte[] deCompressBytes( byte[] bytes ) {
        Inflater inflater = new Inflater ();
        inflater.setInput (bytes);
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream (bytes.length);
        try {
            while (!inflater.finished ()) {
                int count = inflater.inflate (buffer);
                outputStream.write (buffer, 0, count);
            }
        } catch (DataFormatException e) {
            e.printStackTrace ();
        }
        return outputStream.toByteArray ();
    }

}
