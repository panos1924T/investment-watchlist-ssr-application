package pants.pro.analyst_registry.dto;

/**
 * View model exposing analyst data for UI rendering without entity internals.
 */
public record AnalystReadOnlyDTO(
        String uuid,
        String firstname,
        String lastname,
        String email,
        String firm
) {
}
