package pants.pro.investment_watchlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.investment_watchlist.model.Capability;

public interface CapabilityRepository extends JpaRepository<Capability, Long> {

}
