package asd.group2.bms.serviceImpl;

import asd.group2.bms.repository.CreditCardBillRepository;
import asd.group2.bms.service.ICreditCardBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardBillServiceImpl implements ICreditCardBillService {

    @Autowired
    CreditCardBillRepository CreditCard;

    public Long getCreditCardByAccountNumber(Long accountNumber)
    {
        return CreditCard.getCreditCardNumber(accountNumber);
    }

    public Boolean payCreditCardBill(Long accountNumber) {
        Long creditCardNumber = CreditCard.getCreditCardNumber(accountNumber);
        return CreditCard.payCreditCardBill(creditCardNumber,accountNumber);
    }
}
