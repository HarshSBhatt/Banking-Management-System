package asd.group2.bms.service;

import asd.group2.bms.BmsApplicationTests;
import asd.group2.bms.model.user.AccountStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.request.SignUpRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.repository.UserRepository;
import asd.group2.bms.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends BmsApplicationTests {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    //Email available
    @Test
    void isEmailAvailableTrue() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        String oldPassword = "harsh1234";
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        currentservice.isEmailAvailable("testing@gmail.com");
        assertTrue(currentservice.isEmailAvailable("testing@gmail.com"),"Expected true got failed");
    }
    //Email not available
    @Test
    void isEmailAvailableFalse() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService CurrentService = new UserService();
        CurrentService.isEmailAvailable("testing@gmail.com");
        assertTrue(CurrentService.isEmailAvailable("testing@gmail.com"),"Expected false got true");
    }

    @Test
    void isUsernameAvailableTrue() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        assertTrue(currentservice.isUsernameAvailable("harsh"),"Expected true got false");
    }
    @Test
    void isUsernameAvailableFalse() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        assertTrue(currentservice.isUsernameAvailable("raunak"),"Expected false got true");
    }

    @Test
    void createUserAlreadyExist() throws ParseException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        SignUpRequest TestUser = new SignUpRequest();
        TestUser.setFirstName("raunak");
        TestUser.setUsername("rausecret");
        TestUser.setLastName("Seceret");
        TestUser.setEmail("raunakbhargava8@gmail.com");
        Date test=new SimpleDateFormat("yyyy-MM-dd").parse("1997-08-05");
        TestUser.setBirthday(test);
        TestUser.setAddress("Testing address");
        TestUser.setPhone("9692712342");
        TestUser.setCity("Mumbai");
        TestUser.setState("Maharastra");
        TestUser.setZipCode("400101");
        TestUser.setPassword("admin");
        ResponseEntity Test = currentservice.createUser(TestUser);
        ApiResponse a = (ApiResponse) Test.getBody();
        assertTrue(a.getSuccess() == true,"Expected true got failed");
    }

    @Test
    void setUserAccountStatusSucess() throws MessagingException, UnsupportedEncodingException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        AccountStatus test  = AccountStatus.REJECTED;
        User TestUser =  currentservice.setUserAccountStatus("testing@gmail.com",test);
        assertTrue(TestUser.getAccountStatus().equals(AccountStatus.REJECTED),"Expected true got failed");
    }
    //Right password
    @Test
    void changePasswordTrue() {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        String oldPassword = "harsh1234";
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        ApiResponse new1 ;
        new1 = currentservice.changePassword("harsh1234","harsh12345",currentUser);
        assertTrue(new1.getSuccess() == true,"Expected true got failed");
    }
    //Wrong password
    @Test
    void changePasswordFalse() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        String oldPassword = "harsh1234";
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        ApiResponse new1 ;
        new1 = currentservice.changePassword("harsh12341","harsh12345",currentUser);
        assertTrue(new1.getSuccess() == true,"Expected false got true");
    }


    @Test
    void resetPassword() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        ApiResponse new1 ;
        new1 = currentservice.changePassword("harsh1234","harsh12345",currentUser);
        assertTrue(new1.getSuccess() == true,"Expected true got failed");
    }

    @Test
    void updateResetForgotPasswordToken() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        String oldPassword = "harsh1234";
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        ApiResponse new1 ;
        new1 = currentservice.changePassword("harsh1234","harsh12345",currentUser);
        assertTrue(new1.getSuccess() == true,"Expected true got failed");
    }

    @Test
    void getUserByEmail() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        String oldPassword = "harsh1234";
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        ApiResponse new1 ;
        new1 = currentservice.changePassword("harsh1234","harsh12345",currentUser);
        assertTrue(new1.getSuccess() == true,"Expected true got failed");
    }

    @Test
    void getUserByToken() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        String oldPassword = "harsh1234";
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        ApiResponse new1 ;
        new1 = currentservice.changePassword("harsh1234","harsh12345",currentUser);
        assertTrue(new1.getSuccess() == true,"Expected true got failed");
    }

    @Test
    void getUserProfileByUsername() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        String oldPassword = "harsh1234";
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        ApiResponse new1 ;
        new1 = currentservice.changePassword("harsh1234","harsh12345",currentUser);
        assertTrue(new1.getSuccess() == true,"Expected true got failed");
    }

    @Test
    void updateUserProfileByUsername() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        String oldPassword = "harsh1234";
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        UserService currentservice = new UserService();
        ApiResponse new1 ;
        new1 = currentservice.changePassword("harsh1234","harsh12345",currentUser);
        assertTrue(new1.getSuccess() == true,"Expected true got failed");
    }
}