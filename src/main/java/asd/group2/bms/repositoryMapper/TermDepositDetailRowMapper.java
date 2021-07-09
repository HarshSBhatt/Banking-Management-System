package asd.group2.bms.repositoryMapper;

import asd.group2.bms.model.term_deposit.TermDepositDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TermDepositDetailRowMapper implements RowMapper<TermDepositDetail> {

  @Override
  public TermDepositDetail mapRow(ResultSet resultSet, int i) throws SQLException {
    return null;
  }

}
