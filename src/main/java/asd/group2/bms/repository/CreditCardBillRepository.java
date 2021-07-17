package asd.group2.bms.repository;

import asd.group2.bms.model.cards.credit.CreditCardBill;

public interface CreditCardBillRepository {
     Long getCreditCardNumber(Long accountNumber);
     double getBillAmount (Long creditCardNumber);

     Boolean payCreditCardBill(Long billId);

     CreditCardBill showBills(Long creditCardNo);
}
