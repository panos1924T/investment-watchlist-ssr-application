package pants.pro.investment_watchlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.investment_watchlist.model.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByOrderByNameAsc();
}
