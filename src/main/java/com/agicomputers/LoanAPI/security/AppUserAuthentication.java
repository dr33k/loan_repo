package com.agicomputers.LoanAPI.security;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

interface Auth{
    Authentication getInstance();
}

@Component
public class AppUserAuthentication implements Auth {
    @Override
    public Authentication getInstance(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
