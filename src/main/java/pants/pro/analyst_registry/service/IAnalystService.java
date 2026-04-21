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

public interface IAnalystService {

    AnalystReadOnlyDTO saveAnalyst(AnalystInsertDTO analystInsertDTO)
        throws EntityAlreadyExistsException, EntityInvalidArgumentException;

    boolean isAnalystExists(String uuid);

    Page<AnalystReadOnlyDTO> getPaginatedAnalysts(Pageable pageable);
    Page<AnalystReadOnlyDTO> getPaginatedAnalystsDeletedFalse(Pageable pageable);

    AnalystReadOnlyDTO updateAnalyst(AnalystEditDTO analystEditDTO)
        throws EntityNotFoundException, EntityAlreadyExistsException, EntityInvalidArgumentException;

    AnalystEditDTO getAnalystByUuid(UUID uuid) throws EntityNotFoundException;
    AnalystEditDTO getAnalystByUuidDeletedFalse(UUID uuid) throws EntityNotFoundException;

    AnalystReadOnlyDTO deleteAnalystByUuid(UUID uuid) throws EntityNotFoundException;
}
