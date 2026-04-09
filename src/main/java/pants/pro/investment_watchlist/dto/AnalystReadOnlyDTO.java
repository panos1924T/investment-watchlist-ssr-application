package pants.pro.investment_watchlist.dto;

public record AnalystReadOnlyDTO(
        String uuid,
        String firstname,
        String lastname,
        String email,
        String firm
) {
}
