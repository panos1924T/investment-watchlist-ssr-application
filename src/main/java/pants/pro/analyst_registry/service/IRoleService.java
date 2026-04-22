package pants.pro.analyst_registry.service;

import pants.pro.analyst_registry.dto.RoleReadOnlyDTO;

import java.util.List;

/**
 * Service contract for role lookup operations used by user registration.
 */
public interface IRoleService {

    /**
     * Returns all roles sorted by name.
     * @return sorted list of role view data.
     */
    List<RoleReadOnlyDTO> findAllRolesSortedByName();
}
