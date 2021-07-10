package asd.group2.bms.repositoryMapper;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.account.AccountType;
import asd.group2.bms.model.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;


public class AccountRowMapper implements RowMapper<Account> {

  @Override
  public Account mapRow(ResultSet resultSet, int i) throws SQLException {
    Account account = new Account();
    UserRowMapper userRowMapper = new UserRowMapper();

    LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
    LocalDate updatedAt = resultSet.getDate("updated_at").toLocalDate();

    account.setCreatedAt(createdAt.atStartOfDay(ZoneOffset.UTC).toInstant());
    account.setUpdatedAt(updatedAt.atStartOfDay(ZoneOffset.UTC).toInstant());
    account.setAccountNumber(resultSet.getLong("account_number"));
    account.setAccountType(AccountType.valueOf(resultSet.getString(
        "account_type")));
    account.setBalance(resultSet.getDouble("balance"));
    account.setCreditScore(resultSet.getInt("credit_score"));

    User user = userRowMapper.mapRow(resultSet, i);

    account.setUser(user);

    return account;
  }

}
