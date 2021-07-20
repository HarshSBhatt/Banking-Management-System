package asd.group2.bms.repository;

import asd.group2.bms.model.cards.credit.CreditCardBill;

import java.util.Optional;

public interface CreditCardBillRepository {
     Long getCreditCardNumber(Long accountNumber);
     double getBillAmount (Long creditCardNumber);

     Boolean payCreditCardBill(Long billId);

     Optional<CreditCardBill> showBills(Long creditCardNo);
}
