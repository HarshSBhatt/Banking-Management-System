package asd.group2.bms.repositoryImpl;

import asd.group2.bms.repository.CreditCardBillRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;

@Repository
public class CreditCardBillRepositoryImpl extends JdbcDaoSupport implements CreditCardBillRepository {

    private final JdbcTemplate jdbcTemplate;

    final
    DataSource dataSource;

    public CreditCardBillRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public Long getCreditCardNumber(Long accountNumber) {
        String sql = "select credit_card_number from credit_cards where account_number = ?";
        Long credit_card_number = jdbcTemplate.queryForObject(sql, new Object[]{accountNumber}, Long.class);
        return credit_card_number;
    }

    @Override
    public Boolean payCreditCardBill(Long CreditCardNumber,Long accountNumber) {
        String getBalance = "select balance from accounts where account_number = ?";
        Double balance = jdbcTemplate.queryForObject(getBalance,new Object[]{accountNumber},Double.class);
        String getBillAmount = "select amount from credit_card_bills where credit_card_number = ? and bill_status = 'Unpaid'";
        Double billAmount = jdbcTemplate.queryForObject(getBillAmount, new Object[]{CreditCardNumber}, Double.class);
        String updateBalance = "UPDATE accounts SET updated_at = ?,balance = ? where account_number = ?";
        String UpdateBillStatus = "UPDATE credit_card_bills SET updated_at = ?,bill_status = 'Paid' where credit_card_number = ? and bill_status = 'UnPaid' ";
        if (balance > billAmount)
        {
            int status = jdbcTemplate.update(updateBalance,new Date(),(balance- billAmount),accountNumber
            );
            if (status != 0 )
            {
                status = jdbcTemplate.update(UpdateBillStatus,new Date(),CreditCardNumber);
                return status != 0 ;
            }
        }
        return false;
    }
}
