package asd.group2.bms.service;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.term_deposit.TermDepositDetail;
import asd.group2.bms.repository.AccountRepository;
import asd.group2.bms.repository.TermDepositDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TermDepositDetailService {

    @Autowired
    TermDepositDetailRepository termDepositDetailRepository;

    @Autowired
    AccountRepository accountRepository;

    public List<TermDepositDetail> getTermDepositDetail(Long userId) {

        Optional<Account> account = accountRepository.findAccountByUser_Id(userId);
        if (account.isPresent()) {
            List<TermDepositDetail> termDepositDetailList = termDepositDetailRepository.findTermDepositDetailByAccount_AccountNumber(account.get().getAccountNumber());
            if (termDepositDetailList.size() > 0) {
                return termDepositDetailList;
            }
        }
        return null;
    }


}
