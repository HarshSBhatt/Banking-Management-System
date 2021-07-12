package asd.group2.bms.controller;

import asd.group2.bms.payload.request.FundTransferRequest;
import asd.group2.bms.serviceImpl.AccountActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountActivityController {

  @Autowired
  AccountActivityServiceImpl accountActivityService;

  @PostMapping("/account/activity")
  public ResponseEntity<?> fundTransfer(@Valid @RequestBody FundTransferRequest fundTransferRequest) throws Exception {
    Long senderAccountNumber = fundTransferRequest.getSenderAccountNumber();
    Long receiverAccountNumber = fundTransferRequest.getReceiverAccountNumber();
    String comment = fundTransferRequest.getComment();
    Double transactionAmount = fundTransferRequest.getTransactionAmount();

    return accountActivityService.fundTransfer(senderAccountNumber,
        receiverAccountNumber, comment, transactionAmount);
  }

}
