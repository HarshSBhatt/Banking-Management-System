package asd.group2.bms.repository;

import asd.group2.bms.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * @descriptions: This will return the user by email.
     * @param email: email of the user
     */
    Optional<User> findByEmail(String email);

    /**
     * @descriptions: This will return user by username or email.
     * @param username: username of the user
     * @param email:    email of the user
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * @descriptions: This will return list of users based on list of user ids.
     * @param userIds: list of user ids
     */
    List<User> findByIdIn(List<Long> userIds);

    /**
     * @descriptions: This will return user based on username.
     * @param username: username of the user
     */
    Optional<User> findByUsername(String username);

    /**
     * @descriptions: This will return true if username exists in the database.
     * @param username: username of the user
     */
    Boolean existsByUsername(String username);

    /**
     * @descriptions: This will return true if email exists in the database.
     * @param email: email of the user
     */
    Boolean existsByEmail(String email);
}
