package com.bridgelabz.bookstore.services;

import com.bridgelabz.bookstore.models.BookEntity;

import java.util.List;

public interface IUserBookServices {

    boolean isUserBookAddedToBag( String token, long bookId );

    boolean isUserBookAddedToWatchlist( String token, long bookId );

    List<BookEntity> getCartList( String token );

    List<BookEntity> getWatchlistBooks( String token );

    String setPurchasingQuantity( String token, int quantity, long bookId );
}
