package asd.group2.bms.controller;

import asd.group2.bms.payload.request.DebitCardSetLimitRequest;
import asd.group2.bms.payload.request.DebitCardSetPinRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.service.DebitCardService;
import asd.group2.bms.serviceImpl.AccountServiceImpl;
import asd.group2.bms.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DebitCardController {

  @Autowired
  DebitCardService debitCardService;

  @Autowired
  AccountServiceImpl accountService;

  /**
   * @param debitCardSetLimitRequest: Set transaction limit for the debit card
   * @return Returns whether transaction limit is updated
   */
  @PutMapping("/services/debitcard")
  @RolesAllowed({"ROLE_USER"})
  public ResponseEntity<?> debitCardSetLimit(
      @Valid @RequestBody DebitCardSetLimitRequest debitCardSetLimitRequest) {
    if (debitCardSetLimitRequest.getTransactionLimit() < AppConstants.MINIMUM_TRANSACTION_LIMIT) {
      return new ResponseEntity<>(new ApiResponse(false, "Minimum limit" +
          " is " + AppConstants.MINIMUM_TRANSACTION_LIMIT),
          HttpStatus.BAD_REQUEST);
    }
    if (debitCardSetLimitRequest.getTransactionLimit() > AppConstants.MAXIMUM_TRANSACTION_LIMIT) {
      return new ResponseEntity<>(new ApiResponse(false, "Maximum limit" +
          " is " + AppConstants.MAXIMUM_TRANSACTION_LIMIT),
          HttpStatus.BAD_REQUEST);
    }
    Boolean isUpdated = debitCardService.setDebitCardLimit(debitCardSetLimitRequest.getDebitCardNumber(), debitCardSetLimitRequest.getTransactionLimit());
    if (isUpdated) {
      return ResponseEntity.ok(new ApiResponse(true, "Transaction limit changed successfully!"));
    } else {
      return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while changing transaction limit"),
          HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * @param debitCardSetPinRequest: Request to set pin for the given debit card
   * @return Returns whether pin is updated
   */
  @PutMapping("/services/debitcard/pin")
  @RolesAllowed({"ROLE_USER"})
  public ResponseEntity<?> debitCardSetPin(
      @Valid @RequestBody DebitCardSetPinRequest debitCardSetPinRequest) {
    Boolean isUpdated = debitCardService.setDebitCardPin(debitCardSetPinRequest.getDebitCardNumber(), debitCardSetPinRequest.getPin());
    if (isUpdated) {
      return ResponseEntity.ok(new ApiResponse(true, "Pin is updated successfully!"));
    } else {
      return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while changing pin"),
          HttpStatus.BAD_REQUEST);
    }
  }

}
