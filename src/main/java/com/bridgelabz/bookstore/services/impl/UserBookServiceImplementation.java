package com.bridgelabz.bookstore.services.impl;

import com.bridgelabz.bookstore.models.BookEntity;
import com.bridgelabz.bookstore.models.UserEntity;
import com.bridgelabz.bookstore.repositories.BookRepository;
import com.bridgelabz.bookstore.repositories.UserRepository;
import com.bridgelabz.bookstore.security.JwtTokenProvider;
import com.bridgelabz.bookstore.services.IUserBookServices;
import com.bridgelabz.bookstore.services.IUserService;
import com.bridgelabz.bookstore.utility.RabbitMQSender;
import com.bridgelabz.bookstore.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

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
//        not added just add it
        if (!fetchedValidBook.get ().isAddedToCart ()) {
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

}
