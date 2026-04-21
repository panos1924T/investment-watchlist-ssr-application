package pants.pro.analyst_registry.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.analyst_registry.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"role", "role.capabilities"})
    Optional<User> findByUsername(String username);
}
