package asd.group2.bms.controller;

import asd.group2.bms.model.account.AccountType;
import asd.group2.bms.model.user.AccountStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.request.AccountRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.repository.UserRepository;
import asd.group2.bms.service.AccountService;
import asd.group2.bms.service.UserService;
import asd.group2.bms.util.AppConstants;
import asd.group2.bms.util.CustomEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomEmail customEmail;

    @GetMapping("/account/list")
    @RolesAllowed({"ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public PagedResponse<User> getUserAccountListByStatus(
            @RequestParam(value = "accountStatus") AccountStatus accountStatus,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return accountService.getUserAccountListByStatus(accountStatus, page, size);
    }

    @PostMapping("/account/create")
    @RolesAllowed({"ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<?> createUserAccount(@Valid @RequestBody AccountRequest accountRequest) {
        String email = accountRequest.getEmail();
        Double balance = accountRequest.getBalance();
        int creditScore = accountRequest.getCreditScore();
        if (!userRepository.existsByEmail(email)) {
            return new ResponseEntity<>(new ApiResponse(false, "User does not exist!"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.setUserAccountStatus(email, AccountStatus.ACTIVE);
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while changing account status!"),
                    HttpStatus.BAD_REQUEST);
        }
        AccountType accountType = user.getRequestedAccountType();
        accountService.createAccount(user, accountType, balance, creditScore);
        try {
            customEmail.sendAccountCreationMail(email, user.getFirstName());
            return ResponseEntity.ok(new ApiResponse(true, "Account created successfully"));
        } catch (MessagingException | UnsupportedEncodingException e) {
            return ResponseEntity.ok(new ApiResponse(true, "Account created successfully"));
        }
    }
}
