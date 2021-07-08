package asd.group2.bms.repositoryImpl;

import asd.group2.bms.model.cards.debit.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCardRepositoryImpl extends JpaRepository<DebitCard, Long> {

}
