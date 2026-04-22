package pants.pro.analyst_registry.mapper;

import org.springframework.stereotype.Component;
import pants.pro.analyst_registry.dto.*;
import pants.pro.analyst_registry.model.Analyst;
import pants.pro.analyst_registry.model.Role;
import pants.pro.analyst_registry.model.User;
import pants.pro.analyst_registry.model.static_data.Firm;

@Component
/**
 * Converts between persistence entities and DTOs used by the web layer.
 */
public class Mapper {

    /**
     * Maps source data to target type.
     * @param dto dto value.
     * @return result value.
     */
    public Analyst toAnalystEntity(AnalystInsertDTO dto) {
        Analyst analyst = new Analyst(null, null, dto.firstname(), dto.lastname(), dto.email(), null);
        analyst.setDeleted(false);
        return analyst;
    }

    /**
     * Maps source data to target type.
     * @param analyst analyst value.
     * @return result value.
     */
    public AnalystReadOnlyDTO toAnalystReadOnlyDTO(Analyst analyst) {
        return new AnalystReadOnlyDTO(
                analyst.getUuid().toString(),
                analyst.getFirstname(),
                analyst.getLastname(),
                analyst.getEmail(),
                analyst.getFirm() != null ? analyst.getFirm().getName() : null
        );
    }

    /**
     * Maps source data to target type.
     * @param firm firm value.
     * @return result value.
     */
    public FirmReadOnlyDTO toFirmReadOnlyDTO(Firm firm) {
        return new FirmReadOnlyDTO(firm.getId(), firm.getName());
    }

    /**
     * Maps source data to target type.
     * @param analyst analyst value.
     * @return result value.
     */
    public AnalystEditDTO toAnalystEditDTO(Analyst analyst) {
        return new AnalystEditDTO(analyst.getUuid(),
                analyst.getFirstname(),
                analyst.getLastname(),
                analyst.getEmail(),
                analyst.getFirm().getId());
    }

    /**
     * Maps source data to target type.
     * @param userInsertDTO userInsertDTO value.
     * @return result value.
     */
    public User toUserEntity(UserInsertDTO userInsertDTO) {
        return new User(userInsertDTO.username(), userInsertDTO.password());
    }

    /**
     * Maps source data to target type.
     * @param user user value.
     * @return result value.
     */
    public UserReadOnlyDTO toUserReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getUuid().toString(), user.getUsername(), user.getRole().getName());
    }

    /**
     * Maps source data to target type.
     * @param role role value.
     * @return result value.
     */
    public RoleReadOnlyDTO toRoleReadOnlyDTO(Role role) {
        return new RoleReadOnlyDTO(role.getId(), role.getName());
    }
}
