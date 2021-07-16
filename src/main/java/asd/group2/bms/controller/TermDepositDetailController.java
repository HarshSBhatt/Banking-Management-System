package asd.group2.bms.controller;

import asd.group2.bms.model.term_deposit.TermDepositDetail;
import asd.group2.bms.payload.request.TermDepositRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.service.ITermDepositDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TermDepositDetailController {

  @Autowired
  ITermDepositDetailService termDepositDetailService;

  /**
   * @description: Return all the term deposits of current user
   */
  @GetMapping("/services/term-deposit")
  @RolesAllowed({"ROLE_USER"})
  public ResponseEntity<?> getTermDepositDetail(@CurrentLoggedInUser UserPrincipal currentUser) {
    List<TermDepositDetail> termDepositDetailList = termDepositDetailService.getTermDepositDetail(currentUser.getId());
    if (termDepositDetailList != null) {
      return ResponseEntity.ok(termDepositDetailList);
    } else {
      return new ResponseEntity<>(new ApiResponse(false, "No term deposits found!"),
          HttpStatus.BAD_REQUEST);
    }
  }

  /**
   *
   * @param currentUser
   * @param termDepositRequest
   * @return ResponseEntity with the status message
   * @throws Exception
   */
  @PostMapping("/services/term-deposit")
  @RolesAllowed({"ROLE_USER"})
  public ResponseEntity<?> makeTermDepositRequest(@CurrentLoggedInUser UserPrincipal currentUser, @RequestBody TermDepositRequest termDepositRequest) throws Exception {
    Long currentUserId = currentUser.getId();
    String email = currentUser.getEmail();
    String firstName = currentUser.getFirstName();
    return termDepositDetailService.makeTermDepositRequest(currentUserId, email, firstName, termDepositRequest.getInitialAmount(), new Date(), termDepositRequest.getYears());
  }

  /**
   *
   * @param termDepositId
   * @return TermDeposit or ResourceNotFoundException
   */
  @GetMapping("/services/term-deposit/{termDepositId}")
  @RolesAllowed({"ROLE_USER"})
  public TermDepositDetail getTermDepositDetailById(@PathVariable(name = "termDepositId") String termDepositId) {
    Long fdId = Long.parseLong(termDepositId);
    return termDepositDetailService.getTermDepositDetailById(fdId);
  }

}
