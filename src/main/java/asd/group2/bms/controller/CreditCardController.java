package asd.group2.bms.controller;

import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.cards.credit.CreditCardStatus;
import asd.group2.bms.payload.request.UpdateCreditCardStatusRequest;
import asd.group2.bms.payload.request.UpdateResignStatusRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.CreditCardListResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.service.CreditCardService;
import asd.group2.bms.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CreditCardController {
    @Autowired
    CreditCardService creditCardService;

    /**
     * @param creditCardStatus: Credit card status
     * @description: Return all the credit cards having status creditCardStatus
     */
    @GetMapping("/services/creditcards")
    @RolesAllowed({"ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public PagedResponse<CreditCardListResponse> getCreditCardByStatus(
            @RequestParam(value = "creditCardStatus") CreditCardStatus creditCardStatus,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return creditCardService.getCreditCardListByStatus(creditCardStatus, page, size);
    }

    /**
     * @param updateCreditCardStatusRequest: credit card number and credit card status
     * @return success or failure response with appropriate message
     */
    @PutMapping("/services/creditcards")
    @RolesAllowed({"ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<?> updateCreditCardRequestStatus(
            @Valid @RequestBody UpdateCreditCardStatusRequest updateCreditCardStatusRequest){
        CreditCard creditCard = creditCardService.setCreditCardRequestStatus(updateCreditCardStatusRequest.getCreditCardNumber(), updateCreditCardStatusRequest.getCreditCardStatus());
        if (creditCard != null) {
            return ResponseEntity.ok(new ApiResponse(true, "Credit Card request status changed successfully!"));
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while changing Credit Card request status!"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
