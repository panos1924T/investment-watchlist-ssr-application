package pants.pro.analyst_registry.dto;

/**
 * View model exposing user identity and assigned role.
 */
public record UserReadOnlyDTO(String uuid,
                              String username,
                              String role) {
}
