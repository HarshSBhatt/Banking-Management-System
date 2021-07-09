package asd.group2.bms.repositoryMapper;

import asd.group2.bms.model.resign.RequestStatus;
import asd.group2.bms.model.resign.ResignRequest;
import asd.group2.bms.model.user.Role;
import asd.group2.bms.model.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class ResignRowMapper implements RowMapper<ResignRequest> {

  @Override
  public ResignRequest mapRow(ResultSet resultSet, int i) throws SQLException {
    ResignRequest resignRequest = new ResignRequest();
    User user = new User();
    Role role = new Role();

    User mappedUser = new UserRowMapper().mapRow(resultSet, i);

    LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
    LocalDate updatedAt = resultSet.getDate("updated_at").toLocalDate();

    resignRequest.setCreatedAt(createdAt.atStartOfDay(ZoneOffset.UTC).toInstant());
    resignRequest.setUpdatedAt(updatedAt.atStartOfDay(ZoneOffset.UTC).toInstant());
    resignRequest.setResignId(resultSet.getLong("resign_id"));
    resignRequest.setDate(resultSet.getDate("date"));
    resignRequest.setReason(resultSet.getString("reason"));
    resignRequest.setRequestStatus(RequestStatus.valueOf(resultSet.getString("request_status")));
    resignRequest.setUser(mappedUser);

    return resignRequest;
  }
}
