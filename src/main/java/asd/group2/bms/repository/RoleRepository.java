package asd.group2.bms.repository;

import asd.group2.bms.model.user.Role;
import asd.group2.bms.model.user.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * @descriptions: This will return the role of the user
     * @param roleName: role of the user
     */
    Optional<Role> findByName(RoleType roleName);
}