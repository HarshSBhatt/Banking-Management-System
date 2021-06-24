package asd.group2.bms.controller;

import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.request.ResignRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.UserSummary;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.service.ResignService;
import asd.group2.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/resignation")
public class ResignController {
    @Autowired
    ResignService resignService;

    @Autowired
    UserService userService;

    /**
     * @param currentUser: logged in user
     * @description: It will return the current user.
     */
    @GetMapping("/user/me")
    @RolesAllowed({"ROLE_USER"})
    public UserSummary getCurrentUser(@CurrentLoggedInUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getFirstName(), currentUser.getLastName(),
                currentUser.getUsername(), currentUser.getBirthday(), currentUser.getEmail(), currentUser.getPhone(),
                currentUser.getAddress(), currentUser.getCity(), currentUser.getState(), currentUser.getZipCode());
    }

    /**
     * @param resignRequest: resign request of user
     * @description: It will create a resign request
     */
    @PostMapping("/resign")
    public ApiResponse resign(@CurrentLoggedInUser UserPrincipal currentUser, @Valid @RequestBody ResignRequest resignRequest) {

        String email = currentUser.getEmail();
        User user = userService.getUserByEmail(email);
        Date date = resignRequest.getDate();
        String reason = resignRequest.getReason();

        return resignService.resign(user, date, reason);
    }
}
