package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.account.AccountActivity;;
import asd.group2.bms.model.user.User;
import asd.group2.bms.repositoryImpl.AccountActivityRepositoryImpl;
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
  AccountActivityRepositoryImpl accountActivityRepository;

  @Mock
  CustomEmailImpl customEmail;

  @Mock
  AccountServiceImpl accountService;

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
            receiverAccountNumber, "Testing fund transfer", 4000D);

    assertEquals(HttpStatus.OK.toString(),
        responseEntity.getStatusCode().toString(),
        "Fund transfer success test was not executed properly");
  }

  @Test
  void fundTransferFailureTest() throws Exception {
    Long senderAccountNumber = 2L;
    Long receiverAccountNumber = 3L;

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
            receiverAccountNumber, "Testing fund transfer", 4000D);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
        responseEntity.getStatusCode().toString(),
        "Fund transfer was failure test was not executed properly");
  }

}