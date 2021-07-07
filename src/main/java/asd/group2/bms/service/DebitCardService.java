package asd.group2.bms.service;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.debit.DebitCard;

public interface DebitCardService {

  DebitCard createDebitCard(Account account);

}
