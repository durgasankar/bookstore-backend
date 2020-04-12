package com.bridgelabz.bookstore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure( WebSecurity web ) throws Exception {
//       allow swagger to access without authentication
//        /v2/api-docs URL ->  that SpringFox uses for documentation.
//        /swagger-resources/**" -> listing of all apis
        web.ignoring()
                .antMatchers( "/swagger-ui.html" )
                .antMatchers( "/v2/api-docs" )
                .antMatchers( "/swagger-resources/**" )
                .antMatchers( "/webjars/**" )
                .antMatchers( "/configurations/**" )
                .antMatchers( "/public" );
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated();
        http.exceptionHandling()
                .accessDeniedPage( "/login" );
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
