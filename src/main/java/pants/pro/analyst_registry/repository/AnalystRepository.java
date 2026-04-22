package pants.pro.analyst_registry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.analyst_registry.model.Analyst;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data repository for analyst persistence and query methods.
 */
public interface AnalystRepository extends JpaRepository<Analyst, Long> {

    /**
     * Finds an analyst by email.
     * @param email analyst email.
     * @return matching analyst when present.
     */
    Optional<Analyst> findByEmail(String email);

    /**
     * Finds an analyst by UUID.
     * @param uuid analyst UUID.
     * @return matching analyst when present.
     */
    Optional<Analyst> findByUuid(UUID uuid);

    /**
     * Finds an active analyst by email.
     * @param email analyst email.
     * @return matching non-deleted analyst when present.
     */
    Optional<Analyst> findByEmailAndDeletedFalse(String email);

    /**
     * Finds an active analyst by UUID.
     * @param uuid analyst UUID.
     * @return matching non-deleted analyst when present.
     */
    Optional<Analyst> findByUuidAndDeletedFalse(UUID uuid);

    /**
     * Returns active analysts with firm relation eagerly loaded.
     * @param pageable page and sort settings.
     * @return page of non-deleted analysts.
     */
    @EntityGraph(attributePaths = {"firm"})
    Page<Analyst> findAllByDeletedFalse(Pageable pageable);
}
