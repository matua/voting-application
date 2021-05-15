package com.matuageorge.votingapplication.security;

import com.matuageorge.votingapplication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(ApplicationSecurityConfig.class.getName());
    private final UserService userService;

    @Autowired
    public ApplicationSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .headers().frameOptions().disable()
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/h2-console/**").permitAll()
//                .antMatchers("/api/v1/voting/users/**").hasRole("ADMIN")
//                .antMatchers("/api/v1/voting/restaurants/**").hasRole("ADMIN")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();

        http.
                headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/voting/users/**").hasRole("ADMIN")
                .and()
                .httpBasic();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
}