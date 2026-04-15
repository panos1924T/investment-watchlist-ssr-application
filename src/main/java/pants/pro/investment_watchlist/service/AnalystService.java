package pants.pro.investment_watchlist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pants.pro.investment_watchlist.core.exceptions.EntityAlreadyExistsException;
import pants.pro.investment_watchlist.core.exceptions.EntityInvalidArgumentException;
import pants.pro.investment_watchlist.core.exceptions.EntityNotFoundException;
import pants.pro.investment_watchlist.dto.AnalystEditDTO;
import pants.pro.investment_watchlist.dto.AnalystInsertDTO;
import pants.pro.investment_watchlist.dto.AnalystReadOnlyDTO;
import pants.pro.investment_watchlist.mapper.Mapper;
import pants.pro.investment_watchlist.model.Analyst;
import pants.pro.investment_watchlist.model.static_data.Firm;
import pants.pro.investment_watchlist.repository.AnalystRepository;
import pants.pro.investment_watchlist.repository.FirmRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalystService implements IAnalystService {

    private final AnalystRepository analystRepository;
    private final FirmRepository firmRepository;
    private final Mapper mapper;

    @Override
    @Transactional(rollbackFor = {EntityAlreadyExistsException.class, EntityInvalidArgumentException.class})
    public AnalystReadOnlyDTO saveAnalyst(AnalystInsertDTO dto) throws EntityAlreadyExistsException, EntityInvalidArgumentException {
        try {
            if (dto.email() != null && analystRepository.findByEmail(dto.email()).isPresent()) {
                throw new EntityAlreadyExistsException("Analyst with email=" + dto.email() + " already exists.");
            }

            Firm firm = firmRepository.findById(dto.firmId())
                    .orElseThrow(() -> new EntityInvalidArgumentException("Firm with id=" + dto.firmId() + " invalid."));

            Analyst savedAnalyst = mapper.toAnalystEntity(dto);
            firm.addAnalyst(savedAnalyst);
            analystRepository.save(savedAnalyst);
            log.info("Analyst with email={} saved successfully!", dto.email());
            return mapper.toAnalystReadOnlyDTO(savedAnalyst);

        } catch (EntityAlreadyExistsException e) {
            log.error("Save failed for analyst with email={}. Analyst already exists!", dto.email());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAnalystExists(String email) {
        return analystRepository.findByEmail(email).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnalystReadOnlyDTO> getPaginatedAnalysts(Pageable pageable) {
        Page<Analyst> analystPage = analystRepository.findAll(pageable);
        log.debug("Get paginated returned successfully page={} and size={}", analystPage.getNumber(), analystPage.getSize());
        return analystPage.map(mapper::toAnalystReadOnlyDTO);
    }

    @Override
    @Transactional(rollbackFor = {EntityAlreadyExistsException.class, EntityInvalidArgumentException.class, EntityNotFoundException.class})
    public AnalystReadOnlyDTO updateAnalyst(AnalystEditDTO dto) throws EntityNotFoundException, EntityAlreadyExistsException, EntityInvalidArgumentException {


        try {
            Analyst analyst = analystRepository.findByUuid(dto.uuid())
                    .orElseThrow(() -> new EntityNotFoundException("Analyst with uuid=" + dto.uuid() + " not found!"));

            analyst.setFirstname(dto.firstname());
            analyst.setLastname(dto.lastname());
            if (!analyst.getEmail().equals(dto.email())) {
                if (analystRepository.findByEmail(dto.email()).isPresent()) {
                    throw new EntityAlreadyExistsException("Analyst with email=" + dto.email() + " already exists!");
                }
                analyst.setEmail(dto.email());
            }
            if (!Objects.equals(dto.firmId(), analyst.getFirm().getId())) {
                Firm firm = firmRepository.findById(dto.firmId())
                        .orElseThrow(() -> new EntityInvalidArgumentException("Firm id=" + dto.firmId() + " is invalid!"));
                Firm oldFirm = analyst.getFirm();
                if (oldFirm != null) {
                    oldFirm.removeAnalyst(analyst);
                }
                firm.addAnalyst(analyst);
            }

            analystRepository.save(analyst);
            log.info("Analyst with uuid={} updated successfully!", dto.uuid());
            return mapper.toAnalystReadOnlyDTO(analyst);

        } catch (EntityNotFoundException e) {
            log.error("Update failed for analyst with uuid={}. Analyst not found!", dto.uuid(), e);
            throw e;
        } catch (EntityAlreadyExistsException e) {
            log.error("Update failed for analyst with uuid={}. Analyst with email={} already exists!", dto.uuid(), dto.email(), e);
            throw e;
        } catch (EntityInvalidArgumentException e) {
            log.error("Update failed for analyst with uuid={}. Firm id={} invalid!", dto.uuid(), dto.firmId(), e);
            throw e;
        }

    }
}
