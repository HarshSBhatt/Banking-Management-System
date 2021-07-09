package asd.group2.bms.repositoryImpl;

import asd.group2.bms.model.resign.RequestStatus;
import asd.group2.bms.model.resign.ResignRequest;
import asd.group2.bms.model.user.User;
import asd.group2.bms.repository.ResignRepository;
import asd.group2.bms.repositoryMapper.ResignRowMapper;
import asd.group2.bms.repositoryMapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ResignRepositoryImpl extends JdbcDaoSupport implements ResignRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  DataSource dataSource;

  @Autowired
  ResignRepositoryImpl resignRepository;

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  @Override
  public Page<ResignRequest> findByRequestStatusEquals(RequestStatus requestStatus, Pageable pageable) {
    return null;
  }

  @Override
  public List<ResignRequest> findByUser_Id(Long userId) {
    return null;
  }

  @Override
  public Optional<ResignRequest> findById(Long resignId) {
    String sql = "SELECT * FROM resigns r INNER JOIN users u ON r.user_id = u.id INNER JOIN roles r ON r.id = ur.role_id WHERE r.id = ?";
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{resignId},
          new ResignRowMapper()));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<ResignRequest> findByUserOrderByCreatedAtDesc(User user) {
    return null;
  }

  @Override
  public ResignRequest save(ResignRequest resignRequest) {
    return null;
  }

  @Override
  public Boolean update(ResignRequest resignRequest) {
    return null;
  }

  @Override
  public void delete(Long resignId) {

  }

}
