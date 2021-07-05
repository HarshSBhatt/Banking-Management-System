package asd.group2.bms.util;

import asd.group2.bms.model.user.RoleType;
import asd.group2.bms.payload.request.LoginRequest;
import asd.group2.bms.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.SecureRandom;
import java.util.Random;

public class Helper {

  static final private String DIGITS = "0123456789";

  final private Random random = new SecureRandom();

  @Autowired
  AuthenticationManager authenticationManager;

  char randomChar() {
    return DIGITS.charAt(random.nextInt(DIGITS.length()));
  }

  public String generateRandomDigits(int length) {
    StringBuilder sb = new StringBuilder();
    while (length > 0) {
      length--;
      sb.append(randomChar());
    }
    return sb.toString();
  }

  public UserPrincipal getLoggedInUser(RoleType role) {
    LoginRequest loginRequest = new LoginRequest();

    if (role.equals(RoleType.ROLE_USER)) {
      loginRequest.setUsernameOrEmail("customer");
      loginRequest.setPassword("harsh1234");
    } else if (role.equals(RoleType.ROLE_HR)) {
      loginRequest.setUsernameOrEmail("bankhr");
      loginRequest.setPassword("bankhr1234");
    } else if (role.equals(RoleType.ROLE_EMPLOYEE)) {
      loginRequest.setUsernameOrEmail("employee");
      loginRequest.setPassword("aditya1234");
    } else {
      loginRequest.setUsernameOrEmail("harsh");
      loginRequest.setPassword("harsh1234");
    }

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(),
            loginRequest.getPassword()));

    return (UserPrincipal) authentication.getPrincipal();
  }

}
