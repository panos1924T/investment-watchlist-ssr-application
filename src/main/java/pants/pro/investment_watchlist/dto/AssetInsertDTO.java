package pants.pro.investment_watchlist.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AssetInsertDTO(
        @NotNull(message = "Company name must not be null.")
        @Size(min = 3, message = "Company name must contain at least three characters.")
        String name,

        @NotNull(message = "Ticker must not be null!")
        @Size(min = 2, max = 10, message = "Ticker's length must be between two to ten characters.")
        String ticker,

        @NotNull(message = "Asset Type must not be null!")
        Long assetTypeId
) {

    public static AssetInsertDTO empty() {
        return new AssetInsertDTO("", "", 0L);
    }
}
