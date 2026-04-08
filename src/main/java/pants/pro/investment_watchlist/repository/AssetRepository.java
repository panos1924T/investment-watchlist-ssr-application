package pants.pro.investment_watchlist.repository;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.investment_watchlist.model.Asset;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByTicker(String ticker);

}
