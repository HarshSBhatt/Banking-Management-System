package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.account.AccountActivity;
import asd.group2.bms.model.account.ActivityType;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.repositoryImpl.AccountActivityRepositoryImpl;
import asd.group2.bms.security.CustomTransactional;
import asd.group2.bms.service.AccountActivityService;
import asd.group2.bms.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@CustomTransactional
@Service
public class AccountActivityServiceImpl implements AccountActivityService {

  @Autowired
  AccountServiceImpl accountService;

  @Autowired
  AccountActivityRepositoryImpl accountActivityRepository;

  @Autowired
  CustomEmailImpl customEmail;

  @Override
  public ResponseEntity<?> fundTransfer(Long senderAccountNumber,
                                        Long receiverAccountNumber,
                                        String comment,
                                        Double transactionAmount) throws Exception {
    try {
      Account senderAccount =
          accountService.getAccountByAccountNumber(senderAccountNumber);

      Account receiverAccount =
          accountService.getAccountByAccountNumber(receiverAccountNumber);

      if (transactionAmount > AppConstants.MAXIMUM_TRANSACTION_LIMIT) {
        return new ResponseEntity<>(new ApiResponse(false, "Transaction amount " +
            "exceed maximum transaction amount"),
            HttpStatus.BAD_REQUEST);
      }

      Double receiverNewBalance =
          receiverAccount.getBalance() + transactionAmount;
      Double senderNewBalance = senderAccount.getBalance() - transactionAmount;

      receiverAccount.setBalance(receiverNewBalance);
      senderAccount.setBalance(senderNewBalance);

      if (senderNewBalance < AppConstants.MINIMUM_BALANCE) {
        return new ResponseEntity<>(new ApiResponse(false, "Not enough balance in your account!"),
            HttpStatus.BAD_REQUEST);
      }

      Boolean isBalanceCredited =
          accountService.updateAccountBalance(receiverAccount);

      Boolean isBalanceDebited =
          accountService.updateAccountBalance(senderAccount);

      if (!isBalanceCredited) {
        throw new Exception("Balance was not credited to receiver's account");
      }

      if (!isBalanceDebited) {
        throw new Exception("Balance was not debited from sender's account");
      }

      AccountActivity senderAccountActivity =
          new AccountActivity(senderAccount, ActivityType.WITHDRAWAL,
              transactionAmount, comment);

      AccountActivity receiverAccountActivity =
          new AccountActivity(receiverAccount, ActivityType.DEPOSIT,
              transactionAmount, comment);

      try {
        AccountActivity senderActivity =
            accountActivityRepository.save(senderAccountActivity);
        AccountActivity receiverActivity =
            accountActivityRepository.save(receiverAccountActivity);

        String senderEmail = senderAccount.getUser().getEmail();
        String senderFirstName = senderAccount.getUser().getFirstName();
        String receiverEmail = receiverAccount.getUser().getEmail();
        String receiverFirstName = receiverAccount.getUser().getFirstName();

        customEmail.sendAccountActivityMail(senderEmail, senderFirstName,
            transactionAmount, senderNewBalance, "debited", senderActivity.getActivityId());

        customEmail.sendAccountActivityMail(receiverEmail, receiverFirstName,
            transactionAmount, receiverNewBalance, "credited",
            receiverActivity.getActivityId());

        return ResponseEntity.ok(new ApiResponse(true, "Funds transferred " +
            "successfully"));
      } catch (Exception e) {
        throw new Exception("Error occurred while transferring funds");
      }
    } catch (Exception e) {
      return new ResponseEntity<>(new ApiResponse(false, "Something went " +
          "wrong while transferring funds! please check details properly"),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
