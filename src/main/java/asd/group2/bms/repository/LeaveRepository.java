package asd.group2.bms.repository;

import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest,Long> {

    List<LeaveRequest> findByLeaveIdIn(List<Long> leaveIds);

    List<LeaveRequest> findAll();

    Optional<LeaveRequest> findByLeaveId(Long leaveId);

    Optional<LeaveRequest> findByUser(User user);
}