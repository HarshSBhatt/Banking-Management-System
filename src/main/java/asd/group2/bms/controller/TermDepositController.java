package asd.group2.bms.controller;

import asd.group2.bms.payload.request.TermDepositRequest;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.service.TermDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class TermDepositController {

  @Autowired
  TermDepositService termDepositService;

  @PostMapping("/services/termdeposit")
  public ResponseEntity<?> postTermDeposit(@CurrentLoggedInUser UserPrincipal currentUser, @RequestBody TermDepositRequest termDepositRequest) {
    Long currentUserId = currentUser.getId();
    String email = currentUser.getEmail();
    String firstName = currentUser.getFirstName();
    return termDepositService.makeTermDepositRequest(currentUserId,email,firstName,termDepositRequest.getInitialAmount(), new Date(), termDepositRequest.getYears());
  }

}
