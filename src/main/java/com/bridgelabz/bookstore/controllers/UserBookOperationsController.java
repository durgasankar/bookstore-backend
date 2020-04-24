package com.bridgelabz.bookstore.controllers;

import com.bridgelabz.bookstore.responses.Response;
import com.bridgelabz.bookstore.services.IUserBookServices;
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
 * @see {@link IUserBookServices} implementation of all the required functionality
 * @see {@link Response} if there is any type of response it will reflect out
 */
@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:4200")
public class UserBookOperationsController {

    @Autowired
    private IUserBookServices userBookServices;

    @PutMapping("/cart")
    public ResponseEntity<Response> addOrRemoveBookFromBag( @RequestHeader("token") final String token,
                                                            @RequestParam("id") final long bookId ) {
        boolean isAddedToBag = userBookServices.isUserBookAddedToBag (token, bookId);
        if (!isAddedToBag)
            return ResponseEntity.status (HttpStatus.ACCEPTED)
                    .body (new Response ("removed from cart!", 202));
        return ResponseEntity.ok ()
                .body (new Response ("added to cart successfully!", 200));
    }

    @PutMapping("/watchlist")
    public ResponseEntity<Response> addOrRemoveBookFromWatchList( @RequestHeader("token") final String token,
                                                                  @RequestParam("id") final long bookId ) {
        boolean isAddedToWatchList = userBookServices.isUserBookAddedToWatchlist (token, bookId);
        if (!isAddedToWatchList)
            return ResponseEntity.status (HttpStatus.ACCEPTED)
                    .body (new Response ("removed from watchlist!", 202));
        return ResponseEntity.ok ()
                .body (new Response ("added to watchlist.", 200));
    }

    @GetMapping("/cart")
    public ResponseEntity<Response> getAllCartBooks( @RequestHeader("token") final String token ) {
        return ResponseEntity.ok
                (new Response ("Cart books : ", HttpStatus.OK, userBookServices.getCartList (token)));
    }

    @GetMapping("/watchlist")
    public ResponseEntity<Response> getAllWatchlistBooks( @RequestHeader("token") final String token ) {
        return ResponseEntity.ok
                (new Response ("Watchlist books : ", HttpStatus.OK, userBookServices.getWatchlistBooks (token)));
    }

    @PutMapping("/checkout")
    public ResponseEntity<Response> setQuantityAndCheckout( @RequestHeader("token") final String token,
                                                            @RequestParam("quantity") final int quantity,
                                                            @RequestParam("id") final long bookId ) {
        String orderNumber = userBookServices.setPurchasingQuantity (token, quantity, bookId);
        if (!orderNumber.isEmpty ()) {
            return ResponseEntity
                    .ok (new Response ("Order processed Successfully!", HttpStatus.OK, orderNumber));
        }
        return ResponseEntity.badRequest ()
                .body (new Response ("Oops... Error processing order!", HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/get")
    public ResponseEntity<Response> getAllUserBooksOfUser(@RequestHeader("token") final String token){
        return ResponseEntity
                .ok (new Response ("Books are : ", HttpStatus.OK, userBookServices.getAllBooksFromStore (token)));
    }

}
