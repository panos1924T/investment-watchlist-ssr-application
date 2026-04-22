package pants.pro.analyst_registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.analyst_registry.model.Capability;

/**
 * Spring Data repository for capability persistence.
 */
public interface CapabilityRepository extends JpaRepository<Capability, Long> {

}
