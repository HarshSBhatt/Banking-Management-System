package asd.group2.bms.controller;

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
import asd.group2.bms.serviceImpl.UserServiceImpl;
import asd.group2.bms.serviceImpl.CustomEmailImpl;
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

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserServiceImpl userService;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtTokenProvider tokenProvider;

  @Autowired
  CustomEmailImpl customEmail;

  /**
   * Authenticate the user's login request.
   *
   * @param loginRequest: username and password
   * @return success or failure response with appropriate message
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
   * Register the user into the system.
   *
   * @param signUpRequest: username, email, password and related information
   * @return success or failure response with appropriate message
   */
  @PostMapping("/auth/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    return userService.createUser(signUpRequest);
  }

  /**
   * Send email link to change password.
   *
   * @param forgotPasswordRequest: email of the user
   * @return success or failure response with appropriate message
   */
  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
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

  /**
   * Reset the user password based on reset token received via mail
   *
   * @param resetPasswordRequest: email of the user
   * @return success or failure response with appropriate message
   */
  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
    String token = resetPasswordRequest.getToken();
    String newPassword = resetPasswordRequest.getNewPassword();
    String confirmNewPassword = resetPasswordRequest.getConfirmNewPassword();

    User user = userService.getUserByToken(token);
    return userService.resetPassword(newPassword, confirmNewPassword, user);
  }

}
