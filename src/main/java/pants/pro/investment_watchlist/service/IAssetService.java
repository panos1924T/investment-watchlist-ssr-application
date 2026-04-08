package pants.pro.investment_watchlist.service;

import pants.pro.investment_watchlist.core.exceptions.EntityAlreadyExistsException;
import pants.pro.investment_watchlist.core.exceptions.EntityInvalidArgumentException;
import pants.pro.investment_watchlist.dto.AssetInsertDTO;
import pants.pro.investment_watchlist.dto.AssetReadOnlyDTO;

public interface IAssetService {

    AssetReadOnlyDTO saveAsset(AssetInsertDTO assetInsertDTO)
        throws EntityAlreadyExistsException, EntityInvalidArgumentException;
}
