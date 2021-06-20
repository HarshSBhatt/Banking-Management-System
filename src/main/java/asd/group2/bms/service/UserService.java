package asd.group2.bms.service;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.UserProfile;
import asd.group2.bms.repository.UserRepository;
import asd.group2.bms.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    public Boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    public ApiResponse changePassword(String oldPassword, String newPassword, UserPrincipal currentUser) {
        if (passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            User user = getUserByEmail(currentUser.getEmail());
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return new ApiResponse(true, "Password changed successfully!");
        } else {
            return new ApiResponse(false, "Old password is wrong!");
        }
    }

    public ResponseEntity<?> resetPassword(String newPassword, String confirmNewPassword, User user) {
        if (newPassword.equals(confirmNewPassword)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setForgotPasswordToken(null);
            userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse(true, "Password reset successfully"));
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "New passwords are not same"), HttpStatus.BAD_REQUEST);
        }
    }

    public void updateResetForgotPasswordToken(String token, String email) {
        User user = getUserByEmail(email);
        user.setForgotPasswordToken(token);
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email", "email", email));
    }

    public User getUserByToken(String token) {
        return userRepository.findByForgotPasswordToken(token).orElseThrow(() -> new ResourceNotFoundException("Reset Password Token", "token", token));
    }

    public UserProfile getUserProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return new UserProfile(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getBirthday(), user.getEmail(), user.getPhone(), user.getAddress(), user.getCreatedAt());
    }
}
