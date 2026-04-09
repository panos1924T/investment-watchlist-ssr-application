package pants.pro.investment_watchlist.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pants.pro.investment_watchlist.core.exceptions.EntityAlreadyExistsException;
import pants.pro.investment_watchlist.core.exceptions.EntityInvalidArgumentException;
import pants.pro.investment_watchlist.dto.AnalystInsertDTO;
import pants.pro.investment_watchlist.dto.AnalystReadOnlyDTO;
import pants.pro.investment_watchlist.mapper.Mapper;
import pants.pro.investment_watchlist.model.Analyst;
import pants.pro.investment_watchlist.repository.AssetRepository;

@Service
@AllArgsConstructor
@Slf4j
public class AnalystService implements IAnalystService {

    private final AssetRepository assetRepository;
    private final Mapper mapper;

    @Override
    @Transactional(rollbackFor = {EntityAlreadyExistsException.class, EntityInvalidArgumentException.class})
    public AnalystReadOnlyDTO saveAsset(AnalystInsertDTO dto) throws EntityAlreadyExistsException, EntityInvalidArgumentException {
        try {
            if (dto.ticker() != null && assetRepository.findByTicker(dto.ticker()).isPresent()) {
                throw new EntityAlreadyExistsException("Asset with ticker=" + dto.ticker() + " already exists.");
            }

            Analyst savedAnalyst = mapper.toAssetEntity(dto);
            assetRepository.save(savedAnalyst);
            log.info("Asset with ticker={} saved successfully!", dto.ticker());
            return mapper.toReadOnlyDTO(savedAnalyst);

        } catch (EntityAlreadyExistsException e) {
            log.error("Save failed for asset with ticker={}. Asset already exists!", dto.ticker());
            throw e;
        }
    }

}
