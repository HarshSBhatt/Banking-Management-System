package asd.group2.bms.service;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.model.leaves.RequestStatus;
import asd.group2.bms.payload.response.LeaveListResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.repository.LeaveRepository;
import asd.group2.bms.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LeaveService {
    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    UserService userService;

    /**
     * @param requestStatus: Request Status (PENDING, APPROVED, REJECTED)
     * @param page:          Page Number
     * @param size:          Size of the response data
     * @description: This will return all the leave request having status requestStatus
     */

    public PagedResponse<LeaveListResponse> getLeavesByStatus(RequestStatus requestStatus, int page, int size) {
        /** Making list in ascending order to get early applied application first */
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
        Page<LeaveRequest> leaves = leaveRepository.findByRequestStatusEquals(requestStatus, pageable);

        if (leaves.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), leaves.getNumber(),
                    leaves.getSize(), leaves.getTotalElements(), leaves.getTotalPages(), leaves.isLast());
        }

        List<LeaveListResponse> leaveListResponses = leaves.map(leave -> ModelMapper.mapLeavesToLeaveListResponse(leave)).getContent();

        return new PagedResponse<>(leaveListResponses, leaves.getNumber(),
                leaves.getSize(), leaves.getTotalElements(), leaves.getTotalPages(), leaves.isLast());
    }

    public LeaveRequest getLeaveById(Long leaveId) {
        return leaveRepository.findById(leaveId).orElseThrow(() -> new ResourceNotFoundException("Leave ID", "leaveId", leaveId));
    }

    public LeaveRequest setLeaveRequestStatus(Long leaveId, RequestStatus requestStatus) {
        LeaveRequest leaveRequest = getLeaveById(leaveId);
        leaveRequest.setRequestStatus(requestStatus);
        return leaveRepository.save(leaveRequest);
    }
}
