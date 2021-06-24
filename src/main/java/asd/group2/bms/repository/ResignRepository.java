package asd.group2.bms.repository;

import asd.group2.bms.model.resign.ResignRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResignRepository extends JpaRepository<ResignRequest, Long> {

}
