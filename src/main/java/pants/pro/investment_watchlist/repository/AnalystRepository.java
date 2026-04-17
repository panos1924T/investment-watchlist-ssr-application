package pants.pro.investment_watchlist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pants.pro.investment_watchlist.model.Analyst;

import java.util.Optional;
import java.util.UUID;

public interface AnalystRepository extends JpaRepository<Analyst, Long> {

    Optional<Analyst> findByEmail(String email);

    Optional<Analyst> findByUuid(UUID uuid);

    Optional<Analyst> findByEmailAndDeletedFalse(String email);

    Optional<Analyst> findByUuidAndDeletedFalse(UUID uuid);

    @EntityGraph(attributePaths = {"region"})
    Page<Analyst> findAllByDeletedFalse(Pageable pageable);
}
