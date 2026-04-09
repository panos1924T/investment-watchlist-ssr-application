package pants.pro.investment_watchlist.service;

import pants.pro.investment_watchlist.core.exceptions.EntityAlreadyExistsException;
import pants.pro.investment_watchlist.core.exceptions.EntityInvalidArgumentException;
import pants.pro.investment_watchlist.dto.AnalystReadOnlyDTO;
import pants.pro.investment_watchlist.dto.AnalystInsertDTO;

public interface IAnalystService {

    AnalystReadOnlyDTO saveAnalyst(AnalystInsertDTO analystInsertDTO)
        throws EntityAlreadyExistsException, EntityInvalidArgumentException;
}
