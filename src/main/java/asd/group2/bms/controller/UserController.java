package asd.group2.bms.controller;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.UserIdentityAvailability;
import asd.group2.bms.payload.UserProfile;
import asd.group2.bms.payload.UserSummary;
import asd.group2.bms.repository.UserRepository;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @description: It will handle all the user related requests.
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * @param currentUser: logged in user
     * @description: It will return the current user.
     */
    @GetMapping("/user/me")
    @RolesAllowed({ "ROLE_USER" })
    public UserSummary getCurrentUser(@CurrentLoggedInUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getFirstName(), currentUser.getLastName(), currentUser.getUsername(), currentUser.getBirthday(), currentUser.getEmail(), currentUser.getPhone(), currentUser.getAddress());
        return userSummary;
    }

    /**
     * @param username: username of the user
     * @description: It will return true or false.
     */
    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    /**
     * @param email: email of the user
     * @description: It will return true or false.
     */
    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    /**
     * @param username: username of the user
     * @description: It will return user profile.
     */
    @GetMapping("/users/{username}")
    @RolesAllowed({ "ROLE_USER", "ROLE_MANAGER" })
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile userProfile = new UserProfile(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getBirthday(), user.getEmail(), user.getPhone(), user.getAddress(), user.getCreatedAt());

        return userProfile;
    }
}
