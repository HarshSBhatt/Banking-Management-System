package asd.group2.bms.repositoryImpl;

import asd.group2.bms.model.term_deposit.TermDepositDetail;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface TermDepositDetailRepositoryImpl extends JpaRepository<TermDepositDetail, Long> {

  /**
   * @param accountNumber: bank account number of the user
   * @return This will return the user's bank account number.
   */
  List<TermDepositDetail> findTermDepositDetailByAccount_AccountNumber(Long accountNumber);

}
