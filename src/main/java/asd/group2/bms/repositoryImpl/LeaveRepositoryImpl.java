package asd.group2.bms.repositoryImpl;

import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.model.leaves.RequestStatus;
import asd.group2.bms.model.resign.ResignRequest;
import asd.group2.bms.model.user.User;
import asd.group2.bms.repository.LeaveRepository;
import asd.group2.bms.repositoryMapper.LeaveRowMapper;
import asd.group2.bms.repositoryMapper.ResignRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class LeaveRepositoryImpl extends JdbcDaoSupport implements LeaveRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  DataSource dataSource;

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  @Override
  public Page<LeaveRequest> findByRequestStatusEquals(RequestStatus requestStatus, Pageable pageable) {
    String rowCountSql = "SELECT count(1) AS row_count " +
        "FROM leaves " +
        "WHERE request_status = " + "\"" + requestStatus + "\"";

    int total =
        jdbcTemplate.queryForObject(rowCountSql, Integer.class);

    String querySql = "SELECT * FROM leaves l INNER JOIN users u ON l.user_id" +
        " = u.id INNER JOIN user_roles ur ON u.id = " +
        "ur.user_id INNER JOIN roles ro ON ro.id = ur.role_id WHERE request_status = " + "\"" + requestStatus + "\"" +
        "LIMIT " + pageable.getPageSize() +
        " OFFSET " + pageable.getOffset();

    List<LeaveRequest> leaveRequests = jdbcTemplate.query(
        querySql, new LeaveRowMapper()
    );

    return new PageImpl<>(leaveRequests, pageable, total);
  }

  @Override
  public List<LeaveRequest> findByUser_Id(Long userId) {
    String sql = "SELECT * FROM leaves l INNER JOIN users u ON l.user_id = u" +
        ".id INNER JOIN user_roles ur ON u.id = ur.user_id INNER JOIN roles " +
        "ro ON ro.id = ur.role_id WHERE l.user_id = ?";
    try {
      return jdbcTemplate.query(sql, new Object[]{userId},
          new LeaveRowMapper());
    } catch (EmptyResultDataAccessException e) {
      return Collections.emptyList();
    }
  }

  @Override
  public Optional<LeaveRequest> findById(Long leaveId) {
    return Optional.empty();
  }

  @Override
  public List<LeaveRequest> findByUser(User user) {
    return null;
  }

  @Override
  public LeaveRequest save(LeaveRequest leaveRequest) {
    return null;
  }

  @Override
  public Boolean update(LeaveRequest leaveRequest) {
    return null;
  }

  @Override
  public void delete(Long leaveId) {

  }

}