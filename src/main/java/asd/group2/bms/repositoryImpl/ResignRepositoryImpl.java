package asd.group2.bms.repositoryImpl;

import asd.group2.bms.model.resign.RequestStatus;
import asd.group2.bms.model.resign.ResignRequest;
import asd.group2.bms.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResignRepositoryImpl extends JpaRepository<ResignRequest, Long> {

  /**
   * @param requestStatus: request status
   * @return This will return the resign records by status.
   */
  Page<ResignRequest> findByRequestStatusEquals(RequestStatus requestStatus, Pageable pageable);

  /**
   * @param userId: Id of user
   * @return This will return the resign records by user id.
   */
  List<ResignRequest> findByUser_Id(Long userId);

  /**
   * @param user: User model object
   * @return This will return the resign records by user.
   */
  List<ResignRequest> findByUserOrderByCreatedAtDesc(User user);

}
