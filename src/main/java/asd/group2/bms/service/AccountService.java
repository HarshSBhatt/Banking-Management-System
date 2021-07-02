package asd.group2.bms.service;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.account.AccountType;
import asd.group2.bms.model.cards.debit.DebitCard;
import asd.group2.bms.model.user.AccountStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.repository.AccountRepository;
import asd.group2.bms.repository.UserRepository;
import asd.group2.bms.util.CustomEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.Month;
import java.time.Year;
import java.util.Collections;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    DebitCardService debitCardService;

    @Autowired
    CustomEmail customEmail;

    /**
     * @param accountStatus: Account Status (PENDING, APPROVED, REJECTED)
     * @param page:          Page Number
     * @param size:          Size of the response data
     * @description: This will return all the user having status accountStatus
     */
    public PagedResponse<User> getUserAccountListByStatus(AccountStatus accountStatus, int page, int size) {

        /** Making list in ascending order to get early applied application first */
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
     * @description: This will return all the user having status accountStatus
     */
    public Account createAccount(User user, AccountType accountType, Double balance, int creditScore) throws MessagingException, UnsupportedEncodingException {
        Account account = new Account(accountType, balance, creditScore);
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
     * @description: This will return the account details of the user based on user id
     */
    public Account getAccountByUserId(Long userId) {
        return accountRepository.findAccountByUser_Id(userId).orElseThrow(() -> new ResourceNotFoundException("Account", "account", "this user"));
    }
}
