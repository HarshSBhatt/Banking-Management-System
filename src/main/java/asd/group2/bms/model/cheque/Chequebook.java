package asd.group2.bms.model.cheque;

import asd.group2.bms.model.audit.DateAudit;
import asd.group2.bms.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @description: This will create chequebook table in the database
 */
@Entity
@Table(name = "chequebooks")
public class Chequebook extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chequebookNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Column(name = "is_issued", columnDefinition = "boolean default false")
    private Boolean isIssued;

    public Chequebook() {
    }

    public Chequebook(Long chequebookNumber, User user, Boolean isIssued) {
        this.chequebookNumber = chequebookNumber;
        this.user = user;
        this.isIssued = isIssued;
    }

    public Long getChequebookNumber() {
        return chequebookNumber;
    }

    public void setChequebookNumber(Long chequebookNumber) {
        this.chequebookNumber = chequebookNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getIssued() {
        return isIssued;
    }

    public void setIssued(Boolean issued) {
        isIssued = issued;
    }
}
