package asd.group2.bms.repositoryImpl;

import asd.group2.bms.model.account.AccountActivity;
import asd.group2.bms.repository.IAccountActivityRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;

@Repository
public class AccountActivityRepositoryImpl extends JdbcDaoSupport implements IAccountActivityRepository {

  private final JdbcTemplate jdbcTemplate;

  final
  DataSource dataSource;

  public AccountActivityRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
    this.jdbcTemplate = jdbcTemplate;
    this.dataSource = dataSource;
  }

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  @Override
  public AccountActivity save(AccountActivity accountActivity) {
    Date now = new Date();
    KeyHolder keyHolder = new GeneratedKeyHolder();

    String accountActivitySql = "INSERT INTO account_activities " +
        "(created_at, updated_at, activity_type, comment, transaction_amount," +
        " account_number)" +
        "VALUES (?, ?, ?, ?, ?, ?)";

    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement(accountActivitySql,
                  Statement.RETURN_GENERATED_KEYS);
          ps.setDate(1, new java.sql.Date(now.getTime()));
          ps.setDate(2, new java.sql.Date(now.getTime()));
          ps.setString(3, accountActivity.getActivityType().name());
          ps.setString(4, accountActivity.getComment());
          ps.setDouble(5, accountActivity.getTransactionAmount());
          ps.setLong(6, accountActivity.getAccount().getAccountNumber());
          return ps;
        }, keyHolder);

    accountActivity.setActivityId(keyHolder.getKey().longValue());
    return accountActivity;
  }

  @Override
  public Boolean update(AccountActivity accountActivity) {
    String sql = "UPDATE account_activities SET " +
        "updated_at = ?, comment = ? WHERE activity_id = ?";
    int status = jdbcTemplate.update(sql,
        new Date(),
        accountActivity.getComment(),
        accountActivity.getActivityId()
    );

    return status != 0;
  }

}
