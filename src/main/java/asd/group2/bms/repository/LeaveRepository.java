package asd.group2.bms.repository;

import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.model.leaves.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {
    /**
     * @param requestStatus: request status
     * @descriptions: This will return list of leaves having request status of param - requestStatus.
     */
    Page<LeaveRequest> findByRequestStatusEquals(RequestStatus requestStatus, Pageable pageable);
}
