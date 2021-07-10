package asd.group2.bms.repositoryMapper;

import asd.group2.bms.model.term_deposit.TermDepositDetail;
import asd.group2.bms.model.term_deposit.TermDepositStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class TermDepositDetailRowMapper implements RowMapper<TermDepositDetail> {

  @Override
  public TermDepositDetail mapRow(ResultSet resultSet, int i) throws SQLException {
    TermDepositDetail termDepositDetail = new TermDepositDetail();
    UserRowMapper userRowMapper = new UserRowMapper();

    LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
    LocalDate updatedAt = resultSet.getDate("updated_at").toLocalDate();

    termDepositDetail.setCreatedAt(createdAt.atStartOfDay(ZoneOffset.UTC).toInstant());
    termDepositDetail.setUpdatedAt(updatedAt.atStartOfDay(ZoneOffset.UTC).toInstant());
    termDepositDetail.setTermDepositId(resultSet.getLong("term_deposit_id"));
    termDepositDetail.setTermDepositStatus(TermDepositStatus.valueOf(resultSet.getString(
        "term_deposit_status")));


    resignRequest.setResignId(resultSet.getLong("resign_id"));
    resignRequest.setDate(resultSet.getDate("date"));
    resignRequest.setReason(resultSet.getString("reason"));
    resignRequest.setRequestStatus(RequestStatus.valueOf(resultSet.getString("request_status")));

    User user = userRowMapper.mapRow(resultSet, i);
    resignRequest.setUser(user);

    return resignRequest;

  }

}
