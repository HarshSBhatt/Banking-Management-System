package asd.group2.bms.repositoryImpl;

import asd.group2.bms.model.term_deposit.TermDepositDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import asd.group2.bms.repository.TermDepositDetailRepository;
import asd.group2.bms.repositoryMapper.RoleRowMapper;
import asd.group2.bms.repositoryMapper.TermDepositDetailRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@Repository
public class TermDepositDetailRepositoryImpl extends JdbcDaoSupport implements TermDepositDetailRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  DataSource dataSource;

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  /**
   * @param accountNumber: bank account number of the user
   * @return This will return the user's bank account number.
   */
  public List<TermDepositDetail> findTermDepositDetailByAccount_AccountNumber(Long accountNumber){
    return new ArrayList<>();
  }

  @Override
  public Optional<TermDepositDetail> findById(Long termDepositId) {
    String sql = "SELECT * FROM term_deposit_details WHERE id = ?";
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
          new Object[]{termDepositId},
          new TermDepositDetailRowMapper()));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public TermDepositDetail save(TermDepositDetail termDepositDetail) {
    return null;
  }

  @Override
  public Boolean update(TermDepositDetail termDepositDetail) {
    return null;
  }

}