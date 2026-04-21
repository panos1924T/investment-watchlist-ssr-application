package pants.pro.analyst_registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.analyst_registry.model.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByOrderByNameAsc();
}
