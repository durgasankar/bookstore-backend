package com.bridgelabz.bookstore.services;

import com.bridgelabz.bookstore.dto.AddressDto;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.exceptions.BookStoreException;
import com.bridgelabz.bookstore.exceptions.InvalidCredentialsException;
import com.bridgelabz.bookstore.exceptions.UserNotFoundException;
import com.bridgelabz.bookstore.models.Address;
import com.bridgelabz.bookstore.services.impl.UserLoginInfo;

import java.util.List;

/**
 * This interface has the UnImplemented functionality of registering the user
 * and verifying with the identity, login, forget password and update password
 * functionality.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-13
 */
public interface IUserService {

    boolean register( UserDto newUserDto ) throws UserNotFoundException;

    boolean isVerifiedUser( String token );

    UserLoginInfo login( LoginDto loginDto ) throws UserNotFoundException, InvalidCredentialsException;

    boolean isUserAddressAdded( AddressDto addressDto, String token ) throws BookStoreException;

    boolean isUserAddressRemoved( String addressId, String token ) throws BookStoreException;

    List<Address> getAllAddressOfUser( String token );
}
