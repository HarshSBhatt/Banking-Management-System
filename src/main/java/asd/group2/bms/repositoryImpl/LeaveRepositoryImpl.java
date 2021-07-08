package asd.group2.bms.repositoryImpl;

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
public interface LeaveRepositoryImpl extends JpaRepository<LeaveRequest, Long> {

  /**
   * @param requestStatus: request status
   * @return This will return list of leaves having request status of param - requestStatus.
   */
  Page<LeaveRequest> findByRequestStatusEquals(RequestStatus requestStatus, Pageable pageable);

  /**
   * @param leaveIds: Leave Ids
   * @return This will return the leaves records by leave ids.
   */
  List<LeaveRequest> findByLeaveIdIn(List<Long> leaveIds);

  /**
   * @return This will return the leaves records
   */
  List<LeaveRequest> findAll();

  /**
   * @param leaveId: Leave Id
   * @return This will return the leave request by leave id.
   */
  Optional<LeaveRequest> findByLeaveId(Long leaveId);

  /**
   * @param user: User model object
   * @return This will return the leave record by user.
   */
  List<LeaveRequest> findByUser(User user);

  /**
   * @param userId: Id of user
   * @return This will return the leave records by user id.
   */
  List<LeaveRequest> findByUser_Id(Long userId);

}