package asd.group2.bms.repository;

import asd.group2.bms.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  /**
   * @param accountNumber: account number of the user
   * @return This will return the account detail of the user
   */
  Optional<Account> findByAccountNumber(Long accountNumber);

  /**
   * @param userId: user id of the user
   * @return This will return the account detail of the user
   */
  Optional<Account> findAccountByUser_Id(Long userId);

}