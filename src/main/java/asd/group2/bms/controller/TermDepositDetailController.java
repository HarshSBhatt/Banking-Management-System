package asd.group2.bms.controller;

import asd.group2.bms.model.term_deposit.TermDepositDetail;
import asd.group2.bms.payload.request.TermDepositRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.serviceImpl.TermDepositDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TermDepositDetailController {

  @Autowired
  TermDepositDetailServiceImpl termDepositDetailService;

  /**
   * @description: Return all the term deposits of current user
   */
  @GetMapping("/services/term-deposit")
  public ResponseEntity<?> getTermDepositDetail(@CurrentLoggedInUser UserPrincipal currentUser) {
    List<TermDepositDetail> termDepositDetailList = termDepositDetailService.getTermDepositDetail(currentUser.getId());
    if (termDepositDetailList != null) {
      return ResponseEntity.ok(termDepositDetailList);
    } else {
      return new ResponseEntity<>(new ApiResponse(false, "No term deposits found!"),
          HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/services/term-deposit")
  public ResponseEntity<?> makeTermDepositRequest(@CurrentLoggedInUser UserPrincipal currentUser, @RequestBody TermDepositRequest termDepositRequest) {
    Long currentUserId = currentUser.getId();
    String email = currentUser.getEmail();
    String firstName = currentUser.getFirstName();
    return termDepositDetailService.makeTermDepositRequest(currentUserId, email, firstName, termDepositRequest.getInitialAmount(), new Date(), termDepositRequest.getYears());
  }

  @GetMapping("/services/term-deposit/{termDepositId}")
  public TermDepositDetail getTermDepositDetailById(@PathVariable(name = "termDepositId") String termDepositId) {
    Long id = Long.parseLong(termDepositId);
    return termDepositDetailService.getTermDepositDetailById(id);
  }

}
