package pants.pro.analyst_registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.analyst_registry.model.static_data.Firm;

import java.util.List;

/**
 * Spring Data repository for firm persistence and sorted retrieval.
 */
public interface FirmRepository extends JpaRepository<Firm, Long> {

    /**
     * Returns all firms sorted by name.
     * @return sorted list of firms.
     */
    List<Firm> findAllByOrderByNameAsc();
}
