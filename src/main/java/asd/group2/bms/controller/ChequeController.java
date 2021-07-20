package asd.group2.bms.controller;


import asd.group2.bms.payload.request.*;
import asd.group2.bms.service.ChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")

public class ChequeController {

    @Autowired
    ChequeService chequeService;

    @PutMapping("/services/creditcards")
    @RolesAllowed({"ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<?> start_cheque (@Valid @RequestBody StartChequeRequest startChequeRequest){

        return chequeService.startCheque(startChequeRequest.getAccountNumberSender(),startChequeRequest.getAccountNumberReceiver(),
                startChequeRequest.getChequeNumber(),startChequeRequest.getAmount());
    }

}
