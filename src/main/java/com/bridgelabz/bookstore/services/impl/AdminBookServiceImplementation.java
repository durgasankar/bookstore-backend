package com.bridgelabz.bookstore.services.impl;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.models.BookEntity;
import com.bridgelabz.bookstore.repositories.BookRepository;
import com.bridgelabz.bookstore.repositories.UserRepository;
import com.bridgelabz.bookstore.security.JwtTokenProvider;
import com.bridgelabz.bookstore.services.IAdminBookService;
import com.bridgelabz.bookstore.utility.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean isBookAddedToStore( BookDto bookDto ) {
        BookEntity newBook = new BookEntity ();
        BeanUtils.copyProperties (bookDto, newBook);
        newBook.setAdditionDateTime (Util.currentDateTime ());
        bookRepository.save (newBook);
        return true;
    }
}
