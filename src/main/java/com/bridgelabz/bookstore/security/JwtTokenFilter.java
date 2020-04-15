//package com.bridgelabz.bookstore.security;
//
//import com.bridgelabz.bookstore.exceptions.UserAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    private JwtTokenProvider jwtTokenProvider;
//    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Override
//    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
//        String token = jwtTokenProvider.resolveToken (request);
//        try {
//            if (token != null && jwtTokenProvider.isVerifiedUser (token)) {
//                Authentication auth = jwtTokenProvider.getAuthenticatedUser (token);
//                SecurityContextHolder.getContext ().setAuthentication (auth);
//            }
//        } catch (UserAuthenticationException ex) {
//            //this is very important, since it guarantees the user is not authenticated at all
//            SecurityContextHolder.clearContext ();
//            response.sendError (ex.getHttpStatus ().value (), ex.getMessage ());
//            return;
//        }
//        filterChain.doFilter (request, response);
//    }
//}
