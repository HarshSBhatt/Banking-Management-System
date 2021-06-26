package asd.group2.bms.service;

import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.model.leaves.RequestStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.request.SignUpRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.repository.LeaveRepository;
import asd.group2.bms.repository.UserRepository;
import asd.group2.bms.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LeaveService {

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    UserRepository userRepository;

    public LeaveRequest createLeave(asd.group2.bms.payload.request.LeaveRequest leaveRequest, UserPrincipal currentUser) {
        if (userRepository.existsByEmail(currentUser.getEmail())) {
            User user = userRepository.findByEmail(currentUser.getEmail()).get();
            LeaveRequest request = new LeaveRequest();
            request.setUser(user);
            request.setReason(leaveRequest.getReason());
            request.setFromDate(leaveRequest.getFromDate());
            request.setToDate(leaveRequest.getToDate());
            request.setRequestStatus(RequestStatus.PENDING);
            return leaveRepository.save(request);
        }
        return null;
    }
}
