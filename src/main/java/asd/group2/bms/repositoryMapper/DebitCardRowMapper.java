package asd.group2.bms.repositoryMapper;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.debit.DebitCard;
import asd.group2.bms.model.cards.debit.DebitCardStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class DebitCardRowMapper implements RowMapper<DebitCard> {

  @Override
  public DebitCard mapRow(ResultSet resultSet, int i) throws SQLException {

    DebitCard debitCard = new DebitCard();
    AccountRowMapper accountRowMapper = new AccountRowMapper();

    LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
    LocalDate updatedAt = resultSet.getDate("updated_at").toLocalDate();

    debitCard.setCreatedAt(createdAt.atStartOfDay(ZoneOffset.UTC).toInstant());
    debitCard.setUpdatedAt(updatedAt.atStartOfDay(ZoneOffset.UTC).toInstant());

    debitCard.setDebitCardNumber(resultSet.getLong("debit_card_number"));
    debitCard.setCvv(resultSet.getString("cvv"));
    debitCard.setDebitCardStatus(DebitCardStatus.valueOf(resultSet.getString("debit_card_status")));
    debitCard.setExpiryMonth(resultSet.getString("expiry_month"));
    debitCard.setExpiryYear(resultSet.getString("expiry_year"));
    debitCard.setPin(resultSet.getString("pin"));
    debitCard.setTransactionLimit(resultSet.getInt("transaction_limit"));

    Account account = accountRowMapper.mapRow(resultSet, i);

    debitCard.setAccount(account);

    return debitCard;
  }
}
