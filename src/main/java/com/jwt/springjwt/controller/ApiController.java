package com.jwt.springjwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {
    
    @GetMapping("")
    public ResponseEntity<?> getApi() {
        return ResponseEntity.ok().body("Api private, token necesary"); 
    }

    @GetMapping("/public")
    public ResponseEntity<?> getPublic() {
        return ResponseEntity.ok().body("Api private /public"); 
    }
    
}
