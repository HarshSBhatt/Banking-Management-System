package asd.group2.bms.service;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.debit.DebitCard;

public interface DebitCardService {

  DebitCard createDebitCard(Account account);

  /**
   *
   * @param debitCardNumber: Debit Card Number
   * @return Returns Debit card of the to change transaction limit
   */
  public DebitCard getDebitCardByNumber(Long debitCardNumber) {
    return debitCardRepository.findById(debitCardNumber).orElseThrow(() -> new ResourceNotFoundException("DebitCard Number", "debitCardNumber", debitCardNumber));
  }

  /**
   *
   * @param debitCardNumber: Debit Card Number
   * @param transactionLimit: Transaction limit to set for the given debit card number
   * @return Returns Debit card of the changed transaction limit
   */
  public DebitCard setDebitCardLimit(Long debitCardNumber, Integer transactionLimit) {
    DebitCard debitCard = getDebitCardByNumber(debitCardNumber);

    debitCard.setTransactionLimit(transactionLimit);
    return debitCardRepository.save(debitCard);
  }


}
