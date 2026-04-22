package pants.pro.analyst_registry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pants.pro.analyst_registry.dto.RoleReadOnlyDTO;
import pants.pro.analyst_registry.mapper.Mapper;
import pants.pro.analyst_registry.repository.RoleRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
/**
 * Service implementation for retrieving roles and mapping them to read-only DTOs.
 */
public class RoleService implements IRoleService{

    private final RoleRepository roleRepository;
    private final Mapper mapper;

    /**
     * Returns all roles sorted by name.
     * @return sorted list of role view data.
     */
    @Override
    public List<RoleReadOnlyDTO> findAllRolesSortedByName() {
        return roleRepository.findAllByOrderByNameAsc()
                .stream()
                .map(mapper::toRoleReadOnlyDTO)
                .toList();
    }
}
