package asd.group2.bms.controller;


import asd.group2.bms.payload.request.*;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.service.ChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        Boolean chequeStatus =
            chequeService.startCheque(startChequeRequest.getAccountNumberSender(),startChequeRequest.getAccountNumberReceiver(),
                startChequeRequest.getChequeNumber(),startChequeRequest.getAmount());
        if (chequeStatus) {
            return ResponseEntity.ok(new ApiResponse(true, "Bill paid successfully!"));
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while paying bill balance might be insufficient"),
                HttpStatus.BAD_REQUEST);
        }
    }



}
