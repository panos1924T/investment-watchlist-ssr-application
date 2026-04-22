package pants.pro.analyst_registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.analyst_registry.model.Role;

import java.util.List;

/**
 * Spring Data repository for role persistence and sorted retrieval.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Returns all roles sorted by name.
     * @return sorted list of roles.
     */
    List<Role> findAllByOrderByNameAsc();
}
