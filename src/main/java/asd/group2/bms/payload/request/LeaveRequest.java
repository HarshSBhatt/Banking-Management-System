package asd.group2.bms.payload.request;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class LeaveRequest {

    @NotBlank(message = "Reason is required")
    private String reason;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

}
