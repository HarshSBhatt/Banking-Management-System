package asd.group2.bms.repository;

import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.model.leaves.RequestStatus;
import asd.group2.bms.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {
    /**
     * @param requestStatus: request status
     * @descriptions: This will return list of leaves having request status of param - requestStatus.
     */
    Page<LeaveRequest> findByRequestStatusEquals(RequestStatus requestStatus, Pageable pageable);

    List<LeaveRequest> findByLeaveIdIn(List<Long> leaveIds);

    List<LeaveRequest> findAll();

    Optional<LeaveRequest> findByLeaveId(Long leaveId);

    List<LeaveRequest> findByUser(User user);

    /**
     * @param userId: Id of user
     * @descriptions: This will return the resign records by user id.
     */
    List<LeaveRequest> findByUser_Id(Long userId);

}