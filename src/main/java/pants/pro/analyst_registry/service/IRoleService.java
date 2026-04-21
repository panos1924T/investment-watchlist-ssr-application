package pants.pro.analyst_registry.service;

import pants.pro.analyst_registry.dto.RoleReadOnlyDTO;

import java.util.List;

public interface IRoleService {

    List<RoleReadOnlyDTO> findAllRolesSortedByName();
}
