package asd.group2.bms.payload.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import asd.group2.bms.model.account.AccountType;
import asd.group2.bms.model.resign.RequestStatus;
import asd.group2.bms.model.user.User;
import java.util.Date;

/**
 * @description: Structure of resign request
 */
public class ResignRequest {

    @NotBlank(message = "Reason is required")
    private String reason;

    @NotNull(message = "Date is required")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
