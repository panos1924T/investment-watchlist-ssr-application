package pants.pro.investment_watchlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.investment_watchlist.model.Analyst;

import java.util.Optional;

public interface AnalystRepository extends JpaRepository<Analyst, Long> {

    Optional<Analyst> findByEmail(String email);
}
