package asd.group2.bms.controller;

import asd.group2.bms.model.term_deposit.TermDepositDetail;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.service.TermDepositDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TermDepositDetailController {

    @Autowired
    TermDepositDetailService termDepositDetailService;

    /**
     * @description: Return all the term deposits of current user
     */
    @GetMapping("/services/termdepositdetail")
    public ResponseEntity<Object> getTermDepositDetail(@CurrentLoggedInUser UserPrincipal currentUser) {

        List<TermDepositDetail> termDepositDetailList = termDepositDetailService.getTermDepositDetail(currentUser.getId());
        if (termDepositDetailList != null) {

            return new ResponseEntity<Object>(termDepositDetailList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "No term deposits found!"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
