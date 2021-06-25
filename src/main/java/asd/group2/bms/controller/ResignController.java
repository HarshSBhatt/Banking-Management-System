package asd.group2.bms.controller;

import asd.group2.bms.model.resign.RequestStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.request.ResignRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.payload.response.ResignListResponse;
import asd.group2.bms.payload.response.UserSummary;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.service.ResignService;
import asd.group2.bms.service.UserService;
import asd.group2.bms.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * @param currentUser: logged in user
     * @description: It will return the current user.
     */
    @GetMapping("/staff/me")
    @RolesAllowed({"ROLE_USER"})
    public UserSummary getCurrentUser(@CurrentLoggedInUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getFirstName(), currentUser.getLastName(),
                currentUser.getUsername(), currentUser.getBirthday(), currentUser.getEmail(), currentUser.getPhone(),
                currentUser.getAddress(), currentUser.getCity(), currentUser.getState(), currentUser.getZipCode());
    }

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
    public ApiResponse resign(@CurrentLoggedInUser UserPrincipal currentUser, @Valid @RequestBody ResignRequest resignRequest) {

        String email = currentUser.getEmail();
        User user = userService.getUserByEmail(email);
        Date date = resignRequest.getDate();
        String reason = resignRequest.getReason();

        return resignService.resign(user, date, reason);
    }
}
