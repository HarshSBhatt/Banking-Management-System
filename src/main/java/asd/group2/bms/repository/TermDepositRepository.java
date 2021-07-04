package asd.group2.bms.repository;

import asd.group2.bms.model.term_deposit.TermDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermDepositRepository extends JpaRepository<TermDeposit, Long> {

}
