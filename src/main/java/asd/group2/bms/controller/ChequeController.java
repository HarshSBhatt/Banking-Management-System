package asd.group2.bms.controller;


import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.cards.credit.CreditCardStatus;
import asd.group2.bms.payload.request.CreditCardRequest;
import asd.group2.bms.payload.request.CreditCardSetPinRequest;
import asd.group2.bms.payload.request.UpdateCreditCardStatusRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.CreditCardListResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.service.IAccountService;
import asd.group2.bms.service.ICreditCardBillService;
import asd.group2.bms.service.ICreditCardService;
import asd.group2.bms.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")

public class ChequeController {


}
