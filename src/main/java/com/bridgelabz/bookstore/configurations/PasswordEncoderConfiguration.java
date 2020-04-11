package com.bridgelabz.bookstore.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Password encoder class for project.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-11
 * @see {@link BCryptPasswordEncoder}
 */
@Configuration
public class PasswordEncoderConfiguration {

    /**
     * creates the object of BCryptPasswordEncoder
     *
     * @return object of BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
