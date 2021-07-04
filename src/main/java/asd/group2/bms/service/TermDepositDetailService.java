package asd.group2.bms.service;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.term_deposit.TermDepositDetail;
import asd.group2.bms.repository.TermDepositDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TermDepositDetailService {

    @Autowired
    TermDepositDetailRepository termDepositDetailRepository;

    @Autowired
    AccountService accountService;

    public List<TermDepositDetail> getTermDepositDetail(Long userId) {

        Account account = accountService.getAccountByUserId(userId);
        List<TermDepositDetail> termDepositDetailList = termDepositDetailRepository.findTermDepositDetailByAccount_AccountNumber(account.getAccountNumber());
        if (termDepositDetailList.size() > 0) {
            return termDepositDetailList;
        }
        return new ArrayList<>();
    }
}
