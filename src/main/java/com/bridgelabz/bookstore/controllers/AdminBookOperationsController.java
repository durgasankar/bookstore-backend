package com.bridgelabz.bookstore.controllers;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.responses.Response;
import com.bridgelabz.bookstore.services.IAdminBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 * @see {@link IAdminBookService} implementation of all the required functionality
 * @see {@link Response} if there is any type of response it will reflect out
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminBookOperationsController {

    @Autowired
    private IAdminBookService adminBookService;

    @PostMapping("/add")
    public ResponseEntity<Response> addBookToStore( @RequestBody final BookDto bookDto, @RequestHeader("token") final String token ) {
        boolean isAdded = adminBookService.isBookAddedToStoreByAdmin (bookDto, token);
        if (!isAdded)
            return ResponseEntity.status (HttpStatus.BAD_REQUEST)
                    .body (new Response ("Oops...Error adding book to the store!", 400));
        return ResponseEntity.status (HttpStatus.CREATED)
                .body (new Response ("Book added successfully!", 201));
    }

    @GetMapping("/get")
    public ResponseEntity<Response> getAllBooks( @RequestHeader("token") final String token ) {
            return ResponseEntity.ok ()
                    .body (new Response ("Books are : ", HttpStatus.OK, adminBookService.getAllBooksFromStore (token)));

    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Response> removeBookByAdmin( @RequestHeader("token") final String token, @PathVariable("id") final long bookId ) {
        boolean isRemoved = adminBookService.isRemovedFromStoreByAdmin (bookId, token);
        if (!isRemoved)
            return ResponseEntity.status (HttpStatus.BAD_REQUEST)
                    .body (new Response ("Oops...Book Already removed from Store!", 400));
        return ResponseEntity.ok ()
                .body (new Response ("Book removed successfully!", 200));
    }
}
