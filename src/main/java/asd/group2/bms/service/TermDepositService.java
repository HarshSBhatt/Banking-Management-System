package asd.group2.bms.service;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.term_deposit.TermDeposit;
import asd.group2.bms.model.term_deposit.TermDepositDetail;
import asd.group2.bms.model.term_deposit.TermDepositStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.repository.AccountRepository;
import asd.group2.bms.repository.TermDepositDetailRepository;
import asd.group2.bms.repository.TermDepositRepository;
import asd.group2.bms.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TermDepositService {
    @Autowired
    TermDepositRepository termDepositRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TermDepositDetailRepository termDepositDetailRepository;

    @Autowired
    UserRepository userRepository;

    public List<TermDeposit> getTermDeposit() {
        return termDepositRepository.findAll();
    }

    public ResponseEntity<?> makeTermDepositRequest(Long userId, Double amount, Date currentDate, int years) {
        try {
            Optional<Account> account = accountRepository.findAccountByUser_Id(userId);
            if (account.isPresent() && account.get().getBalance() < amount) {
                return new ResponseEntity<>(new ApiResponse(false, "Not enough balance in your account!"),
                        HttpStatus.BAD_REQUEST);
            }
            if (!account.isPresent()) {
                return new ResponseEntity<>(new ApiResponse(false, "No account is there!"), HttpStatus.BAD_REQUEST);
            }

            // Balance Updated
            account.get().setBalance(account.get().getBalance() - amount);
            accountRepository.save(account.get());

            // Maturity Date calculation
            Calendar c = Calendar.getInstance();
            float interestRate = (float) 0.06;
            c.setTime(currentDate);
            c.add(Calendar.YEAR, years);
            Date maturityDate = c.getTime();

            // New TermDeposit
            TermDeposit termDeposit = new TermDeposit(String.valueOf(years), interestRate);
            Double maturityAmount = amount * Math.pow((1 + (interestRate / 12)), 12 * years);

            // New TermDepositDetail
            TermDepositDetail termDepositDetail = new TermDepositDetail();
            termDepositDetail.setAccount(account.get());
            termDepositDetail.setTermDeposit(termDeposit);
            termDepositDetail.setStartDate(currentDate);
            termDepositDetail.setInitialAmount(amount);
            termDepositDetail.setMaturityDate(maturityDate);
            termDepositDetail.setMaturityAmount(maturityAmount);
            termDepositDetail.setTermDepositStatus(TermDepositStatus.ACTIVE);

            // Saving TermDepositDetail & TermDeposit
            termDepositRepository.save(termDeposit);
            termDepositDetailRepository.save(termDepositDetail);

            return ResponseEntity.ok(new ApiResponse(true, "Term Deposit made successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(false, "Something went wrong!"), HttpStatus.BAD_REQUEST);
        }

    }
}
