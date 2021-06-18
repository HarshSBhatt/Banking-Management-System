package asd.group2.bms.model.cards.credit;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.audit.DateAudit;
import asd.group2.bms.model.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @description: This will create credit cards table in the database
 */
@Entity
@Table(name = "credit_cards")
public class CreditCard extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditCardNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_number", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account account;

    @NotBlank
    @Size(max = 6)
    private String pin;

    @NotBlank
    @Column(columnDefinition = "integer default 5000")
    private int limit;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CreditCardStatus creditCardStatus;

    @Column(columnDefinition = "boolean default false")
    private Boolean isActive;

    public CreditCard() {
    }

    public CreditCard(Long creditCardNumber, User user, Account account, String pin, int limit, CreditCardStatus creditCardStatus, Boolean isActive) {
        this.creditCardNumber = creditCardNumber;
        this.user = user;
        this.account = account;
        this.pin = pin;
        this.limit = limit;
        this.creditCardStatus = creditCardStatus;
        this.isActive = isActive;
    }

    public Long getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public CreditCardStatus getCreditCardStatus() {
        return creditCardStatus;
    }

    public void setCreditCardStatus(CreditCardStatus creditCardStatus) {
        this.creditCardStatus = creditCardStatus;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
