package asd.group2.bms.controller;

import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.model.leaves.RequestStatus;
import asd.group2.bms.payload.request.UpdateLeaveStatusRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.LeaveListResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.service.LeaveService;
import asd.group2.bms.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class LeaveController {

    @Autowired
    LeaveService leaveService;

    @GetMapping("/staff/leaves")
    @RolesAllowed({"ROLE_HR", "ROLE_MANAGER"})
    public PagedResponse<LeaveListResponse> getLeavesByStatus(@RequestParam(value = "requestStatus") RequestStatus requestStatus,
                                                              @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                              @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return leaveService.getLeavesByStatus(requestStatus, page, size);
    }

    /**
     * @param updateLeaveStatusRequest: leave id and request status
     * @description: Update the leave status.
     */
    @PutMapping("/staff/leaves")
    @RolesAllowed({"ROLE_HR", "ROLE_MANAGER"})
    public ResponseEntity<?> updateLeaveRequestStatus(
            @Valid @RequestBody UpdateLeaveStatusRequest updateLeaveStatusRequest) {
        LeaveRequest leaveRequest = leaveService.setLeaveRequestStatus(updateLeaveStatusRequest.getLeaveId(), updateLeaveStatusRequest.getRequestStatus());
        if (leaveRequest != null) {
            return ResponseEntity.ok(new ApiResponse(true, "Leave request status changed successfully!"));
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while changing leave request status!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
