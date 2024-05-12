package com.jwt.springjwt.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.springjwt.dto.request.LoginUserDto;
import com.jwt.springjwt.dto.request.RegisterUserDto;
import com.jwt.springjwt.dto.response.AuthResponseDto;
import com.jwt.springjwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;
    private final UserRepository userRepo;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@RequestBody LoginUserDto loginUser) {
        return ResponseEntity.ok().body(service.login(loginUser));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerUser(@RequestBody RegisterUserDto registerUser) {
        return ResponseEntity.ok().body(service.register(registerUser));
    }

    @GetMapping("/username")
    public Object getUsername(@RequestParam String username) {
        System.out.println("get username: "+username);
        return userRepo.findByUsername(username).orElse(null);
    }
    
    
}
