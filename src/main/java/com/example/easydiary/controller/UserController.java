package com.example.easydiary.controller;

import com.example.easydiary.entity.AppUser;
import com.example.easydiary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<AppUser> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(null); // 인증되지 않은 경우 null 반환
        }
        String email = authentication.getName();
        AppUser user = userRepository.findByEmail(email)
                .orElse(null); // 사용자가 없을 경우 null 반환
        return ResponseEntity.ok().body(user);
    }

}
