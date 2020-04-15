package com.bridgelabz.bookstore.controllers;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.responses.Response;
import com.bridgelabz.bookstore.services.IAdminBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminBookOperationsController {

    @Autowired
    private IAdminBookService adminBookService;

    @PostMapping("/add")
    public ResponseEntity<Response> registration( @RequestBody final BookDto bookDto, @RequestHeader("token") final String token ) {
        boolean isAdded = adminBookService.isBookAddedToStore (bookDto, token);
        if (!isAdded)
            return ResponseEntity.status (HttpStatus.BAD_REQUEST)
                    .body (new Response ("Oops...Error adding book to the store!", 400));
        return ResponseEntity.status (HttpStatus.CREATED)
                .body (new Response ("Book added successfully!", 201));
    }
}
