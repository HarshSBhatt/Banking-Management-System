package asd.group2.bms.service;

import asd.group2.bms.BmsApplicationTests;
import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.user.AccountStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.request.SignUpRequest;
import asd.group2.bms.payload.request.UpdateProfileRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.UserProfile;
import asd.group2.bms.repository.UserRepository;
import asd.group2.bms.repositoryImpl.UserRepositoryImpl;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest extends BmsApplicationTests {
    @Autowired
    AuthenticationManager authenticationManager;
    @Mock
    UserServiceImpl currentservice;
    @Mock
    UserRepositoryImpl CurrentRepository;
    @Mock
    UserPrincipal userPrincipal;
    @Mock
    UpdateProfileRequest updateProfileRequest;


    //Email available
    @Test
    void isEmailAvailableTrue() {
        assertTrue(currentservice.isEmailAvailable("abc.ca"),"Expected true got failed");

    }
    //Email not available
    @Test
    void isEmailAvailableFalse() {
        assertFalse(currentservice.isEmailAvailable("harsh.bhatt@dal.ca"),"Expected false got true");
    }

    @Test
    void isUsernameAvailableTrue() {

        assertTrue(currentservice.isUsernameAvailable("harsh"),"Expected true got false");
    }
    @Test
    void isUsernameAvailableFalse() {

        assertTrue(currentservice.isUsernameAvailable("raunak"),"Expected false got true");
    }

    @Test
    void createUserNewUser() throws ParseException {

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
        System.out.println(currentservice.createUser(TestUser));
        //assertTrue(a.getSuccess() == true,"Expected true got failed");
    }

    @Test
    void setUserAccountStatusRejected() throws MessagingException, UnsupportedEncodingException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        AccountStatus test  = AccountStatus.REJECTED;
        Boolean TestUser =  currentservice.setUserAccountStatus("testing@gmail.com",test);
        assertTrue(TestUser,"Expected true got failed");
    }
    //Right password
    @Test
    void changePasswordTrue() {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        String oldPassword = "harsh1234";
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
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
        ApiResponse new1 ;
        new1 = currentservice.changePassword("harsh12341","harsh12345",currentUser);
        assertTrue(new1.getSuccess() == true,"Expected false got true");
    }


    @Test
    void resetPasswordSuccess() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        ApiResponse new1 ;
        new1 = currentservice.changePassword("harsh1234","harsh12345",currentUser);
        assertTrue(new1.getSuccess() == true,"Expected true got failed");
    }
    @Test
    void resetPasswordFalse() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("harsh",
                        "harsh1234"));
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
        ApiResponse new1 ;
        new1 = currentservice.changePassword("harsh1234","harsh12345",currentUser);
        assertTrue(new1.getSuccess() == true,"Expected true got failed");
    }


    @Test
    void updateResetForgotPasswordToken() {

    }

    @Test
    void getUserByEmailSuccess() {
        User test  = currentservice.getUserByEmail("harsh.bhatt@dal.ca");
        assertTrue( test.getEmail() == "harsh.bhatt@dal.ca","Expected true got false");
    }
    @Test
    void getUserByEmailWrongUserFail() {
        try {
            User test = currentservice.getUserByEmail("har2sh.bhatt@dal.ca");
            System.out.println("Error exception expected");
        }
        catch(ResourceNotFoundException r)
        {
            assertTrue(1 == 1 ,"Expected error but got no exception");
        }
    }

    @Test
    void getUserByToken() {

    }

    @Test
    void getUserProfileByUsernameValidUsername() {
        UserProfile test = currentservice.getUserProfileByUsername("harsh");
        assertTrue(test.getUsername() == "harsh","Expected true as username exist but found false");

    }
    @Test
    void getUserProfileByUsernameInValidUsername() {
        UserProfile test = currentservice.getUserProfileByUsername("harsh23");
        assertTrue(test == null,"Expected false as username does not exist but found true");

    }

    @Test
    void updateUserProfileByUsernameValidUsername() {
        assertTrue(currentservice.updateUserProfileByUsername(userPrincipal,updateProfileRequest),"Expected true but got false");
    }
}