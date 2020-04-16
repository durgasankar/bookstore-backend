package com.bridgelabz.bookstore.services.impl;

import com.bridgelabz.bookstore.models.BookEntity;
import com.bridgelabz.bookstore.models.UserEntity;
import com.bridgelabz.bookstore.repositories.BookRepository;
import com.bridgelabz.bookstore.repositories.UserRepository;
import com.bridgelabz.bookstore.services.IUserBookServices;
import com.bridgelabz.bookstore.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class implements {@link IUserBookServices} interface which has the
 * UnImplemented functionality of registering the user and verifying with the
 * identity and all implementations as carried here.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-13
 * @see {@link BookRepository} for storing book data with the database
 * @see {@link UserRepository} for storing user data with the database
 * @see {@link AdminBookServiceImplementation} for getting admin facilities access
 * @see {@link UserServiceImplementation} for getting user facilities access
 */
@Service
public class UserBookServiceImplementation implements IUserBookServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AdminBookServiceImplementation adminBookService;

    @Autowired
    private UserServiceImplementation userService;


    @Override
    public boolean isUserBookAddedToBag( String token, long bookId ) {
        Optional<BookEntity> fetchedValidBook = adminBookService.validBook (bookId);
        Optional<UserEntity> fetchedAuthenticatedUser = userService.getAuthenticateUserWithRoleUser (token);
//        not added to bag just add it
        if (!fetchedValidBook.get ().isAddedToCart () &&
                !fetchedValidBook.get ().isOutOfStock () &&
                !fetchedValidBook.get ().isRemoved ()) {
            fetchedValidBook.get ().setAddedToCart (true);
            fetchedValidBook.get ().setUpdateDateTime (Util.currentDateTime ());
            fetchedAuthenticatedUser.get ().getBooksList ().add (fetchedValidBook.get ());
            bookRepository.saveAndFlush (fetchedValidBook.get ());
            return true;
        }
//        if book is added then remove from bag
        fetchedValidBook.get ().setAddedToCart (false);
        fetchedValidBook.get ().setUpdateDateTime (Util.currentDateTime ());
        fetchedAuthenticatedUser.get ().getBooksList ().remove (fetchedValidBook.get ());
        bookRepository.saveAndFlush (fetchedValidBook.get ());
        return false;
    }

    @Override
    public boolean isUserBookAddedToWatchlist( String token, long bookId ) {
        Optional<BookEntity> fetchedValidBook = adminBookService.validBook (bookId);
        Optional<UserEntity> fetchedAuthenticatedUser = userService.getAuthenticateUserWithRoleUser (token);
//        if not added to watchlist and not removed by admin
        if (!fetchedValidBook.get ().isAddedToWatchlist () &&
                !fetchedValidBook.get ().isRemoved () ) {
            fetchedValidBook.get ().setAddedToWatchlist (true);
            fetchedValidBook.get ().setUpdateDateTime (Util.currentDateTime ());
            fetchedAuthenticatedUser.get ().getBooksList ().add (fetchedValidBook.get ());
            bookRepository.saveAndFlush (fetchedValidBook.get ());
            return true;
        }
//        already added to watchlist
        fetchedValidBook.get ().setAddedToWatchlist (false);
        fetchedValidBook.get ().setUpdateDateTime (Util.currentDateTime ());
        fetchedAuthenticatedUser.get ().getBooksList ().remove (fetchedValidBook.get ());
        bookRepository.saveAndFlush (fetchedValidBook.get ());
        return false;
    }

    @Override
    public List<BookEntity> getCartList( String token ) {
        return userService.getAuthenticateUserWithRoleUser (token)
                .get ()
                .getBooksList ()
                .stream ()
                .filter (BookEntity :: isAddedToCart)
                .filter (fetchedBook -> fetchedBook.getUpdateDateTime ()
                        .compareTo (Util.currentDateTime ()) < 0 && !fetchedBook.isOutOfStock ())
                .collect (Collectors.toList ());
    }

    @Override
    public List<BookEntity> getWatchlistBooks( String token ) {
        return userService.getAuthenticateUserWithRoleUser (token)
                .get ()
                .getBooksList ()
                .stream ()
                .filter (BookEntity :: isAddedToWatchlist)
                .filter (fetchedBook -> fetchedBook.getUpdateDateTime ()
                        .compareTo (Util.currentDateTime ()) < 0)
                .collect (Collectors.toList ());
    }

    @Override
    public String setPurchasingQuantity( String token, int quantity, long bookId ) {
        Optional<BookEntity> fetchedValidBook = adminBookService.validBook (bookId);
        Optional<UserEntity> fetchedAuthenticatedUser = userService.getAuthenticateUserWithRoleUser (token);
        if (fetchedValidBook.get ().getAvailableQuantity () >= quantity) {
            fetchedValidBook.get ().setAvailableQuantity (fetchedValidBook.get ().getAvailableQuantity () - quantity);
            fetchedValidBook.get ().setPurchasedQuantity (quantity);
            fetchedValidBook.get ().setCheckedOut (true);
            fetchedValidBook.get ().setCheckOutDateTime (Util.currentDateTime ());
            fetchedValidBook.get ().setOrderNumber (Util.idGenerator ());
            bookRepository.saveAndFlush (fetchedValidBook.get ());
            return fetchedValidBook.get ().getOrderNumber ();
        } else if (fetchedValidBook.get ().getAvailableQuantity () == quantity) {
            fetchedValidBook.get ().setAvailableQuantity (fetchedValidBook.get ().getAvailableQuantity () - quantity);
            fetchedValidBook.get ().setPurchasedQuantity (quantity);
            fetchedValidBook.get ().setCheckedOut (true);
            fetchedValidBook.get ().setCheckOutDateTime (Util.currentDateTime ());
            fetchedValidBook.get ().setOrderNumber (Util.idGenerator ());
            fetchedValidBook.get ().setOutOfStock (true);
            bookRepository.saveAndFlush (fetchedValidBook.get ());
            return fetchedValidBook.get ().getOrderNumber ();
        }
        return "";
    }

}
