package com.agicomputers.LoanAPI.security;

import com.agicomputers.LoanAPI.models.enums.AppUserRole;
import com.agicomputers.LoanAPI.services.user_services.CustomerUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

   private final PasswordEncoder passwordEncoder;
   private final CustomerUserServiceImpl customerUserService;

    @Autowired
    public AppSecurityConfig( PasswordEncoder passwordEncoder, CustomerUserServiceImpl customerUserService){
        this.passwordEncoder = passwordEncoder;
        this.customerUserService = customerUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
               // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
               // .and()
                .authorizeRequests()
                .antMatchers("/customer/**").hasRole(AppUserRole.CUSTOMER.name())
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/customers",true)

                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .userDetailsService(this.userDetailsService())
                .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                .key("secret")

                .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
        .logoutUrl("/logout")
        .clearAuthentication(true)
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID","remember-me")
        .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(customerUserService);
        return provider;
    }

}