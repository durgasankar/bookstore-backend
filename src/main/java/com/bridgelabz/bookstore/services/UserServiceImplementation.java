package com.bridgelabz.bookstore.services;

import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.exceptions.UserAlreadyPresentException;
import com.bridgelabz.bookstore.models.UserEntity;
import com.bridgelabz.bookstore.repositories.UserRepository;
import com.bridgelabz.bookstore.security.JwtTokenProvider;
import com.bridgelabz.bookstore.utility.RabbitMQSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

@Service
public class UserServiceImplementation implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RabbitMQSender rabbitMQSender;


    @Override
    public boolean register( UserDto newUserDto ) {
        if (!userRepository.existsByUserName (newUserDto.getUserName ())) {
            UserEntity newUser = new UserEntity ();
            BeanUtils.copyProperties (newUserDto, newUser);
            System.out.println ("newUserDto = " + newUserDto);
            System.out.println ("newUser = " + newUser);
            newUser.setCreatedDateTime (LocalDateTime.now ().format (DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm:ss")));
            newUser.setVerified (false);
            newUser.setPassword (passwordEncoder.encode (newUser.getPassword ()));
            userRepository.save (newUser);
            return true;
        }
        return false;
    }
}
