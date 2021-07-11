package asd.group2.bms.repository;

import asd.group2.bms.model.account.AccountActivity;

public interface AccountActivityRepository {

  /**
   * @param accountActivity: Account activity details
   * @return This will return account activity if inserted in the database.
   */
  AccountActivity save(AccountActivity accountActivity);

  /**
   * @param accountActivity: Account activity details
   * @return true if account activity updated else false
   */
  Boolean update(AccountActivity accountActivity);

}
