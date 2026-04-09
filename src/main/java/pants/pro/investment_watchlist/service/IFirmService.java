package pants.pro.investment_watchlist.service;

import pants.pro.investment_watchlist.dto.FirmReadOnlyDTO;

import java.util.List;

public interface IFirmService {

    List<FirmReadOnlyDTO> findAllFirmsSortedByName();
}
