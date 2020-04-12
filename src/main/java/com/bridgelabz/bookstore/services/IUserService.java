package com.bridgelabz.bookstore.services;

import com.bridgelabz.bookstore.dto.UserDto;

public interface IUserService {

    boolean register( UserDto newUserDto );
}
