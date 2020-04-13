package com.bridgelabz.bookstore.security;

import com.bridgelabz.bookstore.exceptions.AuthenticationException;
import com.bridgelabz.bookstore.models.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * -> Verify the access token's signature
 * -> Extract identity and authorization claims from Access token and use them to create UserContext
 * -> If Access token is malformed, expired or simply if token is not signed with the appropriate
 * signing key Authentication exception will be thrown
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-12
 * @see {@link AuthenticationException} if user is not authenticated
 * @see {@link Authentication} is the user is authenticated
 * @see {@link SimpleGrantedAuthority} defines the granted authorities of the user.
 */
@Component
public class JwtTokenProvider {
    private static final String SECRET_KEY = "r20jc134";
    private static final long VALIDITY_PERIOD_IN_MILLISECOND = 3600000;// 1 hour

    @Autowired
    private MyUserDetails myUserDetails;

    public String createToken( String userName, List<Role> roles ) {
        Claims claims = Jwts.claims ().setSubject (userName);
        claims.put ("auth", roles.stream ()
                .map (role -> new SimpleGrantedAuthority (role.getAuthority ()))
                .filter (Objects :: nonNull)
                .collect (Collectors.toList ()));
        Date now = new Date ();
        Date validity = new Date (now.getTime () + VALIDITY_PERIOD_IN_MILLISECOND);
        return Jwts.builder ()
                .setClaims (claims)
                .setIssuedAt (now)
                .setExpiration (validity)
                .signWith (SignatureAlgorithm.HS256, SECRET_KEY)
                .compact ();
    }

    public boolean isVerifiedUser( String token ) {
        try {
            Jwts.parser ().setSigningKey (SECRET_KEY).parseClaimsJws (token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new AuthenticationException ("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);

        }
    }

    public Authentication getAuthenticatedUser( String token ) {
        UserDetails fetchedUserDetails = myUserDetails.loadUserByUsername (getUserName (token));
        return new UsernamePasswordAuthenticationToken (fetchedUserDetails, "", fetchedUserDetails.getAuthorities ());

    }

    public String getUserName( String token ) {
        return Jwts.parser ()
                .setSigningKey (SECRET_KEY)
                .parseClaimsJws (token)
                .getBody ()
                .getSubject ();
    }

    String resolveToken( HttpServletRequest req ) {
        String bearerToken = req.getHeader ("Authorization");
        if (bearerToken != null && bearerToken.startsWith ("Bearer ")) {
            return bearerToken.substring (7);
        }
        return null;
    }
}
