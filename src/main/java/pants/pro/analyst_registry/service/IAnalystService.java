package pants.pro.analyst_registry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pants.pro.analyst_registry.core.exceptions.EntityAlreadyExistsException;
import pants.pro.analyst_registry.core.exceptions.EntityInvalidArgumentException;
import pants.pro.analyst_registry.core.exceptions.EntityNotFoundException;
import pants.pro.analyst_registry.dto.AnalystEditDTO;
import pants.pro.analyst_registry.dto.AnalystReadOnlyDTO;
import pants.pro.analyst_registry.dto.AnalystInsertDTO;

import java.util.UUID;

/**
 * Service contract for analyst lifecycle operations and lookup queries.
 */
public interface IAnalystService {

    /**
     * Creates and persists a new analyst.
     * @param analystInsertDTO analyst creation data.
     * @return saved analyst view data.
     * @throws EntityAlreadyExistsException if analyst email already exists.
     * @throws EntityInvalidArgumentException if firm id is invalid.
     */
    AnalystReadOnlyDTO saveAnalyst(AnalystInsertDTO analystInsertDTO)
        throws EntityAlreadyExistsException, EntityInvalidArgumentException;

    /**
     * Checks whether an analyst exists by email.
     * @param uuid email value to look up.
     * @return true when an analyst with this email exists.
     */
    boolean isAnalystExists(String uuid);

    /**
     * Returns all analysts as a paginated result.
     * @param pageable page and sort settings.
     * @return page of analyst view data.
     */
    Page<AnalystReadOnlyDTO> getPaginatedAnalysts(Pageable pageable);
    /**
     * Returns active analysts as a paginated result.
     * @param pageable page and sort settings.
     * @return page of non-deleted analyst view data.
     */
    Page<AnalystReadOnlyDTO> getPaginatedAnalystsDeletedFalse(Pageable pageable);

    /**
     * Updates an existing analyst.
     * @param analystEditDTO analyst update data.
     * @return updated analyst view data.
     * @throws EntityNotFoundException if analyst is not found.
     * @throws EntityAlreadyExistsException if new email already exists.
     * @throws EntityInvalidArgumentException if firm id is invalid.
     */
    AnalystReadOnlyDTO updateAnalyst(AnalystEditDTO analystEditDTO)
        throws EntityNotFoundException, EntityAlreadyExistsException, EntityInvalidArgumentException;

    /**
     * Loads analyst edit data by UUID.
     * @param uuid analyst UUID.
     * @return analyst edit DTO.
     * @throws EntityNotFoundException if analyst is not found.
     */
    AnalystEditDTO getAnalystByUuid(UUID uuid) throws EntityNotFoundException;
    /**
     * Loads active analyst edit data by UUID.
     * @param uuid analyst UUID.
     * @return analyst edit DTO for non-deleted analyst.
     * @throws EntityNotFoundException if analyst is not found.
     */
    AnalystEditDTO getAnalystByUuidDeletedFalse(UUID uuid) throws EntityNotFoundException;

    /**
     * Soft deletes an analyst by UUID.
     * @param uuid analyst UUID.
     * @return deleted analyst view data.
     * @throws EntityNotFoundException if analyst is not found.
     */
    AnalystReadOnlyDTO deleteAnalystByUuid(UUID uuid) throws EntityNotFoundException;
}
