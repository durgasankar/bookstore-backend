package com.bridgelabz.bookstore.controllers;

import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.models.UserEntity;
import com.bridgelabz.bookstore.responses.LoginResponse;
import com.bridgelabz.bookstore.responses.Response;
import com.bridgelabz.bookstore.services.IUserService;
import com.bridgelabz.bookstore.services.UserLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<Response> registration( @RequestBody final UserDto userDto ) {
        boolean isRegistered = userService.register (userDto);
        if (!isRegistered) {
            return ResponseEntity.status (HttpStatus.ALREADY_REPORTED)
                    .body (new Response ("User already exist... please login!", 208));
        }
        return ResponseEntity.status (HttpStatus.CREATED)
                .body (new Response ("Registration successful... check your mail for verification!", 201));
    }

    @PutMapping("/verification/{token}")
    public ResponseEntity<Response> verifyRegistration( @PathVariable("token") final String token ) {
        if (userService.isVerifiedUser (token)) {
            return ResponseEntity.status (HttpStatus.OK)
                    .body (new Response ("account verified successfully.", 200));
        }
        return ResponseEntity.status (HttpStatus.NOT_ACCEPTABLE)
                .body (new Response ("Invalid verification attempt", 406));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login( @RequestBody final LoginDto loginDto ) {
        UserLoginInfo userLoginInfo = userService.login (loginDto);
        if (!userLoginInfo.getToken ().isEmpty ()) {
            return ResponseEntity.status (HttpStatus.OK)
                    .body (new LoginResponse ("Login Successful!", 200, userLoginInfo));
        }
        return ResponseEntity.status (HttpStatus.NON_AUTHORITATIVE_INFORMATION)
                .body (new LoginResponse ("Oops...user not verified. Check your mail for verification!", 203, userLoginInfo));
    }


}
