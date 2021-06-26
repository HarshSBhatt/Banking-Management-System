package asd.group2.bms.controller;

import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.model.resign.RequestStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.request.UpdateLeaveStatusRequest;
import asd.group2.bms.payload.request.ResignRequest;
import asd.group2.bms.payload.request.UpdateResignStatusRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.payload.response.ResignListResponse;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.service.ResignService;
import asd.group2.bms.service.UserService;
import asd.group2.bms.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class ResignController {
    @Autowired
    ResignService resignService;

    @Autowired
    UserService userService;

    @GetMapping("/staff/resignation")
    @RolesAllowed({"ROLE_MANAGER", "ROLE_HR"})
    public PagedResponse<ResignListResponse> getResignationByStatus(
            @RequestParam(value = "requestStatus") RequestStatus requestStatus,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return resignService.getResignListByStatus(requestStatus, page, size);
    }

    /**
     * @param resignRequest: resign request of user
     * @description: It will create a resign request
     */
    @PostMapping("/staff/resignation")
    @RolesAllowed({"ROLE_MANAGER", "ROLE_HR", "ROLE_EMPLOYEE"})
    public ResponseEntity<?> makeResignRequest(@CurrentLoggedInUser UserPrincipal currentUser, @Valid @RequestBody ResignRequest resignRequest) {
        String email = currentUser.getEmail();
        User user = userService.getUserByEmail(email);
        Date date = resignRequest.getDate();
        String reason = resignRequest.getReason();

        return resignService.makeResignRequest(user, date, reason);
    }

    /**
     * @param updateResignStatusRequest: resign id and request status
     * @description: Update the resign status.
     */
    @PutMapping("/staff/resignation")
    @RolesAllowed({"ROLE_HR", "ROLE_MANAGER"})
    public ResponseEntity<?> updateResignRequestStatus(
            @Valid @RequestBody UpdateResignStatusRequest updateResignStatusRequest) {
        asd.group2.bms.model.resign.ResignRequest resignRequest = resignService.setResignRequestStatus(updateResignStatusRequest.getResignId(), updateResignStatusRequest.getRequestStatus());
        if (resignRequest != null) {
            return ResponseEntity.ok(new ApiResponse(true, "Resign request status changed successfully!"));
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while changing resign request status!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
