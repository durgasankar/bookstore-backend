package com.bridgelabz.bookstore.services;

import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.exceptions.UserNotFoundException;

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

    UserLoginInfo login( LoginDto loginDto );
}
