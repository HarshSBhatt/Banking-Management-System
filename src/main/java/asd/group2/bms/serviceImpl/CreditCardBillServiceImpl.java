package asd.group2.bms.serviceImpl;

import asd.group2.bms.repository.CreditCardBillRepository;
import asd.group2.bms.service.ICreditCardBillService;
import org.springframework.stereotype.Service;

@Service
public class CreditCardBillServiceImpl implements ICreditCardBillService {

    static Long getCreditCardByAccountNumber(Long accountNumber)
    {
        return CreditCardBillRepository.getCreditCardNumber(accountNumber);
    }

    public Boolean payCreditCardBill(Long accountNumber) {
        Long creditCardNumber = CreditCardBillRepository.getCreditCardNumber(accountNumber);
        return CreditCardBillRepository.payCreditCardBill(creditCardNumber,accountNumber);
    }
}
