package asd.group2.bms.repositoryMapper;

import asd.group2.bms.model.cards.debit.DebitCard;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DebitCardRowMapper implements RowMapper<DebitCard> {

  @Override
  public DebitCard mapRow(ResultSet resultSet, int i) throws SQLException {
    return null;
  }

}
