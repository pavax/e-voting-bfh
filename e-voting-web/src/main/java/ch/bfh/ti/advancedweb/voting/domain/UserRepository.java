package ch.bfh.ti.advancedweb.voting.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u where u.username = :username")
    User findUserByUsername(@Param("username") String username);
}
