package asd.group2.bms.model.cheque;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.audit.DateAudit;
import asd.group2.bms.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @description: This will create cheque transaction table in the database
 */
@Entity
@Table(name = "cheque_transactions")
public class ChequeTransaction extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chequeTransactionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cheque_number", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Cheque cheque;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_account_number", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Account senderAccount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_account_number", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Account receiverAccount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ChequeStatus chequeStatus;

    @NotNull
    private Double amount;

    public ChequeTransaction() {
    }

    public ChequeTransaction(Long chequeTransactionId, Cheque cheque, User user, Account senderAccount, Account receiverAccount, ChequeStatus chequeStatus, Double amount) {
        this.chequeTransactionId = chequeTransactionId;
        this.cheque = cheque;
        this.user = user;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.chequeStatus = chequeStatus;
        this.amount = amount;
    }

    public Long getChequeTransactionId() {
        return chequeTransactionId;
    }

    public void setChequeTransactionId(Long chequeTransactionId) {
        this.chequeTransactionId = chequeTransactionId;
    }

    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public ChequeStatus getChequeStatus() {
        return chequeStatus;
    }

    public void setChequeStatus(ChequeStatus chequeStatus) {
        this.chequeStatus = chequeStatus;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
