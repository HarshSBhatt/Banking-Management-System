package asd.group2.bms.payload.response;

import asd.group2.bms.model.resign.RequestStatus;

import java.time.Instant;
import java.util.Date;

/**
 * @description: This class will be responsible to return resignation list.
 */
public class ResignListResponse {
    private Long resign_id;
    private Date date;
    private String reason;
    private RequestStatus request_status;
    private UserMetaResponse userMetaResponse;


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

    public Long getResign_id() {
        return resign_id;
    }

    public void setResign_id(Long resign_id) {
        this.resign_id = resign_id;
    }

    public RequestStatus getRequest_status() {
        return request_status;
    }

    public void setRequest_status(RequestStatus request_status) {
        this.request_status = request_status;
    }

    public UserMetaResponse getUserMetaResponse() {
        return userMetaResponse;
    }

    public void setUserMetaResponse(UserMetaResponse userMetaResponse) {
        this.userMetaResponse = userMetaResponse;
    }
}
