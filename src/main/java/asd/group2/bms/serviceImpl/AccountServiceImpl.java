package asd.group2.bms.serviceImpl;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.account.AccountType;
import asd.group2.bms.model.cards.debit.DebitCard;
import asd.group2.bms.model.user.AccountStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.repository.AccountRepository;
import asd.group2.bms.repository.UserRepository;
import asd.group2.bms.service.AccountService;
import asd.group2.bms.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  DebitCardServiceImpl debitCardService;

  @Autowired
  CustomEmailImpl customEmail;

  /**
   * @param accountStatus: Account Status (PENDING, APPROVED, REJECTED)
   * @param page:          Page Number
   * @param size:          Size of the response data
   * @return This will return all the user having status accountStatus
   */
  public PagedResponse<User> getUserAccountListByStatus(AccountStatus accountStatus, int page, int size) {

    // Making list in ascending order to get early applied application first
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
    Page<User> users = userRepository.findByAccountStatusEquals(accountStatus, pageable);

    if (users.getNumberOfElements() == 0) {
      return new PagedResponse<>(Collections.emptyList(), users.getNumber(),
          users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
    }

    List<User> userResponse = users.getContent();

    return new PagedResponse<>(userResponse, users.getNumber(),
        users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
  }


  /**
   * @param user:        User whose account is being created
   * @param accountType: Type of account (SAVINGS, CURRENT)
   * @param balance:     balance deposited while account approval
   * @param creditScore: credit score of customer
   * @return This will return all the user having status accountStatus
   * @throws MessagingException:           This will throw MessagingException
   * @throws UnsupportedEncodingException: This will throw UnsupportedEncodingException
   */
  public Account createAccount(User user, AccountType accountType, Double balance, int creditScore) throws MessagingException, UnsupportedEncodingException {
    String accountNumber = new Helper().generateRandomDigits(10);
    Account account = new Account(Long.parseLong(accountNumber), accountType, balance,
        creditScore);
    account.setUser(user);

    DebitCard debitCard = debitCardService.createDebitCard(account);
    String email = user.getEmail();
    String firstName = user.getFirstName();
    Long debitCardNumber = debitCard.getDebitCardNumber();
    String pin = debitCard.getPin();
    String expiryYear = debitCard.getExpiryYear();
    String expiryMonth = debitCard.getExpiryMonth();
    String cvv = debitCard.getCvv();

    customEmail.sendDebitCardGenerationMail(email, firstName, debitCardNumber, pin, expiryMonth, expiryYear, cvv);
    return accountRepository.save(account);
  }

  /**
   * @param userId: User id of user whose account detail is requested
   * @return This will return the account details of the user based on user id
   */
  public Account getAccountByUserId(Long userId) {
    return accountRepository.findAccountByUser_Id(userId).orElseThrow(() -> new ResourceNotFoundException("Account", "account", "this user"));
  }

}
