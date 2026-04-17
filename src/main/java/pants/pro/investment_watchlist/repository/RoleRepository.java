package pants.pro.investment_watchlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.investment_watchlist.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {


}
