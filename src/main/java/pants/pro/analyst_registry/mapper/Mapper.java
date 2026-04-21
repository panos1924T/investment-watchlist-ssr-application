package pants.pro.analyst_registry.mapper;

import org.springframework.stereotype.Component;
import pants.pro.analyst_registry.dto.*;
import pants.pro.analyst_registry.model.Analyst;
import pants.pro.analyst_registry.model.Role;
import pants.pro.analyst_registry.model.User;
import pants.pro.analyst_registry.model.static_data.Firm;

@Component
public class Mapper {

    public Analyst toAnalystEntity(AnalystInsertDTO dto) {
        Analyst analyst = new Analyst(null, null, dto.firstname(), dto.lastname(), dto.email(), null);
        analyst.setDeleted(false);
        return analyst;
    }

    public AnalystReadOnlyDTO toAnalystReadOnlyDTO(Analyst analyst) {
        return new AnalystReadOnlyDTO(
                analyst.getUuid().toString(),
                analyst.getFirstname(),
                analyst.getLastname(),
                analyst.getEmail(),
                analyst.getFirm() != null ? analyst.getFirm().getName() : null
        );
    }

    public FirmReadOnlyDTO toFirmReadOnlyDTO(Firm firm) {
        return new FirmReadOnlyDTO(firm.getId(), firm.getName());
    }

    public AnalystEditDTO toAnalystEditDTO(Analyst analyst) {
        return new AnalystEditDTO(analyst.getUuid(),
                analyst.getFirstname(),
                analyst.getLastname(),
                analyst.getEmail(),
                analyst.getFirm().getId());
    }

    public User toUserEntity(UserInsertDTO userInsertDTO) {
        return new User(userInsertDTO.username(), userInsertDTO.password());
    }

    public UserReadOnlyDTO toUserReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getUuid().toString(), user.getUsername(), user.getRole().getName());
    }

    public RoleReadOnlyDTO toRoleReadOnlyDTO(Role role) {
        return new RoleReadOnlyDTO(role.getId(), role.getName());
    }
}
