package asd.group2.bms.controller;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.account.AccountType;
import asd.group2.bms.model.user.AccountStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.request.AccountRequest;
import asd.group2.bms.payload.response.AccountDetailResponse;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.payload.response.UserMetaResponse;
import asd.group2.bms.repositoryImpl.UserRepositoryImpl;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.serviceImpl.AccountServiceImpl;
import asd.group2.bms.serviceImpl.UserServiceImpl;
import asd.group2.bms.util.AppConstants;
import asd.group2.bms.serviceImpl.CustomEmailImpl;
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
  AccountServiceImpl accountService;

  @Autowired
  UserServiceImpl userService;

  @Autowired
  UserRepositoryImpl userRepository;

  @Autowired
  CustomEmailImpl customEmail;

  /**
   * Users list based on the account status of the users
   *
   * @param accountStatus: ACTIVE, REJECTED, PENDING, CLOSED
   * @param page:          number of the page
   * @param size:          page size
   * @return User data of size N having page number "page"
   */
  @GetMapping("/account/user")
  @RolesAllowed({"ROLE_MANAGER", "ROLE_EMPLOYEE"})
  public PagedResponse<User> getUserAccountListByStatus(
      @RequestParam(value = "accountStatus") AccountStatus accountStatus,
      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    return accountService.getUserAccountListByStatus(accountStatus, page, size);
  }

  /**
   * This will fetch user account based on login detail (jwt)
   *
   * @param currentUser: current logged in user
   * @return account detail of logged in user
   */
  @GetMapping("/account/me")
  @RolesAllowed({"ROLE_USER"})
  public AccountDetailResponse getAccountDetails(@CurrentLoggedInUser UserPrincipal currentUser) {
    Account account = accountService.getAccountByUserId(currentUser.getId());
    AccountDetailResponse accountDetailResponse = new AccountDetailResponse();
    accountDetailResponse.setAccountNumber(account.getAccountNumber());
    accountDetailResponse.setAccountType(account.getAccountType());
    accountDetailResponse.setBalance(account.getBalance());
    accountDetailResponse.setCreditScore(account.getCreditScore());
    accountDetailResponse.setAccountCreatedAt(account.getCreatedAt());
    accountDetailResponse.setLastActivityAt(account.getUpdatedAt());
    User user = account.getUser();
    UserMetaResponse userMetaResponse = new UserMetaResponse(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getUsername(),
        user.getEmail(),
        user.getPhone()
    );
    accountDetailResponse.setUserMetaResponse(userMetaResponse);
    return accountDetailResponse;
  }

  /**
   * This will create user account based on the payload received (balance, credit score)
   *
   * @param accountRequest: Request body containing all necessary data
   * @return success or failure response with appropriate message
   * @throws MessagingException:           This will throw MessagingException
   * @throws UnsupportedEncodingException: This will throw UnsupportedEncodingException
   */
  @PostMapping("/account/user")
  @RolesAllowed({"ROLE_MANAGER", "ROLE_EMPLOYEE"})
  public ResponseEntity<?> createUserAccount(@Valid @RequestBody AccountRequest accountRequest) throws MessagingException, UnsupportedEncodingException {
    String email = accountRequest.getEmail();
    Double balance = accountRequest.getBalance();
    int creditScore = accountRequest.getCreditScore();
    if (!userRepository.existsByEmail(email)) {
      return new ResponseEntity<>(new ApiResponse(false, "User does not exist!"), HttpStatus.BAD_REQUEST);
    }
    Boolean isUpdated = userService.setUserAccountStatus(email, AccountStatus.ACTIVE);
    User user = userService.getUserByEmail(email);
    if (!isUpdated) {
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
