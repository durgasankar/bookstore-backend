package com.bridgelabz.bookstore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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
                .antMatchers( "/configuration/**" )
                .antMatchers( "/public" );
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.csrf ().disable ();
        http.sessionManagement ()
                .sessionCreationPolicy (SessionCreationPolicy.STATELESS);
//        allowed all request with full permission
        http.authorizeRequests ()
                .antMatchers ("/users/registration").permitAll ()
                .antMatchers ("/h2-console/**/**").permitAll ()
                .anyRequest ()
                .authenticated ();

//         If a user try to access a resource without having enough permissions
        http.exceptionHandling().accessDeniedPage("/login");

        http.apply(new JwtTokenFilterConfigures (jwtTokenProvider));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




}
