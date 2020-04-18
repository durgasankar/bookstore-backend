package com.bridgelabz.bookstore.services.impl;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.exceptions.BookNotFoundException;
import com.bridgelabz.bookstore.exceptions.BookStoreException;
import com.bridgelabz.bookstore.exceptions.UserAuthenticationException;
import com.bridgelabz.bookstore.exceptions.UserNotFoundException;
import com.bridgelabz.bookstore.models.AdminBookEntity;
import com.bridgelabz.bookstore.models.UserEntity;
import com.bridgelabz.bookstore.repositories.AdminBookRepository;
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
import java.util.stream.Collectors;

/**
 * This class implements {@link IAdminBookService} interface which has the
 * UnImplemented functionality of registering the user and verifying with the
 * identity and all implementations as carried here.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-14
 * @see {@link UserRepository} for storing user data with the database
 * @see {@link AdminBookRepository} for storing books data with admin access
 * @see {@link JwtTokenProvider} fore creation of token
 */
@Service
public class AdminBookServiceImplementation implements IAdminBookService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminBookRepository adminBookRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

//    @Autowired
//    private AuthenticationManager authenticationManager;


    @Override
    public boolean isBookAddedToStoreByAdmin( BookDto bookDto, String token ) {
        if (isAdminUser (token)) {
            AdminBookEntity newBook = new AdminBookEntity ();
            BeanUtils.copyProperties (bookDto, newBook);
            newBook.setBookCode (Util.idGenerator ());
            newBook.setAdditionDateTime (Util.currentDateTime ());
            adminBookRepository.save (newBook);
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
    boolean isAdminUser( final String token ) {
        Optional<UserEntity> fetchedUser = getAuthenticateUser (token);
        if (fetchedUser.isPresent ()) {
            return fetchedUser.get ().getRole ().contains (Util.ROLE_ADMIN);
        }
        throw new UserNotFoundException (Util.USER_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND);
    }

    /**
     * get the user from the token provided from user repository
     *
     * @param token as String input parameter
     * @return Optional<UserEntity>
     */
    Optional<UserEntity> getAuthenticateUser( final String token ) {
        return userRepository.findOneByUserName (jwtTokenProvider.getUserName (token));
    }

    @Override
    public List<AdminBookEntity> getAllBooksFromStore( String token ) {
        return adminBookRepository.findAll ()
                .stream ()
                .filter (fetchedBook -> !fetchedBook.isRemoved ())
                .collect (Collectors.toList ());
    }

    @Override
    public boolean isRemovedFromStoreByAdmin( long bookId, String token ) throws BookStoreException {
        if (isAdminUser (token)) {
            Optional<AdminBookEntity> fetchedBook = validBook (bookId);
            if (!fetchedBook.get ().isRemoved ()) {
                fetchedBook.get ().setRemoved (true);
                fetchedBook.get ().setUpdateDateTime (Util.currentDateTime ());
                adminBookRepository.save (fetchedBook.get ());
                return true;
            }
            return false;
        }
        throw new UserAuthenticationException ("Oops...User is not admin!", HttpStatus.UNAUTHORIZED);
    }

    /**
     * takes book id as input parameter and returns valid book
     *
     * @param bookId as Long
     * @return Optional<UserBookEntity>
     * @throws BookNotFoundException If book not found
     */
    Optional<AdminBookEntity> validBook( final long bookId ) throws BookNotFoundException {
        Optional<AdminBookEntity> fetchedBook = adminBookRepository.findById (bookId);
        if (fetchedBook.isPresent ())
            return fetchedBook;
        throw new BookNotFoundException (Util.BOOK_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND);
    }


}
