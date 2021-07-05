package asd.group2.bms.controller;

import asd.group2.bms.BmsApplicationTests;
import asd.group2.bms.payload.request.LoginRequest;
import asd.group2.bms.payload.response.JwtAuthenticationResponse;
import asd.group2.bms.security.JwtTokenProvider;
import asd.group2.bms.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest extends BmsApplicationTests {
    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    void authenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("harsh");
        loginRequest.setPassword("harsh1234");

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        assertEquals("harsh", userPrincipal.getUsername(), "Wrong username retrieved");
    }

    @Test
    void registerUser() {
    }

    @Test
    void forgotPassword() {
    }

    @Test
    void resetPassword() {
    }
}