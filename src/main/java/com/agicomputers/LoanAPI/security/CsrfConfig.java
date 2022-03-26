package com.agicomputers.LoanAPI.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
public class CsrfConfig {
    @Bean
    public CsrfTokenRepository csrfTokenRepository(){
        return new CookieCsrfTokenRepository();
    }
}
