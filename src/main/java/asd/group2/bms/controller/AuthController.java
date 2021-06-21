package asd.group2.bms.controller;

import asd.group2.bms.exception.BMSException;
import asd.group2.bms.model.user.AccountStatus;
import asd.group2.bms.model.user.Role;
import asd.group2.bms.model.user.RoleType;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.request.ForgotPasswordRequest;
import asd.group2.bms.payload.request.LoginRequest;
import asd.group2.bms.payload.request.ResetPasswordRequest;
import asd.group2.bms.payload.request.SignUpRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.JwtAuthenticationResponse;
import asd.group2.bms.repository.RoleRepository;
import asd.group2.bms.repository.UserRepository;
import asd.group2.bms.security.JwtTokenProvider;
import asd.group2.bms.service.UserService;
import asd.group2.bms.util.CustomEmail;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    CustomEmail customEmail;

    /**
     * @param loginRequest: username and password
     * @description: Authenticate the user's login request.
     */
    @PostMapping("/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    /**
     * @param signUpRequest: username, email, password and related information
     * @description: Register the user into the system.
     */
    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                signUpRequest.getBirthday(), signUpRequest.getPhone(), signUpRequest.getPassword(), signUpRequest.getAddress(), AccountStatus.PENDING);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new BMSException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    /**
     * @param forgotPasswordRequest: email of the user
     * @description: Send email link to change password.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(HttpServletRequest request, @Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String email = forgotPasswordRequest.getEmail();
        String token = RandomString.make(45);

        userService.updateResetForgotPasswordToken(token, email);
        String forgotPasswordLink = "http://localhost:3000/reset-password?token=" + token;
        try {
            customEmail.sendResetPasswordEmail(email, forgotPasswordLink);
            return ResponseEntity.ok(new ApiResponse(true, "Email sent successfully"));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(false, "Error while sending email"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        String token = resetPasswordRequest.getToken();
        String newPassword = resetPasswordRequest.getNewPassword();
        String confirmNewPassword = resetPasswordRequest.getConfirmNewPassword();

        User user = userService.getUserByToken(token);
        return userService.resetPassword(newPassword, confirmNewPassword, user);
    }
}
