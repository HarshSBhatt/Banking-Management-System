package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.term_deposit.TermDepositDetail;
import asd.group2.bms.model.user.User;
import asd.group2.bms.repository.ITermDepositDetailRepository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TermDepositDetailServiceImplTest {

  @Mock
  ITermDepositDetailRepository termDepositDetailRepository;

  @Mock
  IAccountService accountService;

  @Mock
  ICustomEmail customEmail;

  @InjectMocks
  TermDepositDetailServiceImpl termDepositDetailService;

  @BeforeEach
  public void setup() {
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
  void makeTermDepositRequestSuccessTest() throws Exception {
    Long userId = 1L;
    String email = "arpan@gmail.com";
    String firstName = "arpan";
    Double fdAmount = 1500.0;
    Double balance = 5000.0;
    Date date = new Date();
    int duration = 4;

    User user = new User();
    user.setId(userId);
    user.setFirstName(firstName);
    user.setEmail(email);

    Account account = new Account();
    account.setUser(user);
    account.setBalance(balance);

    when(accountService.getAccountByUserId(userId)).thenReturn(account);
    when(accountService.updateAccountBalance(account)).thenReturn(true);
    when(termDepositDetailRepository.save(any(TermDepositDetail.class))).then(returnsFirstArg());

    ResponseEntity<?> responseEntity = termDepositDetailService.makeTermDepositRequest(userId, email, firstName,
        fdAmount, date, duration);

    assertEquals(HttpStatus.OK.toString(),
        responseEntity.getStatusCode().toString(),
        "Make Term Deposit success test was not executed properly");
  }

  @Test
  void makeTermDepositRequestBalanceNotUpdatedFailTest() throws Exception {
    Long userId = 1L;
    String email = "arpan@gmail.com";
    String firstName = "arpan";
    Double fdAmount = 1500.0;
    Double balance = 5000.0;
    Date date = new Date();
    int duration = 4;

    User user = new User();
    user.setId(userId);
    user.setFirstName(firstName);
    user.setEmail(email);

    Account account = new Account();
    account.setUser(user);
    account.setBalance(balance);

    when(accountService.getAccountByUserId(userId)).thenReturn(account);
    when(accountService.updateAccountBalance(account)).thenReturn(false);

    ResponseEntity<?> responseEntity = termDepositDetailService.makeTermDepositRequest(userId, email, firstName,
        fdAmount, date, duration);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
        responseEntity.getStatusCode().toString(),
        "Make Term Deposit failure test was not executed properly because " +
            "balance was not updated!");
  }

  @Test
  void makeTermDepositRequestLowFDAmountTest() throws Exception {
    Long userId = 1L;
    String email = "arpan@gmail.com";
    String firstName = "arpan";
    Double fdAmount = 10.0;
    Double balance = 5000.0;
    Date date = new Date();
    int duration = 4;

    ResponseEntity<?> responseEntity = termDepositDetailService.makeTermDepositRequest(userId, email, firstName,
        fdAmount, date, duration);

    assertEquals(HttpStatus.BAD_REQUEST.toString(),
        responseEntity.getStatusCode().toString(),
        "Make Term Deposit failure test was not executed properly because FD " +
            "amount was low");
  }

  @Test
  void makeTermDepositRequestNotEnoughBalanceTest() throws Exception {
    Long userId = 1L;
    String email = "arpan@gmail.com";
    String firstName = "arpan";
    Double fdAmount = 6000.0;
    Double balance = 5000.0;
    Date date = new Date();
    int duration = 4;

    User user = new User();
    user.setId(userId);
    user.setFirstName(firstName);
    user.setEmail(email);

    Account account = new Account();
    account.setUser(user);
    account.setBalance(balance);

    when(accountService.getAccountByUserId(userId)).thenReturn(account);

    ResponseEntity<?> responseEntity = termDepositDetailService.makeTermDepositRequest(userId, email, firstName,
        fdAmount, date, duration);

    assertEquals(HttpStatus.BAD_REQUEST.toString(),
        responseEntity.getStatusCode().toString(),
        "Make Term Deposit failure test was not executed properly because FD " +
            "amount was higher than available balance");
  }

  @Test
  void makeTermDepositRequestNotEnoughBalanceAfterDeductionTest() throws Exception{
    Long userId = 1L;
    String email = "arpan@gmail.com";
    String firstName = "arpan";
    Double fdAmount = 5000.0;
    Double balance = 5000.0;
    Date date = new Date();
    int duration = 4;

    User user = new User();
    user.setId(userId);
    user.setFirstName(firstName);
    user.setEmail(email);

    Account account = new Account();
    account.setUser(user);
    account.setBalance(balance);

    when(accountService.getAccountByUserId(userId)).thenReturn(account);

    ResponseEntity<?> responseEntity = termDepositDetailService.makeTermDepositRequest(userId, email, firstName,
        fdAmount, date, duration);
    assertEquals(HttpStatus.BAD_REQUEST.toString(),
        responseEntity.getStatusCode().toString(),
        "Make Term Deposit failure test was not executed properly because " +
            "balance after deduction is lower");
  }

  @Test
  void makeTermDepositRequestExceptionTest() throws Exception{
    Long userId = 1L;
    String email = "arpan@gmail.com";
    String firstName = "arpan";
    Double fdAmount = 4900.0;
    Double balance = 5000.0;
    Date date = new Date();
    int duration = 4;

    User user = new User();
    user.setId(userId);
    user.setFirstName(firstName);
    user.setEmail(email);

    Account account = new Account();


    when(accountService.getAccountByUserId(userId)).thenReturn(account);

    ResponseEntity<?> responseEntity = termDepositDetailService.makeTermDepositRequest(userId, email, firstName,
        fdAmount, date, duration);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
        responseEntity.getStatusCode().toString(),
        "Make Term Deposit failure test was not executed properly because " +
            "exception was raised");
  }

  @Test
  void getTermDepositDetailByIdTest() {
    Long id = 1L;
    Double initialAmount = 1500.0;
    TermDepositDetail termDepositDetail = new TermDepositDetail();
    termDepositDetail.setInitialAmount(initialAmount);

    when(termDepositDetailRepository.findById(id)).thenReturn(Optional.of(termDepositDetail));

    TermDepositDetail fetchedTermDeposit =
        termDepositDetailService.getTermDepositDetailById(id);
    assertEquals(initialAmount, fetchedTermDeposit.getInitialAmount(), "Wrong " +
        "Initial Amount was returned");
  }


  @Test
  void getTermDepositDetailTest() {
    Long userId = 1L;
    Long accountNumber = 1L;
    String email = "arpan@gmail.com";
    String firstName = "arpan";
    Double balance = 5000.0;

    List<TermDepositDetail> termDepositDetails = new ArrayList<>();

    User user = new User();
    user.setId(userId);
    user.setFirstName(firstName);
    user.setEmail(email);

    Account account = new Account();
    account.setAccountNumber(accountNumber);
    account.setUser(user);
    account.setBalance(balance);

    TermDepositDetail termDepositDetail = new TermDepositDetail();
    termDepositDetail.setAccount(account);
    termDepositDetails.add(termDepositDetail);

    when(termDepositDetailRepository.findTermDepositDetailByAccount_AccountNumber(accountNumber)).thenReturn(termDepositDetails);
    when(accountService.getAccountByUserId(userId)).thenReturn(account);

    assertEquals(accountNumber,
        termDepositDetailService.getTermDepositDetail(userId).get(0).getAccount().getAccountNumber(), "Wrong accountNumber was returned");
  }

  @Test
  void getTermDepositDetailEmptyTest() {
    Long userId = 1L;
    Long accountNumber = 1L;
    String email = "arpan@gmail.com";
    String firstName = "arpan";
    Double balance = 5000.0;

    List<TermDepositDetail> termDepositDetails = new ArrayList<>();

    User user = new User();
    user.setId(userId);
    user.setFirstName(firstName);
    user.setEmail(email);

    Account account = new Account();
    account.setAccountNumber(accountNumber);
    account.setUser(user);
    account.setBalance(balance);

    when(accountService.getAccountByUserId(userId)).thenReturn(account);
    when(termDepositDetailRepository.findTermDepositDetailByAccount_AccountNumber(accountNumber)).thenReturn(termDepositDetails);

    assertEquals(0,
        termDepositDetailService.getTermDepositDetail(userId).size(), "Empty " +
            "Term Deposit list was not returned");
  }
}
