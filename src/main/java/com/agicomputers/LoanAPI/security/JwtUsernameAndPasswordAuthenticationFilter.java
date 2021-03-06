package com.agicomputers.LoanAPI.security;

import com.agicomputers.LoanAPI.models.request.AppUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

//@Configuration
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            //Map to UsernameAndPasswordAuthenticationRequest using ObjectMapper
            AppUserRequest authenticationRequest =
                    new ObjectMapper().readValue(request.getInputStream(), AppUserRequest.class);

            //Create an Authentication Token to be authenticated
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );

            //Authenticate
            return authenticationManager.authenticate(authenticationToken);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        String token = Jwts.builder().setSubject(authResult.getName())
                .claim("authorities",authResult.getAuthorities())
                .setIssuedAt(new Date()) // for example, now
                .setExpiration(SecurityConstants.EXPIRY) //a java.sql.Date
                .setId(UUID.randomUUID().toString())
                .signWith(SecurityConstants.ENCRYPTING_KEY)
                .compact();

        response.addHeader("Authorisation",SecurityConstants.PREFIX+token);

    }
}
