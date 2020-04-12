package com.bridgelabz.bookstore.security;

import com.bridgelabz.bookstore.models.UserEntity;
import com.bridgelabz.bookstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implements UserDetailsService in order to define our own custom loadUserByUsername function.
 * The UserDetailsService interface is used to retrieve user-related data. It has one method named
 * loadUserByUsername which finds a user entity based on the username and can be overridden to
 * customize the process of finding the user. It is used by the DaoAuthenticationProvider to
 * load details about the user during authentication.
 *
 * @author Durgasankar Mishra
 * @created 2020-04-12
 * @version 1.0
 * @see {@link UserDetailsService} provided services for authentication
 * @see {@link UserRepository} database operations for user
 * @see {@link User} class of spring checks the below functionality of user
 */
@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        final UserEntity fetchedUser = userRepository.findOneByUserName( username );
        if (fetchedUser == null)
            throw new UsernameNotFoundException( "User ' " + username + " ' not found" );
        return User.withUsername( fetchedUser.getUserName() )
                .password( fetchedUser.getPassword() )
                .authorities( fetchedUser.getRoles() )
                .accountExpired( false )
                .accountLocked( false )
                .credentialsExpired( false )
                .disabled( false )
                .build();
    }
}
