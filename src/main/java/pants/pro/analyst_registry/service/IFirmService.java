package pants.pro.analyst_registry.service;

import pants.pro.analyst_registry.dto.FirmReadOnlyDTO;

import java.util.List;

/**
 * Service contract for firm lookup operations used by forms and views.
 */
public interface IFirmService {

    /**
     * Returns all firms sorted by name.
     * @return sorted list of firm view data.
     */
    List<FirmReadOnlyDTO> findAllFirmsSortedByName();
}
