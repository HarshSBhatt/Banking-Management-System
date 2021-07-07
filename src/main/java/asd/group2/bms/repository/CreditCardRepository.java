package asd.group2.bms.repository;

import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.cards.credit.CreditCardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    /**
     * @param creditCardStatus: request status
     * @descriptions: This will return the Credit Card records by status.
     */
    Page<CreditCard> findByCreditCardStatusEquals(CreditCardStatus creditCardStatus, Pageable pageable);
}
