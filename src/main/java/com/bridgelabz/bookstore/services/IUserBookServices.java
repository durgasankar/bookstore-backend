package com.bridgelabz.bookstore.services;

import com.bridgelabz.bookstore.models.UserBookEntity;

import java.util.List;

public interface IUserBookServices {

    boolean isUserBookAddedToBag( String token, long bookId );

    boolean isUserBookAddedToWatchlist( String token, long bookId );

    List<UserBookEntity> getCartList( String token );

    List<UserBookEntity> getWatchlistBooks( String token );

    List<UserBookEntity> getAllBooksFromStore( String token );

    void removeFromBag( String token, String bookCode );

    String placeOrder( String token, List<UserBookEntity> orderBooks );
}
