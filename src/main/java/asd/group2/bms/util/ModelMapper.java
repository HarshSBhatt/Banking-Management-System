package asd.group2.bms.util;

import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.LeaveListResponse;
import asd.group2.bms.payload.response.UserMetaResponse;

public class ModelMapper {
    public static LeaveListResponse mapLeavesToLeaveListResponse(LeaveRequest leaveRequest) {
        LeaveListResponse leaveListResponse = new LeaveListResponse();
        leaveListResponse.setLeaveId(leaveRequest.getLeaveId());
        leaveListResponse.setFromDate(leaveRequest.getFromDate());
        leaveListResponse.setToDate(leaveRequest.getToDate());
        leaveListResponse.setReason(leaveRequest.getReason());
        leaveListResponse.setRequestStatus(leaveRequest.getRequestStatus());

        User user = leaveRequest.getUser();
        UserMetaResponse userMetaResponse = new UserMetaResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone()
        );
        leaveListResponse.setUserMetaResponse(userMetaResponse);
        return leaveListResponse;
    }
}
