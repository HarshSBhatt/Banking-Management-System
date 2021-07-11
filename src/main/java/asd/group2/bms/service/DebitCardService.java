package asd.group2.bms.service;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.debit.DebitCard;
import asd.group2.bms.model.cards.debit.DebitCardStatus;

public interface DebitCardService {

  DebitCard createDebitCard(Account account);

  DebitCard getDebitCardByNumber(Long debitCardNumber);

  Boolean setDebitCardLimit(Long debitCardNumber, Integer transactionLimit);

  Boolean setDebitCardPin(Long debitCardNumber, String pin);

  Boolean setDebitCardRequestStatus(Long debitCardNumber, DebitCardStatus debitCardStatus);

}
