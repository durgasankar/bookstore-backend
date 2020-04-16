package com.bridgelabz.bookstore.controllers;

import com.bridgelabz.bookstore.responses.Response;
import com.bridgelabz.bookstore.services.IUserBookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * By using the object reference of service class This class has the
 * functionality of getting connected with the user on the user specific request
 * it will redirect to the respective controller method and carry out
 * functionality of that particular request.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-16
 * @see {@link IUserBookServices} implementation of all the required functionality
 * @see {@link Response} if there is any type of response it will reflect out
 */
@RestController
@RequestMapping("books")
public class UserBookOperationsController {

    @Autowired
   private IUserBookServices userBookServices;

    @PutMapping("/cart/{id}")
    public ResponseEntity<Response> addBookToBag( @RequestHeader("token") final String token, @RequestParam("id") final long bookId ) {
        boolean isAddedToBag = userBookServices.isUserBookAddedToBag (token, bookId);
        if (!isAddedToBag) {
            return ResponseEntity.badRequest ()
                    .body (new Response ("Oops...Error adding book to bag!", 400));
        }
        return ResponseEntity.ok ()
                .body (new Response ("Book added to cart successfully!", 200));
    }

}
