package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.account.AccountType;
import asd.group2.bms.model.cards.debit.DebitCard;
import asd.group2.bms.model.user.AccountStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.repository.IAccountRepository;
import asd.group2.bms.repository.IUserRepository;
import asd.group2.bms.service.ICustomEmail;
import asd.group2.bms.service.IDebitCardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

  @Mock
  IUserRepository userRepository;

  @Mock
  IAccountRepository accountRepository;

  @Mock
  ICustomEmail customEmail;

  @Mock
  IDebitCardService debitCardService;

  @InjectMocks
  AccountServiceImpl accountService;

  @BeforeEach
  void setUp() {
    HttpServletRequest mockRequest = new MockHttpServletRequest();
    ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
    RequestContextHolder.setRequestAttributes(servletRequestAttributes);
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void teardown() {
    RequestContextHolder.resetRequestAttributes();
  }

  @Test
  void getUserAccountListByStatusTest() {
    String username = "harsh";
    AccountStatus accountStatus = AccountStatus.PENDING;
    int page = 0;
    int size = 3;

    Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");

    User userOne = new User();
    userOne.setUsername("harsh");

    List<User> users = new ArrayList<>();
    users.add(userOne);

    Page<User> pagedUsers = new PageImpl<>(users);

    when(userRepository.findByAccountStatusEquals(accountStatus, pageable)).thenReturn(pagedUsers);

    PagedResponse<User> accounts =
        accountService.getUserAccountListByStatus(accountStatus,
            page, size);

    assertEquals(username, accounts.getContent().get(0).getUsername(), "Wrong " +
        "user was returned");
  }

  @Test
  void createAccountTest() throws MessagingException,
      UnsupportedEncodingException {
    User user = new User();
    AccountType accountType = AccountType.SAVINGS;
    Double balance = 1000.0;
    int creditScore = 750;

    Account accountToBeCreated = new Account();
    accountToBeCreated.setCreditScore(creditScore);

    DebitCard debitCard = new DebitCard();
    debitCard.setDebitCardNumber(1L);

    when(accountRepository.save(any(Account.class))).thenReturn(accountToBeCreated);
    when(debitCardService.createDebitCard(accountToBeCreated)).thenReturn(debitCard);

    Account account = accountService.createAccount(user, accountType, balance,
        creditScore);

    assertEquals(creditScore, account.getCreditScore(), "Wrong " +
        "credit score was returned");
  }

  @Test
  void getAccountByUserIdTest() {
    Long userId = 1L;
    Double accountBalance = 5000.0;

    Account account = new Account();
    account.setBalance(accountBalance);

    when(accountRepository.findAccountByUser_Id(userId)).thenReturn(Optional.of(account));

    Account fetchedAccount = accountService.getAccountByUserId(userId);

    assertEquals(accountBalance, fetchedAccount.getBalance(), "Wrong balance " +
        "was returned");
  }

  @Test
  void getAccountByAccountNumberTest() {
    Long accountNumber = 1L;
    Double accountBalance = 5000.0;

    Account account = new Account();
    account.setBalance(accountBalance);

    when(accountRepository.findAccountByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

    Account fetchedAccount = accountService.getAccountByAccountNumber(accountNumber);

    assertEquals(accountBalance, fetchedAccount.getBalance(), "Wrong balance " +
        "was returned");
  }

  @Test
  void updateAccountBalanceSuccessTest() {
    Double accountBalance = 5000.0;
    Account account = new Account();
    account.setBalance(accountBalance);

    when(accountRepository.update(any(Account.class))).thenReturn(true);

    Boolean isUpdated = accountService.updateAccountBalance(account);

    assertTrue(isUpdated, "False was returned");
  }

  @Test
  void updateAccountBalanceFailureTest() {
    Double accountBalance = 5000.0;
    Account account = new Account();
    account.setBalance(accountBalance);

    when(accountRepository.update(any(Account.class))).thenReturn(false);

    Boolean isUpdated = accountService.updateAccountBalance(account);

    assertFalse(isUpdated, "True was returned");
  }

}