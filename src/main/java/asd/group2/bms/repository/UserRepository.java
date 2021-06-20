package asd.group2.bms.repository;

import asd.group2.bms.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * @param email: email of the user
     * @descriptions: This will return the user by email.
     */
    Optional<User> findByEmail(String email);

    /**
     * @param forgotPasswordToken: token of the user
     * @descriptions: This will return the user by token.
     */
    Optional<User> findByForgotPasswordToken(String forgotPasswordToken);

    /**
     * @param username: username of the user
     * @param email:    email of the user
     * @descriptions: This will return user by username or email.
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * @param userIds: list of user ids
     * @descriptions: This will return list of users based on list of user ids.
     */
    List<User> findByIdIn(List<Long> userIds);

    /**
     * @param username: username of the user
     * @descriptions: This will return user based on username.
     */
    Optional<User> findByUsername(String username);

    /**
     * @param username: username of the user
     * @descriptions: This will return true if username exists in the database.
     */
    Boolean existsByUsername(String username);

    /**
     * @param email: email of the user
     * @descriptions: This will return true if email exists in the database.
     */
    Boolean existsByEmail(String email);
}
