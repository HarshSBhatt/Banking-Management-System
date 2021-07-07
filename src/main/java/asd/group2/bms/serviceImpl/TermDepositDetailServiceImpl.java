package asd.group2.bms.serviceImpl;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.term_deposit.TermDepositDetail;
import asd.group2.bms.model.term_deposit.TermDepositStatus;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.repository.AccountRepository;
import asd.group2.bms.repository.TermDepositDetailRepository;
import asd.group2.bms.service.TermDepositDetailService;
import asd.group2.bms.util.AppConstants;
import asd.group2.bms.util.CustomEmailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TermDepositDetailServiceImpl implements TermDepositDetailService {

  @Autowired
  TermDepositDetailRepository termDepositDetailRepository;

  @Autowired
  AccountServiceImpl accountService;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  CustomEmailImpl customEmail;


  public ResponseEntity<?> makeTermDepositRequest(Long userId, String email, String firstName, Double fdAmount, Date currentDate, int duration) {
    try {
      Account account = accountService.getAccountByUserId(userId);
      if (fdAmount < 1000) {
        return new ResponseEntity<>(new ApiResponse(false, "Minimum amount to create Fixed Deposit is $1000!"),
            HttpStatus.BAD_REQUEST);
      }
      if (account.getBalance() < fdAmount) {
        return new ResponseEntity<>(new ApiResponse(false, "Not enough balance in your account!"),
            HttpStatus.BAD_REQUEST);
      }
      if (account.getBalance() - 1000 < fdAmount) {
        return new ResponseEntity<>(new ApiResponse(false, "Minimum $1000 is required after creating Fixed Deposit in your account!"),
            HttpStatus.BAD_REQUEST);
      }

      // Balance Updated
      Double newBalance = account.getBalance() - fdAmount;
      account.setBalance(newBalance);
      accountRepository.save(account);

      //Sending email
      customEmail.sendBalanceDeductionMail(email, firstName, fdAmount, newBalance);

      // Maturity Date calculation
      Calendar c = Calendar.getInstance();
      float interestRate = AppConstants.DEFAULT_INTEREST_VALUE;
      c.setTime(currentDate);
      c.add(Calendar.YEAR, duration);
      Date maturityDate = c.getTime();

      // Calculating maturityAmount
      Double maturityAmount = fdAmount * Math.pow((1 + (interestRate / 12)), 12 * duration);

      // New TermDepositDetail
      TermDepositDetail termDepositDetail = new TermDepositDetail(account, currentDate, fdAmount, duration, interestRate, maturityDate, maturityAmount, TermDepositStatus.ACTIVE);

      // Saving TermDepositDetail & TermDeposit
      termDepositDetailRepository.save(termDepositDetail);

      return ResponseEntity.ok(new ApiResponse(true, "Term Deposit made successfully!"));
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(new ApiResponse(false, "Something went wrong!"), HttpStatus.BAD_REQUEST);
    }

  }

  public TermDepositDetail getTermDepositDetailById(Long id) {
    return termDepositDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Termdeposit", "termdeposit", "temp"));
  }

  public List<TermDepositDetail> getTermDepositDetail(Long userId) {

    Account account = accountService.getAccountByUserId(userId);
    List<TermDepositDetail> termDepositDetailList = termDepositDetailRepository.findTermDepositDetailByAccount_AccountNumber(account.getAccountNumber());
    if (termDepositDetailList.size() > 0) {
      return termDepositDetailList;
    }
    return new ArrayList<>();
  }

}
