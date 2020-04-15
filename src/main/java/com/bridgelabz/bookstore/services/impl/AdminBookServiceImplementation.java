package com.bridgelabz.bookstore.services.impl;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.exceptions.UserNotFoundException;
import com.bridgelabz.bookstore.models.BookEntity;
import com.bridgelabz.bookstore.models.Roles;
import com.bridgelabz.bookstore.models.UserEntity;
import com.bridgelabz.bookstore.repositories.BookRepository;
import com.bridgelabz.bookstore.repositories.UserRepository;
import com.bridgelabz.bookstore.security.JwtTokenProvider;
import com.bridgelabz.bookstore.services.IAdminBookService;
import com.bridgelabz.bookstore.utility.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class implements {@link IAdminBookService} interface which has the
 * UnImplemented functionality of registering the user and verifying with the
 * identity and all implementations as carried here.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-14
 * @see {@link UserRepository} for storing user data with the database
 * @see {@link BookRepository} for storing books data with the database
 * @see {@link JwtTokenProvider} fore creation of token
 */
@Service
public class AdminBookServiceImplementation implements IAdminBookService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

//    @Autowired
//    private AuthenticationManager authenticationManager;


    @Override
    public boolean isBookAddedToStore( BookDto bookDto, String token ) {
        if (isAdminUser (token)) {
            BookEntity newBook = new BookEntity ();
            BeanUtils.copyProperties (bookDto, newBook);
            newBook.setAdditionDateTime (Util.currentDateTime ());
            bookRepository.save (newBook);
            return true;
        }
        return false;
    }

    /**
     * Checks whether the user is admin user or not
     *
     * @param token as String input parameter
     * @return Boolean
     */
    private boolean isAdminUser( final String token ) {
        Optional<UserEntity> fetchedUser = getAuthenticateUser (token);
        if (fetchedUser.isPresent ()) {
            if (fetchedUser.get ().getRoles ().contains (Roles.ROLE_ADMIN)) {
                return true;
            }
            return false;
        }
        throw new UserNotFoundException (Util.USER_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND);
    }

    /**
     * get the user from the token provided from user repository
     *
     * @param token as String input parameter
     * @return Optional<UserEntity>
     */
    private Optional<UserEntity> getAuthenticateUser( final String token ) {
        return userRepository.findOneByUserName (jwtTokenProvider.getUserName (token));
    }

    @Override
    public List<BookEntity> getAllBooksFromStore( String token ) {
            return bookRepository.findAll ();
    }
}
