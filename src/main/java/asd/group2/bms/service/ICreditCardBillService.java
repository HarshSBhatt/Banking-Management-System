package asd.group2.bms.service;

import asd.group2.bms.model.cards.credit.CreditCardBill;

public interface ICreditCardBillService {

      Boolean payCreditCardBill(Long accountNumber,Long billId);


      CreditCardBill getBills(Long creditCardNo);
}
