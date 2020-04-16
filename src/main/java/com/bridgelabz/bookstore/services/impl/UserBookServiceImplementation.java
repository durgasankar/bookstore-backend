package com.bridgelabz.bookstore.services.impl;

import com.bridgelabz.bookstore.models.AdminBookEntity;
import com.bridgelabz.bookstore.models.UserBookEntity;
import com.bridgelabz.bookstore.models.UserEntity;
import com.bridgelabz.bookstore.repositories.AdminBookRepository;
import com.bridgelabz.bookstore.repositories.UsersBookRepository;
import com.bridgelabz.bookstore.repositories.UserRepository;
import com.bridgelabz.bookstore.services.IUserBookServices;
import com.bridgelabz.bookstore.utility.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
 * @see {@link UsersBookRepository} for storing book data with the database
 * @see {@link UserRepository} for storing user data with the database
 * @see {@link AdminBookServiceImplementation} for getting admin facilities access
 * @see {@link UserServiceImplementation} for getting user facilities access
 */
@Service
public class UserBookServiceImplementation implements IUserBookServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersBookRepository usersBookRepository;

    @Autowired
    private AdminBookRepository adminBookRepository;

    @Autowired
    private AdminBookServiceImplementation adminBookService;

    @Autowired
    private UserServiceImplementation userService;


    @Override
    public boolean isUserBookAddedToBag( String token, long bookId ) {
        Optional<AdminBookEntity> fetchedValidAdminBook = adminBookService.validBook (bookId);
        Optional<UserEntity> fetchedAuthenticatedUser = userService.getAuthenticateUserWithRoleUser (token);
        UserBookEntity fetchedUserBook = checkAndCreateNewBookForUserAndCopyContent (
                fetchedValidAdminBook.get ().getBookCode (), fetchedValidAdminBook);
//        not added to bag just add it
        if (!fetchedUserBook.isAddedToCart ()) {
            fetchedUserBook.setAddedToCart (true);
            fetchedUserBook.setUpdateDateTime (Util.currentDateTime ());
            fetchedAuthenticatedUser.get ().getBooksList ().add (fetchedUserBook);
            System.out.println ("Reached here before saving");
            usersBookRepository.save (fetchedUserBook);
            System.out.println ("Reached here after saving");
            return true;
        }
//        if book is added then remove from bag
        fetchedUserBook.setAddedToCart (false);
        fetchedUserBook.setUpdateDateTime (Util.currentDateTime ());
        fetchedAuthenticatedUser.get ().getBooksList ().remove (fetchedUserBook);
        usersBookRepository.saveAndFlush (fetchedUserBook);
        return false;
    }

    private UserBookEntity checkAndCreateNewBookForUserAndCopyContent( final String bookCode,
                                                                       Optional<AdminBookEntity> fetchedAdminBook ) {
        Optional<UserBookEntity> fetchedUserBook = usersBookRepository.findOneByBookCode (bookCode);
        if (fetchedUserBook.isPresent ()) {
            return fetchedUserBook.get ();
        }
        UserBookEntity newUserBookEntity = new UserBookEntity ();
        BeanUtils.copyProperties (fetchedAdminBook.get (), newUserBookEntity);
        System.out.println ("newUserBookEntity :::::::::" + newUserBookEntity);
        System.out.println ("fetchedUserBookEntity :::::::::" + fetchedAdminBook.get ().toString ());
        newUserBookEntity.setBookId (0);
        return newUserBookEntity;
    }

    @Override
    public boolean isUserBookAddedToWatchlist( String token, long bookId ) {
        Optional<AdminBookEntity> fetchedValidAdminBook = adminBookService.validBook (bookId);
        Optional<UserEntity> fetchedAuthenticatedUser = userService.getAuthenticateUserWithRoleUser (token);
        UserBookEntity fetchedUserBook = checkAndCreateNewBookForUserAndCopyContent (
                fetchedValidAdminBook.get ().getBookCode (), fetchedValidAdminBook);
//        if not added to watchlist and not removed by admin
        if (!fetchedUserBook.isAddedToWatchlist ()) {
            fetchedUserBook.setAddedToWatchlist (true);
            fetchedUserBook.setUpdateDateTime (Util.currentDateTime ());
            fetchedAuthenticatedUser.get ().getBooksList ().add (fetchedUserBook);
            usersBookRepository.saveAndFlush (fetchedUserBook);
            return true;
        }
//        already added to watchlist
        fetchedUserBook.setAddedToWatchlist (false);
        fetchedUserBook.setUpdateDateTime (Util.currentDateTime ());
        fetchedAuthenticatedUser.get ().getBooksList ().remove (fetchedUserBook);
        usersBookRepository.saveAndFlush (fetchedUserBook);
        return false;
    }

    @Override
    public List<UserBookEntity> getCartList( String token ) {
        return userService.getAuthenticateUserWithRoleUser (token)
                .get ()
                .getBooksList ()
                .stream ()
//                .filter (UserBookEntity :: isAddedToCart)
                .filter (fetchedBook -> fetchedBook.getUpdateDateTime ()
                        .compareTo (Util.currentDateTime ()) < 0 /*&& !fetchedBook.isOutOfStock ()*/)
                .collect (Collectors.toList ());
    }

    @Override
    public List<UserBookEntity> getWatchlistBooks( String token ) {
        return userService.getAuthenticateUserWithRoleUser (token)
                .get ()
                .getBooksList ()
                .stream ()
                .filter (UserBookEntity :: isAddedToWatchlist)
                .filter (fetchedBook -> fetchedBook.getUpdateDateTime ()
                        .compareTo (Util.currentDateTime ()) < 0)
                .collect (Collectors.toList ());
    }

    @Override
    public String setPurchasingQuantity( String token, int quantity, long bookId ) {
        Optional<AdminBookEntity> fetchedValidAdminBook = adminBookService.validBook (bookId);
        userService.getAuthenticateUserWithRoleUser (token);
        UserBookEntity fetchedUserBook = checkAndCreateNewBookForUserAndCopyContent (
                fetchedValidAdminBook.get ().getBookCode (), fetchedValidAdminBook);
        if (fetchedValidAdminBook.get ().getAvailableQuantity () >= quantity) {
//            updating quantity in admin book
            fetchedValidAdminBook.get ().setAvailableQuantity (fetchedValidAdminBook.get ().getAvailableQuantity () - quantity);
            fetchedUserBook.setPurchasedQuantity (quantity);
            fetchedUserBook.setCheckedOut (true);
            fetchedUserBook.setCheckOutDateTime (Util.currentDateTime ());
            fetchedUserBook.setOrderNumber (Util.generateOrderNumber ());
            usersBookRepository.saveAndFlush (fetchedUserBook);
            adminBookRepository.saveAndFlush (fetchedValidAdminBook.get ());
            return fetchedUserBook.getOrderNumber ();
        } else if (fetchedValidAdminBook.get ().getAvailableQuantity () == quantity && !fetchedValidAdminBook.get ().isOutOfStock ()) {
//            updating quantity in admin book
            fetchedValidAdminBook.get ().setAvailableQuantity (fetchedValidAdminBook.get ().getAvailableQuantity () - quantity);
            fetchedUserBook.setPurchasedQuantity (quantity);
            fetchedUserBook.setCheckedOut (true);
            fetchedUserBook.setCheckOutDateTime (Util.currentDateTime ());
            fetchedUserBook.setOrderNumber (Util.generateOrderNumber ());
            fetchedValidAdminBook.get ().setOutOfStock (true);
            usersBookRepository.save (fetchedUserBook);
            adminBookRepository.saveAndFlush (fetchedValidAdminBook.get ());
            return fetchedUserBook.getOrderNumber ();
        } else {
            return "";
        }
    }

}
