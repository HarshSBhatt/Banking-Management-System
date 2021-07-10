package asd.group2.bms.service;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.account.AccountType;
import asd.group2.bms.model.user.AccountStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.PagedResponse;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface AccountService {

  PagedResponse<User> getUserAccountListByStatus(AccountStatus accountStatus,
                                                 int page, int size);

  Account createAccount(User user, AccountType accountType,
                        Double balance, int creditScore) throws MessagingException, UnsupportedEncodingException;

  Account getAccountByUserId(Long userId);

}
