package asd.group2.bms.repository;

import asd.group2.bms.model.resign.RequestStatus;
import asd.group2.bms.model.resign.ResignRequest;
import asd.group2.bms.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResignRepository extends JpaRepository<ResignRequest, Long> {

  /**
   * @param requestStatus: request status
   * @descriptions: This will return the resign records by status.
   */
  Page<ResignRequest> findByRequestStatusEquals(RequestStatus requestStatus, Pageable pageable);

  /**
   * @param userId: Id of user
   * @descriptions: This will return the resign records by user id.
   */
  List<ResignRequest> findByUser_Id(Long userId);

  List<ResignRequest> findByUserOrderByCreatedAtDesc(User user);

}
