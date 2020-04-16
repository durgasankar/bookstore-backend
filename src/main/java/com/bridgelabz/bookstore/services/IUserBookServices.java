package com.bridgelabz.bookstore.services;

public interface IUserBookServices {

    boolean isUserBookAddedToBag( String token, long bookId );

}
