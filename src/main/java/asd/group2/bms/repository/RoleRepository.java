package asd.group2.bms.repository;

import asd.group2.bms.model.user.Role;
import asd.group2.bms.model.user.RoleType;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
=======

>>>>>>> 8b6d5742dc16cc5c728ceb0dc63884b6be1e54b0
import java.util.Optional;

public interface RoleRepository {

  /**
   * @param roleName: role of the user
   * @return This will return the role of the user
   */
  Optional<Role> findByName(RoleType roleName);

}
