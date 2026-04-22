package pants.pro.analyst_registry.dto;

/**
 * View model exposing role identifier and name.
 */
public record RoleReadOnlyDTO(
        Long id,
        String name
) {
}
