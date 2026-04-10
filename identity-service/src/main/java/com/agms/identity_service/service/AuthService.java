package com.agms.identity_service.service;

import com.agms.identity_service.entity.UserCredential;
import com.agms.identity_service.repository.UserCredentialRepository;
import com.agms.identity_service.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredential credential) {
        // Fixed: Use getPassword() instead of password()
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "User added to the AGMS system successfully";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }
}