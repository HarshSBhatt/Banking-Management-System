package asd.group2.bms.controller;

import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.service.LeaveService;
import asd.group2.bms.service.UserService;
import ch.qos.logback.core.CoreConstants;
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

    @PostMapping("/bank/staff/leave")
    @RolesAllowed({"ROLE_EMPLOYEE","ROLE_MANAGER"})
    public ResponseEntity<?> saveLeaveRequest(@CurrentLoggedInUser UserPrincipal currentUser,@Valid @RequestBody asd.group2.bms.payload.request.LeaveRequest leaveRequest){
        LeaveRequest insertLeave = leaveService.createLeave(leaveRequest,currentUser);
        if(insertLeave!=null) {
            return new ResponseEntity<>(insertLeave, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, "Leave is empty!"),
                HttpStatus.BAD_REQUEST);
    }
}