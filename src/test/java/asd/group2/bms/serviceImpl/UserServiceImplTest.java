package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.account.AccountType;
import asd.group2.bms.model.user.Role;
import asd.group2.bms.model.user.RoleType;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.request.SignUpRequest;
import asd.group2.bms.payload.response.UserProfile;
import asd.group2.bms.repositoryImpl.RoleRepositoryImpl;
import asd.group2.bms.repositoryImpl.UserRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  RoleRepositoryImpl roleRepository;

  @Mock
  UserRepositoryImpl userRepository;

  @Mock
  PasswordEncoder passwordEncoder;

  @InjectMocks
  UserServiceImpl userService;

  @BeforeEach
  public void setup() {
    HttpServletRequest mockRequest = new MockHttpServletRequest();
    ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
    RequestContextHolder.setRequestAttributes(servletRequestAttributes);
  }

  @AfterEach
  public void teardown() {
    RequestContextHolder.resetRequestAttributes();
  }

  @Test
  void isEmailAvailableTrue() {
    String email = "random@dal.ca";

    when(userRepository.existsByEmail(email)).thenReturn(false);

    Boolean isAvailable = userService.isEmailAvailable(email);

    assertTrue(isAvailable, "False was returned");
  }

  @Test
  void isEmailAvailableFalse() {
    String email = "harsh.bhatt@dal.ca";

    when(userRepository.existsByEmail(email)).thenReturn(true);

    Boolean isAvailable = userService.isEmailAvailable(email);

    assertFalse(isAvailable, "True was returned");
  }

  @Test
  void isUsernameAvailableTrue() {
    String username = "random_name";

    when(userRepository.existsByUsername(username)).thenReturn(false);

    Boolean isAvailable = userService.isUsernameAvailable(username);

    assertTrue(isAvailable, "False was returned");
  }

  @Test
  void isUsernameAvailableFalse() {
    String username = "harsh";

    when(userRepository.existsByUsername(username)).thenReturn(true);

    Boolean isAvailable = userService.isUsernameAvailable(username);

    assertFalse(isAvailable, "True was returned");
  }

  @Test
  void createUser() {
    String username = "test__harsh";
    String email = "test__harsh.bhatt@dal.ca";

    Role role = new Role();

    when(roleRepository.findByName(RoleType.ROLE_USER)).thenReturn(java.util.Optional.of(role));

    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setFirstName("f_name");
    signUpRequest.setLastName("l_name");
    signUpRequest.setUsername(username);
    signUpRequest.setEmail(email);
    signUpRequest.setBirthday(new Date());
    signUpRequest.setPhone("9876543210");
    signUpRequest.setAddress("demo address");
    signUpRequest.setCity("city");
    signUpRequest.setState("state");
    signUpRequest.setZipCode("369852");
    signUpRequest.setPassword("demo1234");
    signUpRequest.setRequestedAccountType(AccountType.SAVINGS);
    signUpRequest.setRole(RoleType.ROLE_USER);

    when(userRepository.save(any(User.class))).then(returnsFirstArg());

    ResponseEntity<?> responseEntity = userService.createUser(signUpRequest);

    assertEquals(HttpStatus.CREATED.toString(),
        responseEntity.getStatusCode().toString(),
        "User does not created");
  }

  @Test
  void setUserAccountStatus() {
  }

  @Test
  void changePassword() {
  }

  @Test
  void resetPassword() {
  }

  @Test
  void updateResetForgotPasswordToken() {
  }

  @Test
  void getUserByEmail() {
    String email = "harsh.bhatt@dal.ca";

    User user = new User();
    user.setEmail(email);

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

    User fetchedUser = userService.getUserByEmail(email);

    assertEquals(email, fetchedUser.getEmail(), "Wrong user was returned");
  }

  @Test
  void getUserByToken() {
    String token = "asasas";

    User user = new User();
    user.setForgotPasswordToken(token);

    when(userRepository.findByForgotPasswordToken(token)).thenReturn(Optional.of(user));

    User fetchedUser = userService.getUserByToken(token);

    assertEquals(token, fetchedUser.getForgotPasswordToken(), "Wrong user was" +
        " returned");
  }

  @Test
  void getUserProfileByUsername() {
    String username = "harsh";

    UserProfile userProfile = new UserProfile();
    userProfile.setUsername(username);

    User user = new User();

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
    when(userService.getUserProfileByUsername(username)).thenReturn(userProfile);

    UserProfile fetchedUserProfile =
        userService.getUserProfileByUsername(username);

    assertEquals(username, fetchedUserProfile.getUsername(), "Wrong user " +
        "was returned");

  }

  @Test
  void updateUserProfileByUsername() {
  }

}