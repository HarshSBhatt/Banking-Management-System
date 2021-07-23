package asd.group2.bms.repositoryImpl;

import asd.group2.bms.model.cheque.ChequeStatus;
import asd.group2.bms.repository.IChequeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;

public class ChequeRepositoryImp extends JdbcDaoSupport implements IChequeRepository {
  private final JdbcTemplate jdbcTemplate;

  final
  DataSource dataSource;

  public ChequeRepositoryImp(JdbcTemplate jdbcTemplate, DataSource dataSource) {
    this.jdbcTemplate = jdbcTemplate;
    this.dataSource = dataSource;
  }

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  @Override
  public Boolean update_cheque(Long chequeNumber) {

    String createCheque = "INSERT INTO cheques " +
        "(cheque_number, created_at) " +
        "VALUES (?, ?, ?)";

    int status = jdbcTemplate.update(createCheque,chequeNumber,
        new Date());

    return status!=0 ;
  }

  @Override
  public Boolean update_chequeTransaction(Long senderAccountNumber, Long receiverAccountNumber, Long chequeNumber, Double chequeAmount) {
    String createChequeTransaction = "INSERT INTO cheque_transactions " +
        "(created_at,amount,cheque_status,cheque_number," +
        "receiver_account_number,sender_account_number) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    ChequeStatus chequeStatus = ChequeStatus.PENDING;

    int status = jdbcTemplate.update(createChequeTransaction,
        new Date(),
        chequeAmount,
        chequeStatus.name(),
        chequeNumber,
        receiverAccountNumber,
        senderAccountNumber
        );

    return status!=0 ;
  }
  @Override
  public Boolean updateChequeStatus(ChequeStatus chequeStatus)
  {
    String updateChequeStatus = "INSERT INTO cheque_transactions " +
        "(updated_at,cheque_status) " +
        "VALUES (?, ?)";

    int status = jdbcTemplate.update(updateChequeStatus,
        new Date(),
        chequeStatus.name()
    );

    return status!=0 ;

  }

  @Override
  public ChequeStatus getChequeStatus(Long chequeNumber) {

    String chequeStatus = "SELECT cheque_status from cheque_transactions " +
        "where cheque_number = ?";
    ChequeStatus chequeStatus1 = jdbcTemplate.queryForObject(chequeStatus,
        new Object[]{chequeNumber},ChequeStatus.class);
    return chequeStatus1;

  }


}
