package com.jwt.springjwt.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.springjwt.auth.jwt.JwtService;
import com.jwt.springjwt.dto.request.LoginUserDto;
import com.jwt.springjwt.dto.request.RegisterUserDto;
import com.jwt.springjwt.dto.response.AuthResponseDto;
import com.jwt.springjwt.entity.UserEntity;
import com.jwt.springjwt.enumeration.Role;
import com.jwt.springjwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    public AuthResponseDto login(LoginUserDto loginUser) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.username(), loginUser.password()));
        UserDetails user = userRepo.findByUsername(loginUser.username()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        String token = jwtService.getToken(user);
        return new AuthResponseDto(token);
    }

    public AuthResponseDto register(RegisterUserDto registerUser) {
        UserEntity user = UserEntity.builder()
                        .username(registerUser.username())
                        .email(registerUser.email())
                        .password(passwordEncoder.encode(registerUser.password()))
                        .role(Role.USER)
                        .build();

        userRepo.save(user);
        return new AuthResponseDto(jwtService.getToken(user));
    }
    
}
