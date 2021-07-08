package asd.group2.bms.repository;

import asd.group2.bms.model.cards.debit.DebitCard;

import java.util.Optional;

public interface DebitCardRepository {

  /**
   * @param debitCardNumber: number of the debit card
   * @return This will return user based on user id.
   */
  Optional<DebitCard> findById(Long debitCardNumber);

  /**
   * @param debitCard: Debit Card details
   * @return This will return debitCard if inserted in the database.
   */
  DebitCard save(DebitCard debitCard);

  /**
   * @param debitCard: Debit Card details
   * @return true if debitCard id updated else false
   */
  Boolean update(DebitCard debitCard);

}
