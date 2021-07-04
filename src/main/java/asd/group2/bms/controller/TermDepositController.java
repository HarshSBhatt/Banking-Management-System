package asd.group2.bms.controller;

import asd.group2.bms.model.term_deposit.TermDeposit;
import asd.group2.bms.payload.request.TermDepositRequest;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.service.TermDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class TermDepositController {
    @Autowired
    TermDepositService termDepositService;


    @GetMapping("/services/termdeposit")
    public List<TermDeposit> getTermDeposit() {
        return termDepositService.getTermDeposit();
    }

    @PostMapping("/services/termdeposit")
    public ResponseEntity<?> postTermDeposit(@CurrentLoggedInUser UserPrincipal currentUser,@RequestBody TermDepositRequest termDepositRequest) {
        Long currentUserId = currentUser.getId();
        return termDepositService.makeTermDepositRequest(currentUserId, termDepositRequest.getInitialAmount(), new Date(), termDepositRequest.getYears());
    }
    
}
