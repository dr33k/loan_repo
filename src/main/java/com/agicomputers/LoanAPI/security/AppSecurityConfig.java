package com.agicomputers.LoanAPI.security;

import com.agicomputers.LoanAPI.services.user_services.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;


import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

   private final PasswordEncoder passwordEncoder;
   private final AppUserServiceImpl appUserService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
              // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                //.and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/invalid_session")


                .and()
                .authorizeRequests()
                .antMatchers("/","/register/**").permitAll()
                .antMatchers("/app_users/**").hasAnyAuthority("appuser:read","appuser:write")
                .antMatchers("/administrator/**").hasAnyRole("ADMIN","SUBADMIN")
                .antMatchers(HttpMethod.GET,"/error").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/dashboard",true)
                .failureUrl("/failed")



                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .tokenRepository(new InMemoryTokenRepositoryImpl())
                .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(14))
                .key(SecurityConstants.KEY)


                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .clearAuthentication(true)
                .deleteCookies("JSESSION","remember-me")
                .invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }

}