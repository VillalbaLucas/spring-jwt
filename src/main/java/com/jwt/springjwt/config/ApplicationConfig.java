package com.jwt.springjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jwt.springjwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig  {

    private final UserRepository userRepo;

    @Bean
    AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailSerice) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userDetailSerice);
        return authProvider;
    }

    @Bean
    UserDetailsService userDetailService() {
        return username -> userRepo.findByUsername(username)
            .orElseThrow(()-> new UsernameNotFoundException("User not found!"));
    }

    @Bean
    PasswordEncoder passEncoder(){
        return new BCryptPasswordEncoder();
    }
}
