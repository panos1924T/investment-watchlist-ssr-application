package pants.pro.investment_watchlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.investment_watchlist.model.static_data.Firm;

import java.util.List;

public interface FirmRepository extends JpaRepository<Firm, Long> {

    List<Firm> findAllByOrderByNameAsc();
}
