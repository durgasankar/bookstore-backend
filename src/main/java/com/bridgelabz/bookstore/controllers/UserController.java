package com.bridgelabz.bookstore.controllers;

import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.responses.Response;
import com.bridgelabz.bookstore.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * By using the object reference of service class This class has the
 * functionality of getting connected with the user on the user specific request
 * it will redirect to the respective controller method and carry out
 * functionality of that particular request.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-13
 * @see {@link IUserService} implementation of all the required functionality*
 * @see {@link Response} if there is any type of response it will reflect out
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Response> registration( @RequestBody UserDto userDto ) {
        System.out.println ("userDto controller  = " + userDto);
        boolean isRegistered = userService.register (userDto);
        if (!isRegistered) {
            return ResponseEntity.status (HttpStatus.ALREADY_REPORTED)
                    .body (new Response ("User already exist... please login!", 208));
        }
        return ResponseEntity.status (HttpStatus.CREATED)
                .body (new Response ("Registration successful... check your mail for verification!", 201));
    }


}
