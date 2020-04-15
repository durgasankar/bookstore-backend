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
