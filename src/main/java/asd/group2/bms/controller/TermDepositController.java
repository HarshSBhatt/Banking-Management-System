package asd.group2.bms.controller;

import asd.group2.bms.model.term_deposit.TermDeposit;
import asd.group2.bms.service.TermDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TermDepositController {
    @Autowired
    TermDepositService termDepositService;

    @GetMapping("/services/termdeposit")
    public List<TermDeposit> getTermDeposit() {
        return termDepositService.getTermDeposit();
    }
}
