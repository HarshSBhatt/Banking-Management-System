package asd.group2.bms.service;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.UserProfile;
import asd.group2.bms.repository.UserRepository;
import asd.group2.bms.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            Optional<User> user = getUserByEmail(currentUser.getEmail());
            if (user.isPresent()) {
                User updatedUser = user.get();
                updatedUser.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(updatedUser);
            }
            return new ApiResponse(true, "Password changed successfully!");
        } else {
            return new ApiResponse(false, "Old password is wrong!");
        }
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserProfile getUserProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return new UserProfile(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getBirthday(), user.getEmail(), user.getPhone(), user.getAddress(), user.getCreatedAt());
    }
}
