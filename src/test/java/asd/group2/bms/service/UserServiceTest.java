package asd.group2.bms.service;

import asd.group2.bms.BmsApplicationTests;
import asd.group2.bms.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends BmsApplicationTests {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void isEmailAvailable() {
    }

    @Test
    void isUsernameAvailable() {
    }

    @Test
    void createUser() {
    }

    @Test
    void setUserAccountStatus() {
    }

    @Test
    void changePassword() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));

        String oldPassword = "harsh1234";
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        assertTrue(passwordEncoder.matches(oldPassword, currentUser.getPassword()), "Expected true, but got false");
    }

    @Test
    void resetPassword() {
    }

    @Test
    void updateResetForgotPasswordToken() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void getUserByToken() {
    }

    @Test
    void getUserProfileByUsername() {
    }

    @Test
    void updateUserProfileByUsername() {
    }
}