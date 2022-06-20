package com.agicomputers.LoanAPI.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtVerificationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorisation");
        if(bearerToken == null || bearerToken == "" || !bearerToken.contains("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        try {
            bearerToken = bearerToken.replace(SecurityConstants.PREFIX, "");

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SecurityConstants.DECRYPTING_KEY)
                    .build()
                    .parseClaimsJws(bearerToken)
                    .getBody();
            Date expiry = claims.getExpiration();

            String username = claims.getSubject();
            List<Map<String, String>> authorities = (List<Map<String, String>>) claims.get("authorities");

            List<? extends GrantedAuthority> grantedAuthorities = authorities.stream()
                    .map(map -> {
                        return new SimpleGrantedAuthority(map.get("authority"));
                    })
                    .collect(Collectors.toList());

            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    grantedAuthorities
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        catch(JwtException e){
                throw new IllegalArgumentException(e);
            }
        filterChain.doFilter(request,response);
    }
}
