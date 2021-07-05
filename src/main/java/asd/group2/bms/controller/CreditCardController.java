package asd.group2.bms.controller;

import asd.group2.bms.model.cards.credit.CreditCardStatus;
import asd.group2.bms.payload.response.CreditCardListResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.service.CreditCardService;
import asd.group2.bms.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

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
}
