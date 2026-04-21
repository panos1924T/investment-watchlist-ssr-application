package pants.pro.analyst_registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.analyst_registry.model.static_data.Firm;

import java.util.List;

public interface FirmRepository extends JpaRepository<Firm, Long> {

    List<Firm> findAllByOrderByNameAsc();
}
