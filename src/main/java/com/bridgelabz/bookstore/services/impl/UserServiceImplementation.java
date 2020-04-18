package com.bridgelabz.bookstore.services.impl;

import com.bridgelabz.bookstore.dto.AddressDto;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.exceptions.BookStoreException;
import com.bridgelabz.bookstore.exceptions.InvalidCredentialsException;
import com.bridgelabz.bookstore.exceptions.UserAuthenticationException;
import com.bridgelabz.bookstore.exceptions.UserNotFoundException;
import com.bridgelabz.bookstore.models.Address;
import com.bridgelabz.bookstore.models.UserEntity;
import com.bridgelabz.bookstore.repositories.AddressRepository;
import com.bridgelabz.bookstore.repositories.UserRepository;
import com.bridgelabz.bookstore.responses.MailObject;
import com.bridgelabz.bookstore.security.JwtTokenProvider;
import com.bridgelabz.bookstore.services.IUserService;
import com.bridgelabz.bookstore.utility.RabbitMQSender;
import com.bridgelabz.bookstore.utility.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class implements {@link IUserService} interface which has the
 * UnImplemented functionality of registering the user and verifying with the
 * identity and all implementations as carried here.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-13
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

    @Autowired
    private AddressRepository addressRepository;

//    @Autowired
//    private AuthenticationManager authenticationManager;


    @Override
    public boolean register( UserDto newUserDto ) throws UserNotFoundException {
        if (!userRepository.existsByUserName (newUserDto.getUserName ())) {
            UserEntity newUser = new UserEntity ();
            BeanUtils.copyProperties (newUserDto, newUser);
            newUser.setCreatedDateTime (Util.currentDateTime ());
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
                Util.IP_ADDRESS + Util.ANGULAR_PORT_NUMBER + Util.REGISTRATION_VERIFICATION_LINK,
                jwtTokenProvider.createToken (fetchedUser.getUserName (), fetchedUser.getRole ()));
        String subject = Util.REGISTRATION_EMAIL_SUBJECT;
        return new MailObject (emailId, subject, bodyContent);
    }

    @Override
    public boolean isVerifiedUser( final String token ) {
        userRepository.verifyTheUser (jwtTokenProvider.getUserName (token));
        return true;
    }

    @Override
    public UserLoginInfo login( LoginDto loginDto ) throws UserNotFoundException, InvalidCredentialsException {
        /*login operation with spring security*/

//        try {
//            authenticate the user and get the authenticated username and data
//            Authentication authenticationUserInfo = authenticationManager.authenticate (
//                    new UsernamePasswordAuthenticationToken (loginDto.getUserName (), loginDto.getPassword ()));
//            Optional<UserEntity> fetchedUser = userRepository.findOneByUserName (loginDto.getUserName ());
//            System.out.println ("name : " + authenticationUserInfo.getName ()); //username
//            System.out.println ("fetched user : " + fetchedUser);
//            user valid and verified
//        if (fetchedUser.get ().isVerified ()) {
//                String createdToken = jwtTokenProvider.createToken (fetchedUser.get ().getUserName (), fetchedUser.get ().getRoles ());
//                return new UserLoginInfo (createdToken, fetchedUser.get ().getFirstName ());
//            }
//            user is valid but not verified.
//            rabbitMQSender.send (mailContent (fetchedUser.get ()));
//            return new UserLoginInfo ("", fetchedUser.get ().getFirstName ());
//        } catch (AuthenticationException e) {
//            throw new UserAuthenticationException ("Oops...Invalid username/password supplied!", HttpStatus.UNPROCESSABLE_ENTITY);
//        }


        /*login operation after disabling spring security*/
        Optional<UserEntity> fetchedUser = userRepository.findOneByUserName (loginDto.getUserName ());
//        fetched user present
        if (fetchedUser.isPresent ()) {
//            password matches
            if (passwordEncoder.matches (loginDto.getPassword (), fetchedUser.get ().getPassword ())) {
//                verified
                if (fetchedUser.get ().isVerified ()) {
                    String createdToken = jwtTokenProvider.createToken (fetchedUser.get ().getUserName (), fetchedUser.get ().getRole ());
                    return new UserLoginInfo (createdToken, fetchedUser.get ().getFirstName (), fetchedUser.get ().getRole ());
                }
//                user is valid but not verified.
                rabbitMQSender.send (mailContent (fetchedUser.get ()));
                return new UserLoginInfo ("", fetchedUser.get ().getFirstName ());
            }
//            password dint match
            throw new InvalidCredentialsException ("Oops...Invalid username/password supplied!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
//        not found
        throw new UserNotFoundException (Util.USER_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND);
    }

    @Override
    public boolean isUserAddressAdded( AddressDto addressDto, String token ) throws BookStoreException {
        Optional<UserEntity> fetchedUser = getAuthenticateUserWithRoleUser (token);
        Address newAddress = new Address ();
        BeanUtils.copyProperties (addressDto, newAddress);
        fetchedUser.get ().getAddresses ().add (newAddress);
        addressRepository.saveAndFlush (newAddress);
        return true;
    }

    /**
     * get the user from the token provided from user repository and
     *
     * @param token as String input parameter
     * @return Optional<UserEntity>
     */
    Optional<UserEntity> getAuthenticateUserWithRoleUser( final String token )
            throws UserAuthenticationException, UserNotFoundException {
        Optional<UserEntity> fetchedUser = userRepository.findOneByUserName (jwtTokenProvider.getUserName (token));
        if (fetchedUser.isPresent ()) {
            if (fetchedUser.get ().getRole ().contains (Util.ROLE_USER)) {
                return fetchedUser;
            }
            throw new UserAuthenticationException ("Oops...User is not authorized for Operation!", HttpStatus.UNAUTHORIZED);
        }
        throw new UserNotFoundException (Util.USER_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND);
    }

    @Override
    public boolean isUserAddressRemoved( String addressId, String token ) throws BookStoreException {
        Optional<UserEntity> fetchedUser = getAuthenticateUserWithRoleUser (token);
        addressRepository.removeAddress (addressId, fetchedUser.get ().getUserId ());
        return true;
    }

    @Override
    public List<Address> getAllAddressOfUser( String token ) {
        return getAuthenticateUserWithRoleUser (token).get ().getAddresses ();
    }

}
