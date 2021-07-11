package asd.group2.bms.service;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.debit.DebitCard;

public interface DebitCardService {

  DebitCard createDebitCard(Account account);

  DebitCard getDebitCardByNumber(Long debitCardNumber);

  DebitCard getDebitCardByAccountNumber(Long accountNumber);

  Boolean setDebitCardLimit(Long debitCardNumber, Integer transactionLimit);

  Boolean setDebitCardPin(Long debitCardNumber, String pin);

}
