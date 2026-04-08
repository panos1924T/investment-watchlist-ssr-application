package pants.pro.investment_watchlist.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pants.pro.investment_watchlist.core.exceptions.EntityAlreadyExistsException;
import pants.pro.investment_watchlist.core.exceptions.EntityInvalidArgumentException;
import pants.pro.investment_watchlist.dto.AssetInsertDTO;
import pants.pro.investment_watchlist.dto.AssetReadOnlyDTO;
import pants.pro.investment_watchlist.repository.AssetRepository;

@Service
@AllArgsConstructor
@Slf4j
public class AssetService implements IAssetService{

    private final AssetRepository assetRepository;

    @Override
    @Transactional(rollbackFor = {EntityAlreadyExistsException.class, EntityInvalidArgumentException.class})
    public AssetReadOnlyDTO saveAsset(AssetInsertDTO dto) throws EntityAlreadyExistsException, EntityInvalidArgumentException {
        try {
            if (dto.ticker() != null && assetRepository.findByTicker(dto.ticker()).isPresent()) {
                throw new EntityAlreadyExistsException("Asset with ticker=" + dto.ticker() + " already exists.");
            }

        } catch (EntityAlreadyExistsException e) {
            log.error("Save failed for asset with ticker={}. Asset already exists!", dto.ticker());
            throw e;
        }

        return null;
    }

}
