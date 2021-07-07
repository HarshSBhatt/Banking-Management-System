package asd.group2.bms.controller;

import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.request.ChangePasswordRequest;
import asd.group2.bms.payload.request.SignUpRequest;
import asd.group2.bms.payload.request.UpdateAccountStatusRequest;
import asd.group2.bms.payload.request.UpdateProfileRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.UserIdentityAvailability;
import asd.group2.bms.payload.response.UserProfile;
import asd.group2.bms.payload.response.UserSummary;
import asd.group2.bms.repository.UserRepository;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

/**
 * @description: It will handle all the user related requests.
 */
@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  UserServiceImpl userService;

  @Autowired
  UserRepository userRepository;

  /**
   * @param currentUser: logged in user
   * @return It will return the current user.
   */
  @GetMapping("/user/me")
  public UserSummary getCurrentUser(@CurrentLoggedInUser UserPrincipal currentUser) {
    return new UserSummary(currentUser.getId(), currentUser.getFirstName(), currentUser.getLastName(),
        currentUser.getUsername(), currentUser.getBirthday(), currentUser.getEmail(), currentUser.getPhone(),
        currentUser.getAddress(), currentUser.getCity(), currentUser.getState(), currentUser.getZipCode());
  }


  /**
   * It will update the user profile
   *
   * @param currentUser:          current logged in user
   * @param updateProfileRequest: updated data field
   * @return success or failure response with appropriate message
   */
  @PutMapping("/user/me")
  public ResponseEntity<?> updateUserProfile(@CurrentLoggedInUser UserPrincipal currentUser, @Valid @RequestBody UpdateProfileRequest updateProfileRequest) {
    Boolean isUserProfileUpdated = userService.updateUserProfileByUsername(currentUser, updateProfileRequest);
    if (isUserProfileUpdated) {
      return ResponseEntity.ok(new ApiResponse(true, "Profile updated successfully!"));
    } else {
      return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while updating user profile"),
          HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * @param username: username of the user
   * @return It will return true or false.
   */
  @GetMapping("/user/checkUsernameAvailability")
  public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
    Boolean isAvailable = userService.isUsernameAvailable(username);
    return new UserIdentityAvailability(isAvailable);
  }

  /**
   * @param email: email of the user
   * @return It will return true or false.
   */
  @GetMapping("/user/checkEmailAvailability")
  public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
    Boolean isAvailable = userService.isEmailAvailable(email);
    return new UserIdentityAvailability(isAvailable);
  }

  /**
   * @param username: username of the user
   * @return It will return user profile.
   */
  @GetMapping("/users/{username}")
  @RolesAllowed({"ROLE_MANAGER", "ROLE_HR", "ROLE_USER", "ROLE_EMPLOYEE"})
  public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
    return userService.getUserProfileByUsername(username);
  }

  /**
   * @param currentUser:           current logged in user
   * @param changePasswordRequest: new password of user
   * @return success or failure response with appropriate message
   */
  @PostMapping("/users/change-password")
  public ApiResponse changePassword(@CurrentLoggedInUser UserPrincipal currentUser,
                                    @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
    String oldPassword = changePasswordRequest.getOldPassword();
    String newPassword = changePasswordRequest.getNewPassword();
    String confirmNewPassword = changePasswordRequest.getConfirmNewPassword();

    if (!oldPassword.equals(newPassword)) {
      if (newPassword.equals(confirmNewPassword)) {
        return userService.changePassword(oldPassword, newPassword, currentUser);
      } else {
        return new ApiResponse(false, "New passwords are not same");
      }
    } else {
      return new ApiResponse(false, "Current and new password are same");
    }
  }

  /**
   * Register the user into the system
   *
   * @param signUpRequest: username, email, password and related information
   * @return success or failure response with appropriate message
   */
  @PostMapping("/users/create")
  @RolesAllowed({"ROLE_MANAGER", "ROLE_HR"})
  public ResponseEntity<?> createUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    return userService.createUser(signUpRequest);
  }

  /**
   * Updates the status of the user account
   *
   * @param updateAccountStatus: email and account status
   * @return success or failure response with appropriate message
   * @throws MessagingException:           This will throw MessagingException
   * @throws UnsupportedEncodingException: This will throw UnsupportedEncodingException
   */
  @PutMapping("/users/account/status")
  @RolesAllowed({"ROLE_MANAGER", "ROLE_EMPLOYEE"})
  public ResponseEntity<?> updateUserAccountStatus(
      @Valid @RequestBody UpdateAccountStatusRequest updateAccountStatus) throws MessagingException, UnsupportedEncodingException {
    User user = userService.setUserAccountStatus(updateAccountStatus.getEmail(),
        updateAccountStatus.getAccountStatus());
    if (user != null) {
      return ResponseEntity.ok(new ApiResponse(true, "Account status changed successfully!"));
    } else {
      return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while changing account status!"),
          HttpStatus.BAD_REQUEST);
    }
  }

}
