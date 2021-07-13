package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.account.AccountActivity;
import asd.group2.bms.model.user.User;
import asd.group2.bms.repository.IAccountActivityRepository;
import asd.group2.bms.service.IAccountService;
import asd.group2.bms.service.ICustomEmail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountActivityServiceImplTest {

  @Mock
  IAccountActivityRepository accountActivityRepository;

  @Mock
  ICustomEmail customEmail;

  @Mock
  IAccountService accountService;

  @InjectMocks
  AccountActivityServiceImpl accountActivityService;

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
  void fundTransferSuccessTest() throws Exception {
    Long senderAccountNumber = 2L;
    Long receiverAccountNumber = 3L;
    Double transactionAmount = 4000D;

    User senderUser = new User();
    senderUser.setEmail("userOne@dal.ca");
    senderUser.setFirstName("userOne");

    User receiverUser = new User();
    receiverUser.setEmail("userTwo@dal.ca");
    receiverUser.setFirstName("userTwo");

    Account senderAccount = new Account();
    senderAccount.setAccountNumber(senderAccountNumber);
    senderAccount.setBalance(5000D);
    senderAccount.setUser(senderUser);

    Account receiverAccount = new Account();
    receiverAccount.setAccountNumber(receiverAccountNumber);
    receiverAccount.setBalance(10000D);
    receiverAccount.setUser(receiverUser);

    when(accountService.getAccountByAccountNumber(senderAccountNumber)).thenReturn(senderAccount);
    when(accountService.getAccountByAccountNumber(receiverAccountNumber)).thenReturn(receiverAccount);
    when(accountService.updateAccountBalance(senderAccount)).thenReturn(true);
    when(accountService.updateAccountBalance(receiverAccount)).thenReturn(true);
    when(accountActivityRepository.save(any(AccountActivity.class))).then(returnsFirstArg());

    ResponseEntity<?> responseEntity =
        accountActivityService.fundTransfer(senderAccountNumber,
            receiverAccountNumber, "Testing fund transfer", transactionAmount);

    assertEquals(HttpStatus.OK.toString(),
        responseEntity.getStatusCode().toString(),
        "Fund transfer success test was not executed properly");
  }

  @Test
  void fundTransferExceedLimitTest() throws Exception {
    Long senderAccountNumber = 2L;
    Long receiverAccountNumber = 3L;
    Double transactionAmount = 400000D;

    User senderUser = new User();
    senderUser.setEmail("userOne@dal.ca");
    senderUser.setFirstName("userOne");

    User receiverUser = new User();
    receiverUser.setEmail("userTwo@dal.ca");
    receiverUser.setFirstName("userTwo");

    Account senderAccount = new Account();
    senderAccount.setAccountNumber(senderAccountNumber);
    senderAccount.setBalance(5000D);
    senderAccount.setUser(senderUser);

    Account receiverAccount = new Account();
    receiverAccount.setAccountNumber(receiverAccountNumber);
    receiverAccount.setBalance(10000D);
    receiverAccount.setUser(receiverUser);

    when(accountService.getAccountByAccountNumber(senderAccountNumber)).thenReturn(senderAccount);
    when(accountService.getAccountByAccountNumber(receiverAccountNumber)).thenReturn(receiverAccount);

    ResponseEntity<?> responseEntity =
        accountActivityService.fundTransfer(senderAccountNumber,
            receiverAccountNumber, "Testing fund transfer", transactionAmount);

    assertEquals(HttpStatus.BAD_REQUEST.toString(),
        responseEntity.getStatusCode().toString(),
        "Fund transfer exceed transaction amount test was not executed " +
            "properly");
  }

  @Test
  void fundTransferMinimumBalanceTest() throws Exception {
    Long senderAccountNumber = 2L;
    Long receiverAccountNumber = 3L;
    Double transactionAmount = 4000D;

    User senderUser = new User();
    senderUser.setEmail("userOne@dal.ca");
    senderUser.setFirstName("userOne");

    User receiverUser = new User();
    receiverUser.setEmail("userTwo@dal.ca");
    receiverUser.setFirstName("userTwo");

    Account senderAccount = new Account();
    senderAccount.setAccountNumber(senderAccountNumber);
    senderAccount.setBalance(50D);
    senderAccount.setUser(senderUser);

    Account receiverAccount = new Account();
    receiverAccount.setAccountNumber(receiverAccountNumber);
    receiverAccount.setBalance(100D);
    receiverAccount.setUser(receiverUser);

    when(accountService.getAccountByAccountNumber(senderAccountNumber)).thenReturn(senderAccount);
    when(accountService.getAccountByAccountNumber(receiverAccountNumber)).thenReturn(receiverAccount);

    ResponseEntity<?> responseEntity =
        accountActivityService.fundTransfer(senderAccountNumber,
            receiverAccountNumber, "Testing fund transfer", transactionAmount);

    assertEquals(HttpStatus.BAD_REQUEST.toString(),
        responseEntity.getStatusCode().toString(),
        "Fund transfer minimum balance amount test was not executed " +
            "properly");
  }

  @Test
  void fundTransferFailureTest() throws Exception {
    Long senderAccountNumber = 2L;
    Long receiverAccountNumber = 3L;
    Double transactionAmount = 4000D;

    User senderUser = new User();
    senderUser.setEmail("userOne@dal.ca");
    senderUser.setFirstName("userOne");

    User receiverUser = new User();
    receiverUser.setEmail("userTwo@dal.ca");
    receiverUser.setFirstName("userTwo");

    Account senderAccount = new Account();
    senderAccount.setAccountNumber(senderAccountNumber);
    senderAccount.setBalance(5000D);
    senderAccount.setUser(senderUser);

    Account receiverAccount = new Account();
    receiverAccount.setAccountNumber(receiverAccountNumber);
    receiverAccount.setBalance(10000D);
    receiverAccount.setUser(receiverUser);

    when(accountService.getAccountByAccountNumber(senderAccountNumber)).thenThrow(new RuntimeException());

    ResponseEntity<?> responseEntity =
        accountActivityService.fundTransfer(senderAccountNumber,
            receiverAccountNumber, "Testing fund transfer", transactionAmount);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
        responseEntity.getStatusCode().toString(),
        "Fund transfer failure test was not executed properly");
  }

  @Test
  void fundTransferSenderBalanceUpdatedTest() throws Exception {
    Long senderAccountNumber = 2L;
    Long receiverAccountNumber = 3L;
    Double transactionAmount = 4000D;

    User senderUser = new User();
    senderUser.setEmail("userOne@dal.ca");
    senderUser.setFirstName("userOne");

    User receiverUser = new User();
    receiverUser.setEmail("userTwo@dal.ca");
    receiverUser.setFirstName("userTwo");

    Account senderAccount = new Account();
    senderAccount.setAccountNumber(senderAccountNumber);
    senderAccount.setBalance(5000D);
    senderAccount.setUser(senderUser);

    Account receiverAccount = new Account();
    receiverAccount.setAccountNumber(receiverAccountNumber);
    receiverAccount.setBalance(10000D);
    receiverAccount.setUser(receiverUser);

    when(accountService.getAccountByAccountNumber(senderAccountNumber)).thenReturn(senderAccount);
    when(accountService.getAccountByAccountNumber(receiverAccountNumber)).thenReturn(receiverAccount);
    when(accountService.updateAccountBalance(senderAccount)).thenReturn(false);
    when(accountService.updateAccountBalance(receiverAccount)).thenReturn(true);

    ResponseEntity<?> responseEntity =
        accountActivityService.fundTransfer(senderAccountNumber,
            receiverAccountNumber, "Testing fund transfer", transactionAmount);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
        responseEntity.getStatusCode().toString(),
        "Fund transfer sender balance updated test was not executed properly");
  }

  @Test
  void fundTransferReceiverBalanceUpdatedTest() throws Exception {
    Long senderAccountNumber = 2L;
    Long receiverAccountNumber = 3L;
    Double transactionAmount = 4000D;

    User senderUser = new User();
    senderUser.setEmail("userOne@dal.ca");
    senderUser.setFirstName("userOne");

    User receiverUser = new User();
    receiverUser.setEmail("userTwo@dal.ca");
    receiverUser.setFirstName("userTwo");

    Account senderAccount = new Account();
    senderAccount.setAccountNumber(senderAccountNumber);
    senderAccount.setBalance(5000D);
    senderAccount.setUser(senderUser);

    Account receiverAccount = new Account();
    receiverAccount.setAccountNumber(receiverAccountNumber);
    receiverAccount.setBalance(10000D);
    receiverAccount.setUser(receiverUser);

    when(accountService.getAccountByAccountNumber(senderAccountNumber)).thenReturn(senderAccount);
    when(accountService.getAccountByAccountNumber(receiverAccountNumber)).thenReturn(receiverAccount);
    when(accountService.updateAccountBalance(senderAccount)).thenReturn(true);
    when(accountService.updateAccountBalance(receiverAccount)).thenReturn(false);

    ResponseEntity<?> responseEntity =
        accountActivityService.fundTransfer(senderAccountNumber,
            receiverAccountNumber, "Testing fund transfer", transactionAmount);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
        responseEntity.getStatusCode().toString(),
        "Fund transfer receiver balance updated test was not executed " +
            "properly");
  }

  @Test
  void fundTransferSaveDataErrorTest() throws Exception {
    Long senderAccountNumber = 2L;
    Long receiverAccountNumber = 3L;
    Double transactionAmount = 4000D;

    User senderUser = new User();
    senderUser.setEmail("userOne@dal.ca");
    senderUser.setFirstName("userOne");

    User receiverUser = new User();
    receiverUser.setEmail("userTwo@dal.ca");
    receiverUser.setFirstName("userTwo");

    Account senderAccount = new Account();
    senderAccount.setAccountNumber(senderAccountNumber);
    senderAccount.setBalance(5000D);
    senderAccount.setUser(senderUser);

    Account receiverAccount = new Account();
    receiverAccount.setAccountNumber(receiverAccountNumber);
    receiverAccount.setBalance(10000D);
    receiverAccount.setUser(receiverUser);

    when(accountService.getAccountByAccountNumber(senderAccountNumber)).thenReturn(senderAccount);
    when(accountService.getAccountByAccountNumber(receiverAccountNumber)).thenReturn(receiverAccount);
    when(accountService.updateAccountBalance(senderAccount)).thenReturn(true);
    when(accountService.updateAccountBalance(receiverAccount)).thenReturn(true);
    when(accountActivityRepository.save(any(AccountActivity.class))).thenThrow(new RuntimeException());

    ResponseEntity<?> responseEntity =
        accountActivityService.fundTransfer(senderAccountNumber,
            receiverAccountNumber, "Testing fund transfer", transactionAmount);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
        responseEntity.getStatusCode().toString(),
        "Fund transfer save data error test was not executed " +
            "properly");
  }

}