package asd.group2.bms.service;

import asd.group2.bms.model.term_deposit.TermDeposit;
import asd.group2.bms.repository.TermDepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermDepositService {
    @Autowired
    TermDepositRepository termDepositRepository;

    public List<TermDeposit> getTermDeposit() {
        return termDepositRepository.findAll();
    }
}
