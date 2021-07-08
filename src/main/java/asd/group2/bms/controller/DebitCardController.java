package asd.group2.bms.controller;

import asd.group2.bms.model.cards.debit.DebitCard;
import asd.group2.bms.payload.request.DebitCardSetLimitRequest;
import asd.group2.bms.payload.request.DebitCardSetPinRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.service.DebitCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DebitCardController {

    @Autowired
    DebitCardService debitCardService;

    /**
     *
     * @param debitCardSetLimitRequest: Set transaction limit for the debit card
     * @return Returns whether transaction limit is updated
     */
    @PutMapping("/services/debitcard")
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<?> debitCardSetLimit(
            @Valid @RequestBody DebitCardSetLimitRequest debitCardSetLimitRequest) {
        if (debitCardSetLimitRequest.getTransactionLimit() < 5000) {
            return new ResponseEntity<>(new ApiResponse(false, "Minimum limit is 5000"),
                    HttpStatus.BAD_REQUEST);
        }
        if (debitCardSetLimitRequest.getTransactionLimit() > 50000) {
            return new ResponseEntity<>(new ApiResponse(false, "Maximum limit is 50000"),
                    HttpStatus.BAD_REQUEST);
        }
        DebitCard debitCard = debitCardService.setDebitCardLimit(debitCardSetLimitRequest.getDebitCardNumber(), debitCardSetLimitRequest.getTransactionLimit());
        if (debitCard.getTransactionLimit() == debitCardSetLimitRequest.getTransactionLimit()) {
            return ResponseEntity.ok(new ApiResponse(true, "Transaction limit changed successfully!"));
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while changing transaction limit"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param debitCardSetPinRequest: Request to set pin for the given debit card
     * @return Returns whether pin is updated
     */
    @PutMapping("/services/debitcard")
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<?> debitCardSetPin(
            @Valid @RequestBody DebitCardSetPinRequest debitCardSetPinRequest) {
        DebitCard debitCard = debitCardService.setDebitCardPin(debitCardSetPinRequest.getDebitCardNumber(), debitCardSetPinRequest.getPin());
        if (debitCard.getPin() == debitCardSetPinRequest.getPin()) {
            return ResponseEntity.ok(new ApiResponse(true, "Pin is updated successfully!"));
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while changing pin"),
                    HttpStatus.BAD_REQUEST);
        }
    }



    }
