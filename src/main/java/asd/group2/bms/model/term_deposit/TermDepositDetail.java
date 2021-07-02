package asd.group2.bms.model.term_deposit;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.audit.DateAudit;
import asd.group2.bms.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @description: This will create term deposit detail table in the database
 */
@Entity
@Table(name = "term_deposit_details")
public class TermDepositDetail extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long termDepositId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_number", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "term_duration", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TermDeposit termDeposit;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull
    private Double initialAmount;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maturityDate;

    @NotNull
    private Double maturityAmount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TermDepositStatus termDepositStatus;

    public TermDepositDetail() {
    }

    public TermDepositDetail(User user, Account account, TermDeposit termDeposit, Date startDate, Double initialAmount, Date maturityDate, Double maturityAmount, TermDepositStatus termDepositStatus) {
        this.account = account;
        this.termDeposit = termDeposit;
        this.startDate = startDate;
        this.initialAmount = initialAmount;
        this.maturityDate = maturityDate;
        this.maturityAmount = maturityAmount;
        this.termDepositStatus = termDepositStatus;
    }

    public Long getTermDepositId() {
        return termDepositId;
    }

    public void setTermDepositId(Long termDepositId) {
        this.termDepositId = termDepositId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TermDeposit getTermDeposit() {
        return termDeposit;
    }

    public void setTermDeposit(TermDeposit termDeposit) {
        this.termDeposit = termDeposit;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(Double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Double getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(Double maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public TermDepositStatus getTermDepositStatus() {
        return termDepositStatus;
    }

    public void setTermDepositStatus(TermDepositStatus termDepositStatus) {
        this.termDepositStatus = termDepositStatus;
    }
}
