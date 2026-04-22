package pants.pro.analyst_registry.dto;

/**
 * View model exposing firm identifier and name.
 */
public record FirmReadOnlyDTO(
        Long id,
        String name
) {
}
