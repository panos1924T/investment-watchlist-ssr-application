package pants.pro.analyst_registry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pants.pro.analyst_registry.core.exceptions.EntityAlreadyExistsException;
import pants.pro.analyst_registry.core.exceptions.EntityInvalidArgumentException;
import pants.pro.analyst_registry.core.exceptions.EntityNotFoundException;
import pants.pro.analyst_registry.dto.AnalystEditDTO;
import pants.pro.analyst_registry.dto.AnalystInsertDTO;
import pants.pro.analyst_registry.dto.AnalystReadOnlyDTO;
import pants.pro.analyst_registry.mapper.Mapper;
import pants.pro.analyst_registry.model.Analyst;
import pants.pro.analyst_registry.model.static_data.Firm;
import pants.pro.analyst_registry.repository.AnalystRepository;
import pants.pro.analyst_registry.repository.FirmRepository;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalystService implements IAnalystService {

    private final AnalystRepository analystRepository;
    private final FirmRepository firmRepository;
    private final Mapper mapper;

    @Override
    @PreAuthorize("hasAuthority('INSERT_ANALYST')")
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
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public Page<AnalystReadOnlyDTO> getPaginatedAnalysts(Pageable pageable) {
        Page<Analyst> analystPage = analystRepository.findAll(pageable);
        log.debug("Get paginated returned successfully page={} and size={}", analystPage.getNumber(), analystPage.getSize());
        return analystPage.map(mapper::toAnalystReadOnlyDTO);
    }

    @Override
    @PreAuthorize("hasAuthority('VIEW_ANALYSTS')")
    @Transactional(readOnly = true)
    public Page<AnalystReadOnlyDTO> getPaginatedAnalystsDeletedFalse(Pageable pageable) {
        Page<Analyst> analystPage = analystRepository.findAllByDeletedFalse(pageable);
        log.debug("Get paginated not deleted returned successfully page={} and size={}", analystPage.getNumber(), analystPage.getSize());
        return analystPage.map(mapper::toAnalystReadOnlyDTO);
    }

    @Override

    @Transactional(rollbackFor = EntityAlreadyExistsException.class)
    @PreAuthorize("hasAuthority('DELETE_ANALYST')")
    public AnalystReadOnlyDTO deleteAnalystByUuid(UUID uuid) throws EntityNotFoundException {
        try {

            Analyst analyst = analystRepository.findByUuidAndDeletedFalse(uuid)
                    .orElseThrow(() -> new EntityNotFoundException("Analyst with uuid=" + uuid + " not found!"));

            analyst.softDelete();

            log.info("Analyst with uuid={} deleted successfully!", uuid);
            return mapper.toAnalystReadOnlyDTO(analyst);

        } catch (EntityNotFoundException e) {
            log.error("Delete failed for analyst with uuid={}. Analyst not found!", uuid, e);
            throw e;
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public AnalystEditDTO getAnalystByUuid(UUID uuid) throws EntityNotFoundException {
        try {
            Analyst analyst = analystRepository.findByUuid(uuid)
                    .orElseThrow(() -> new EntityNotFoundException("Analyst with uuid=" + uuid + " not found!"));
            log.debug("Get analyst with uuid={} returned successfully", uuid);
            return mapper.toAnalystEditDTO(analyst);
        } catch (EntityNotFoundException e) {
            log.error("Get analyst with uuid={} failed!", uuid, e);
            throw e;
        }
    }

    @Override
    @PreAuthorize("hasAuthority('EDIT_ANALYST')")
    @Transactional
    public AnalystEditDTO getAnalystByUuidDeletedFalse(UUID uuid) throws EntityNotFoundException{
        try {
            Analyst analyst = analystRepository.findByUuidAndDeletedFalse(uuid)
                    .orElseThrow(() -> new EntityNotFoundException("Analyst with uuid=" + uuid + " not found!"));
            log.debug("Get non deleted analyst with uuid={} returned successfully", uuid);
            return mapper.toAnalystEditDTO(analyst);
        } catch (EntityNotFoundException e) {
            log.error("Get analyst with uuid={} failed!", uuid, e);
            throw e;
        }
    }

    @Override
    @PreAuthorize("hasAuthority('EDIT_ANALYST')")
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
