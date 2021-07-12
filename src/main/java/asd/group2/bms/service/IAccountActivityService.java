package asd.group2.bms.service;

import org.springframework.http.ResponseEntity;

public interface IAccountActivityService {

  ResponseEntity<?> fundTransfer(Long senderAccountNumber,
                                 Long receiverAccountNumber,
                                 String comment, Double transactionAmount) throws Exception;

}
