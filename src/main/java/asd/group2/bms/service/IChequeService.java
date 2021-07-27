package asd.group2.bms.service;

import asd.group2.bms.model.cards.credit.CreditCardBill;
import asd.group2.bms.model.cheque.ChequeStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IChequeService {

    Boolean startCheque(Long senderAccountNumber,
                                  Long receiverAccountNumber,
                                  Long chequeNumber, Double chequeAmount);
    public void processCheque(Long chequeNumber,Long senderAccountNumber,
        Long receiverAccountNumber,Double chequeAmount);

    public ChequeStatus chequeStatus(Long chequeNumber);
}
