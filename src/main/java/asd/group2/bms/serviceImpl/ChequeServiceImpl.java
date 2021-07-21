package asd.group2.bms.serviceImpl;

import asd.group2.bms.repository.IChequeRepository;
import asd.group2.bms.service.ChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class ChequeServiceImpl implements ChequeService {
    @Autowired
    IChequeRepository repository;

    @Override
    public Boolean startCheque(Long senderAccountNumber,
        Long receiverAccountNumber, Long chequeNumber, Double chequeAmount) {

        return repository.update_cheque(senderAccountNumber,
            receiverAccountNumber,
            chequeNumber,chequeAmount);


    }
}
