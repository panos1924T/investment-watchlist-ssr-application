package pants.pro.investment_watchlist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pants.pro.investment_watchlist.dto.FirmReadOnlyDTO;
import pants.pro.investment_watchlist.mapper.Mapper;
import pants.pro.investment_watchlist.repository.FirmRepository;

import java.util.Collections;
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
                .map(mapper::toReadOnlyDTO)
                .toList();
    }
}
