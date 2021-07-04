package asd.group2.bms.repository;

import asd.group2.bms.model.term_deposit.TermDepositDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermDepositDetailRepository extends JpaRepository<TermDepositDetail, Long> {

  /**
   * @param accountNumber: bank account number of the user
   * @descriptions: This will return the user's bank account number.
   */
  List<TermDepositDetail> findTermDepositDetailByAccount_AccountNumber(Long accountNumber);

}
