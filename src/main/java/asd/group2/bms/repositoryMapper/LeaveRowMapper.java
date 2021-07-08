package asd.group2.bms.repositoryMapper;

import asd.group2.bms.model.leaves.LeaveRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaveRowMapper implements RowMapper<LeaveRequest> {

  @Override
  public LeaveRequest mapRow(ResultSet resultSet, int i) throws SQLException {
    return null;
  }

}
