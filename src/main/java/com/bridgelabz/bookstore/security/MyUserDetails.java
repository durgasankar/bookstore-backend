//package com.bridgelabz.bookstore.security;
//
//import com.bridgelabz.bookstore.models.UserEntity;
//import com.bridgelabz.bookstore.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
///**
// * Implements UserDetailsService in order to define our own custom loadUserByUsername function.
// * The UserDetailsService interface is used to retrieve user-related data. It has one method named
// * loadUserByUsername which finds a user entity based on the username and can be overridden to
// * customize the process of finding the user. It is used by the DaoAuthenticationProvider to
// * load details about the user during authentication.
// *
// * @author Durgasankar Mishra
// * @version 1.0
// * @created 2020-04-12
// * @see {@link UserDetailsService} provided services for authentication
// * @see {@link UserRepository} database operations for user
// * @see {@link User} class of spring checks the below functionality of user
// */
//@Service
//public class MyUserDetails implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
//        final Optional<UserEntity> fetchedUser = userRepository.findOneByUserName (username);
//        if (!fetchedUser.isPresent ())
//            throw new UsernameNotFoundException ("User ' " + username + " ' not found");
//        return User.withUsername (fetchedUser.get ().getUserName ())
//                .password (fetchedUser.get ().getPassword ())
//                .authorities (fetchedUser.get ().getRoles ())
//                .accountExpired (false)
//                .accountLocked (false)
//                .credentialsExpired (false)
//                .disabled (false)
//                .build ();
//    }
//}
