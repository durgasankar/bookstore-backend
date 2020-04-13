package com.bridgelabz.bookstore.services;

import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.exceptions.UserNotFoundException;
import com.bridgelabz.bookstore.models.UserEntity;
import com.bridgelabz.bookstore.repositories.UserRepository;
import com.bridgelabz.bookstore.responses.MailObject;
import com.bridgelabz.bookstore.security.JwtTokenProvider;
import com.bridgelabz.bookstore.utility.RabbitMQSender;
import com.bridgelabz.bookstore.utility.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * This class implements {@link IUserService} interface which has the
 * UnImplemented functionality of registering the user and verifying with the
 * identity and all implementations as carried here.
 *
 * @author Durgasankar Mishra
 * @version 1.1
 * @created 2020-01-22
 * @modified -> 2020-02-09
 * @updated -> RabbitMQ functionality added to the existing JMS mail service.
 * @updated -> 2020-04-02
 * @modified -> added extra field address on registration form.
 * @see {@link PasswordEncoder} for creating encrypted password
 * @see {@link UserRepository} for storing data with the database
 * @see {@link JwtTokenProvider} fore creation of token
 * @see {@link RabbitMQSender} for mail facilities
 */
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
    public boolean register( UserDto newUserDto ) throws UserNotFoundException {
        if (!userRepository.existsByUserName (newUserDto.getUserName ())) {
            UserEntity newUser = new UserEntity ();
            BeanUtils.copyProperties (newUserDto, newUser);
            System.out.println ("newUserDto = " + newUserDto);
            System.out.println ("newUser = " + newUser);
            newUser.setCreatedDateTime (LocalDateTime.now ().format (DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm:ss")));
            newUser.setVerified (false);
            newUser.setPassword (passwordEncoder.encode (newUser.getPassword ()));
            userRepository.save (newUser);
//            fetching the user from data base again to send verification mail
            Optional<UserEntity> fetchedUser = userRepository.findOneByUserName (newUser.getUserName ());
            if (fetchedUser.isPresent ()) {
                rabbitMQSender.send (mailContent (fetchedUser.get ()));
                return true;
            }
            throw new UserNotFoundException (Util.USER_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND);
        }
        return false;
    }

    private MailObject mailContent( final UserEntity fetchedUser ) {
        String emailId = fetchedUser.getEmailId ();
        String bodyContent = Util.createLink (
                Util.IP_ADDRESS + Util.SPRING_PORT_NUMBER + Util.REGISTRATION_VERIFICATION_LINK,
                jwtTokenProvider.createToken (fetchedUser.getUserName (), fetchedUser.getRoles ()));
        String subject = Util.REGISTRATION_EMAIL_SUBJECT;
        return new MailObject (emailId, subject, bodyContent);
    }

    @Override
    public  boolean isVerifiedUser( final String token ) {
       userRepository.verifyTheUser(jwtTokenProvider.getUserName (token));
        return true;
    }

}
