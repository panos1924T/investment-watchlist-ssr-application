package pants.pro.analyst_registry.service;

import pants.pro.analyst_registry.dto.FirmReadOnlyDTO;

import java.util.List;

public interface IFirmService {

    List<FirmReadOnlyDTO> findAllFirmsSortedByName();
}
