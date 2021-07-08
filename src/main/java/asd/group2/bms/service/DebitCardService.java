package asd.group2.bms.service;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.debit.DebitCard;
import asd.group2.bms.model.cards.debit.DebitCardStatus;
import asd.group2.bms.repository.DebitCardRepository;
import asd.group2.bms.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Service
public class DebitCardService {

  @Autowired
  DebitCardRepository debitCardRepository;

  /**
   * @param account: Account of user whose debit card is being created
   * @return This will return the debit card details
   */
  public DebitCard createDebitCard(Account account) {
    Random random = new Random();
    Date date = new Date();
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    int month = localDate.getMonthValue();
    int currentYear = localDate.getYear();

    String expiryMonth = String.valueOf(month);
    String expiryYear = String.valueOf(currentYear + 4);

    String pin = String.format("%04d", random.nextInt(10000));
    String cvv = String.format("%06d", random.nextInt(1000000));

    String debitCardNumber = new Helper().generateRandomDigits(16);
    DebitCard debitCard = new DebitCard(Long.parseLong(debitCardNumber),
        account, pin, 50000, DebitCardStatus.ACTIVE, expiryYear, expiryMonth, cvv);

    return debitCardRepository.save(debitCard);
  }

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

  /**
   *
   * @param debitCardNumber: Debit card number
   * @param pin: Pin to set for the given debit card
   * @return Returns debit card of the changed pin
   */
  public DebitCard setDebitCardPin(Long debitCardNumber, String pin) {
    DebitCard debitCard = getDebitCardByNumber(debitCardNumber);
    debitCard.setPin(pin);
    return debitCardRepository.save(debitCard);
  }


}
