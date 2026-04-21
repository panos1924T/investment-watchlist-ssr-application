package pants.pro.analyst_registry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pants.pro.analyst_registry.dto.FirmReadOnlyDTO;
import pants.pro.analyst_registry.mapper.Mapper;
import pants.pro.analyst_registry.repository.FirmRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirmService implements IFirmService{

    private final FirmRepository firmRepository;
    private final Mapper mapper;

    @Override
    public List<FirmReadOnlyDTO> findAllFirmsSortedByName() {
        return firmRepository.findAllByOrderByNameAsc()
                .stream()
                .map(mapper::toFirmReadOnlyDTO)
                .toList();
    }
}
