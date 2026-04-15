package pants.pro.investment_watchlist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pants.pro.investment_watchlist.core.exceptions.EntityAlreadyExistsException;
import pants.pro.investment_watchlist.core.exceptions.EntityInvalidArgumentException;
import pants.pro.investment_watchlist.core.exceptions.EntityNotFoundException;
import pants.pro.investment_watchlist.dto.AnalystEditDTO;
import pants.pro.investment_watchlist.dto.AnalystReadOnlyDTO;
import pants.pro.investment_watchlist.dto.AnalystInsertDTO;

import java.util.UUID;

public interface IAnalystService {

    AnalystReadOnlyDTO saveAnalyst(AnalystInsertDTO analystInsertDTO)
        throws EntityAlreadyExistsException, EntityInvalidArgumentException;

    boolean isAnalystExists(String uuid);

    Page<AnalystReadOnlyDTO> getPaginatedAnalysts(Pageable pageable);

    AnalystReadOnlyDTO updateAnalyst(AnalystEditDTO analystEditDTO)
        throws EntityNotFoundException, EntityAlreadyExistsException, EntityInvalidArgumentException;

    AnalystReadOnlyDTO getAnalystByUuid(UUID uuid) throws EntityNotFoundException;
}
