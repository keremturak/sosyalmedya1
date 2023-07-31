package com.keremturak.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @GetMapping("/auth")
    public ResponseEntity<String> fallbackAuth() {
        return ResponseEntity.ok("Auth Service is not available. Please Try again later.");
    }
    @GetMapping("/post")
    public ResponseEntity<String> fallbackPost() {
        return ResponseEntity.ok("Post Service is not available. Please Try again later.");
    }
    @GetMapping("/user")
    public ResponseEntity<String> fallbackUser() {
        return ResponseEntity.ok("User Service is not available. Please Try again later.");
    }
}
