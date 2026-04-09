package pants.pro.investment_watchlist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pants.pro.investment_watchlist.core.exceptions.EntityAlreadyExistsException;
import pants.pro.investment_watchlist.core.exceptions.EntityInvalidArgumentException;
import pants.pro.investment_watchlist.dto.AnalystInsertDTO;
import pants.pro.investment_watchlist.dto.AnalystReadOnlyDTO;
import pants.pro.investment_watchlist.mapper.Mapper;
import pants.pro.investment_watchlist.model.Analyst;
import pants.pro.investment_watchlist.model.static_data.Firm;
import pants.pro.investment_watchlist.repository.AnalystRepository;
import pants.pro.investment_watchlist.repository.FirmRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalystService implements IAnalystService {

    private final AnalystRepository analystRepository;
    private final FirmRepository firmRepository;
    private final Mapper mapper;

    @Override
    @Transactional(rollbackFor = {EntityAlreadyExistsException.class, EntityInvalidArgumentException.class})
    public AnalystReadOnlyDTO saveAsset(AnalystInsertDTO dto) throws EntityAlreadyExistsException, EntityInvalidArgumentException {
        try {
            if (dto.email() != null && analystRepository.findByEmail(dto.email()).isPresent()) {
                throw new EntityAlreadyExistsException("Analyst with email=" + dto.email() + " already exists.");
            }

            Firm firm = firmRepository.findById(dto.firmId())
                    .orElseThrow(() -> new EntityInvalidArgumentException("Firm with id=" + dto.firmId() + " invalid."));

            Analyst savedAnalyst = mapper.toAnalystEntity(dto);
            analystRepository.save(savedAnalyst);
            log.info("Asset with ticker={} saved successfully!", dto.email());
            return mapper.toReadOnlyDTO(savedAnalyst);

        } catch (EntityAlreadyExistsException e) {
            log.error("Save failed for analyst with email={}. Analyst already exists!", dto.email());
            throw e;
        }
    }

}
