package asd.group2.bms.repository;

public interface CreditCardBillRepository {
     Long getCreditCardNumber(Long accountNumber);

     Boolean payCreditCardBill(Long creditCardNumber, Long accountNumber);
}
