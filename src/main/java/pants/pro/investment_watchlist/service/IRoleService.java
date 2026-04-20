package pants.pro.investment_watchlist.service;

import pants.pro.investment_watchlist.dto.RoleReadOnlyDTO;

import java.util.List;

public interface IRoleService {

    List<RoleReadOnlyDTO> findAllRolesSortedByName();
}
