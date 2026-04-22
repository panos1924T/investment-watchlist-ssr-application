package pants.pro.analyst_registry.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.analyst_registry.model.User;

import java.util.Optional;

/**
 * Spring Data repository for user persistence and username-based lookup.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username with role and capabilities loaded.
     * @param username login username.
     * @return matching user when present.
     */
    @EntityGraph(attributePaths = {"role", "role.capabilities"})
    Optional<User> findByUsername(String username);
}
