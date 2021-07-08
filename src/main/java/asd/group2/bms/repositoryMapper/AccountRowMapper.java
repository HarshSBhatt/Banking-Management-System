package asd.group2.bms.repositoryMapper;

import asd.group2.bms.model.account.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {

  @Override
  public Account mapRow(ResultSet resultSet, int i) throws SQLException {
    return null;
  }

}
