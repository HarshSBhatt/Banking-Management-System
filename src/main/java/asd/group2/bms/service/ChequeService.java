package asd.group2.bms.service;

import org.springframework.http.ResponseEntity;

public interface ChequeService {

    Boolean startCheque(Long senderAccountNumber,
                                  Long receiverAccountNumber,
                                  Long chequeNumber, Double chequeAmount);
}
