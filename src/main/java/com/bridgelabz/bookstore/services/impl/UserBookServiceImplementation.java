package com.bridgelabz.bookstore.services.impl;

import com.bridgelabz.bookstore.exceptions.BookNotFoundException;
import com.bridgelabz.bookstore.models.AdminBookEntity;
import com.bridgelabz.bookstore.models.UserBookEntity;
import com.bridgelabz.bookstore.models.UserEntity;
import com.bridgelabz.bookstore.repositories.AdminBookRepository;
import com.bridgelabz.bookstore.repositories.UsersBookRepository;
import com.bridgelabz.bookstore.repositories.UserRepository;
import com.bridgelabz.bookstore.responses.MailObject;
import com.bridgelabz.bookstore.services.IUserBookServices;
import com.bridgelabz.bookstore.utility.RabbitMQSender;
import com.bridgelabz.bookstore.utility.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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

    @Autowired
    private RabbitMQSender rabbitMQSender;


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
                .filter (UserBookEntity :: isAddedToCart)
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
    public List<UserBookEntity> getAllBooksFromStore( String token ) {
        userService.getAuthenticateUserWithRoleUser (token);
        return usersBookRepository.findAll ();
    }

    @Override
    public void removeFromBag( String token, String bookCode ) {
        Optional<UserEntity> fetchedAuthenticatedUser = userService.getAuthenticateUserWithRoleUser (token);
        Optional<AdminBookEntity> fetchedValidAdminBook = validBookCheckByBookCode (bookCode);
        UserBookEntity fetchedUserBook = checkAndCreateNewBookForUserAndCopyContent (
                fetchedValidAdminBook.get ().getBookCode (), fetchedValidAdminBook);
//        if book is added then remove from bag
        fetchedUserBook.setAddedToCart (false);
        fetchedUserBook.setUpdateDateTime (Util.currentDateTime ());
        fetchedAuthenticatedUser.get ().getBooksList ().remove (fetchedUserBook);
        usersBookRepository.saveAndFlush (fetchedUserBook);

    }

    Optional<AdminBookEntity> validBookCheckByBookCode( final String bookCode ) throws BookNotFoundException {
        Optional<AdminBookEntity> fetchedBook = adminBookRepository.findOneByBookCode (bookCode);
        if (fetchedBook.isPresent ())
            return fetchedBook;
        throw new BookNotFoundException (Util.BOOK_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND);
    }

    @Override
    public String placeOrder( String token, List<UserBookEntity> orderBooks ) {
        Optional<UserEntity> fetchedAuthenticatedUser = userService.getAuthenticateUserWithRoleUser (token);
        List<AdminBookEntity> fetchedAdminBooks = validateBooksFromAdminList (orderBooks);
//        fetched all user's cart books
        final String orderNumber = Util.generateOrderNumber ();
        for (AdminBookEntity adminBook : fetchedAdminBooks) {
            for (UserBookEntity userBook : orderBooks) {
                if (adminBook.getBookCode ().equals (userBook.getBookCode ())) {
                    buyOperationOnBook (adminBook, orderNumber, userBook);
                    usersBookRepository.saveAndFlush (userBook);
                    adminBookRepository.saveAndFlush (adminBook);
                }
            }
        }
//        sending mail
        if (rabbitMQSender.send (sendOrderConfirmationMail (fetchedAuthenticatedUser.get (), orderBooks, orderNumber))) {
            return orderNumber;
        }
        return "";
    }


    private MailObject sendOrderConfirmationMail( final UserEntity fetchedUser, final List<UserBookEntity> orderBooks, final String orderNumber ) {
        String emailId = fetchedUser.getEmailId ();
        String bodyContent = "";
        for (UserBookEntity book : orderBooks) {
            String bookOrderInfo = "Book code : " + book.getBookCode ()
                    + ",Title : " + book.getTitle ()
                    + ",Price : " + book.getPrice ()
                    + ",Quantity : " + book.getPurchasedQuantity ()
                    + ",Total : " + book.getPrice () * book.getPurchasedQuantity () + ".\n";
            bodyContent += bookOrderInfo;
        }
        String subject = "Order Confirmation Mail, Order number : " + orderNumber;
        return new MailObject (emailId, subject, bodyContent);
    }

    private void buyOperationOnBook( AdminBookEntity adminBook, String orderNumber, UserBookEntity userBook ) {
        if (adminBook.getAvailableQuantity () == userBook.getPurchasedQuantity ()) {
            adminBook.setAvailableQuantity (0);
            adminBook.setOutOfStock (true);
        } else {
            adminBook.setAvailableQuantity (adminBook.getAvailableQuantity () - userBook.getPurchasedQuantity ());
        }
        userBook.setCheckedOut (true);
        userBook.setAddedToCart (false);
        userBook.setOrderNumber (orderNumber);
        userBook.setCheckOutDateTime (Util.currentDateTime ());
        adminBook.setUpdateDateTime (Util.currentDateTime ());
        userBook.setUpdateDateTime (Util.currentDateTime ());
    }

    private List<AdminBookEntity> validateBooksFromAdminList( List<UserBookEntity> orderBooks ) {
        List<AdminBookEntity> fetchedAdminBooks = new LinkedList<> ();
        for (UserBookEntity userBookEntity : orderBooks) {
            Optional<AdminBookEntity> fetchedBook = adminBookRepository.findOneByBookCode (userBookEntity.getBookCode ());
            if (fetchedBook.isPresent ())
                fetchedAdminBooks.add (fetchedBook.get ());
            throw new BookNotFoundException (Util.BOOK_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND);
        }
        return fetchedAdminBooks;
    }


}
