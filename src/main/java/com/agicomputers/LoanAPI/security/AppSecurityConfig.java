package com.agicomputers.LoanAPI.security;

import com.agicomputers.LoanAPI.models.enums.AppUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

   private final PasswordEncoder passwordEncoder;
   private final CsrfTokenRepository csrfTokenRepository;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder, CsrfTokenRepository csrfTokenRepository){
        this.passwordEncoder = passwordEncoder;
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/customer/**").hasRole(AppUserRole.CUSTOMER.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails starB = User.builder()
                .username("starButterfly")
                .password(passwordEncoder.encode("ilovemarco"))
                .roles(AppUserRole.CUSTOMER.name())//ROLE_CUSTOMER
                .build();

        UserDetails michaelC = User.builder()
                .username("michaelChukwuma")
                .password(passwordEncoder.encode("mexasxlr"))
                .roles(AppUserRole.ADMIN.name())//ROLE_ADMIN
                .build();

        return new InMemoryUserDetailsManager(starB,michaelC);
    }
}