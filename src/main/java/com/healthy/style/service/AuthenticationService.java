package com.healthy.style.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;

import com.healthy.style.model.payload.LoginRequest;
import com.healthy.style.security.CustomUserDetails;

public interface AuthenticationService {

    Optional<Authentication> authenticateUser(LoginRequest loginRequest);

    String generateToken(CustomUserDetails customUserDetails);
}
